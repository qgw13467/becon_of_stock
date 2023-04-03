package com.ssafy.beconofstock.spark.service;

import com.ssafy.beconofstock.backtest.dto.*;
import com.ssafy.beconofstock.backtest.entity.Industry;
import com.ssafy.beconofstock.backtest.entity.InterestRate;
import com.ssafy.beconofstock.backtest.entity.Kospi;
import com.ssafy.beconofstock.backtest.entity.Trade;
import com.ssafy.beconofstock.backtest.repository.BackIndustryRepository;
import com.ssafy.beconofstock.backtest.repository.InterestRateRepository;
import com.ssafy.beconofstock.backtest.repository.KospiRepository;
import com.ssafy.beconofstock.backtest.repository.TradeRepository;
import com.ssafy.beconofstock.board.repository.BoardRepository;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.col;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class SparkServiceImpl implements SparkService {
    private final BoardRepository boardRepository;

    private final TradeRepository tradeRepository;
    private final IndicatorRepository indicatorRepository;
    private final BackIndustryRepository backIndustryRepository;
    private final InterestRateRepository interestRateRepository;
    private final KospiRepository kospiRepository;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;

    public BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto) {

        BacktestResultDto backtestResult = new BacktestResultDto();

        SparkSession spark = SparkSession.builder()
                .appName("becon_of_stock")
                .config("spark.master", "local")
                .getOrCreate();

        //각 기간에서의 변화량
        List<ChangeRateDto> strategyRateDtos = new ArrayList<>();
        List<Double> changeRates = new ArrayList<>();
        List<List<Double>> distHistory = new ArrayList<>();
        List<List<BuySellDto>> history = new ArrayList<>();

        List<YearMonth> rebalanceYearMonth = getRebalanceYearMonth(backtestIndicatorsDto);
        //시장 리벨런싱별 수익률
        List<ChangeRateDto> marketChangeRateDtos = getKospiList(backtestIndicatorsDto, rebalanceYearMonth);
        List<Double> marketChangeRates = changDtoToDoubleList(marketChangeRateDtos);
        //입력받은 지표들
        List<Indicator> indicators = indicatorRepository.findByIdIn(backtestIndicatorsDto.getIndicators());
        List<Industry> industries = new ArrayList<>();
        if (backtestIndicatorsDto.getIndustries() != null) {
            industries = backIndustryRepository.findByIdIn(backtestIndicatorsDto.getIndustries());
        }

        List<Industry> industryAllList = backIndustryRepository.findAll();

        Dataset<Row> tradeDataset;
        String query;
        if (industries.size() != industryAllList.size() && industries.size() != 0) {
            query = getQueryTradeView("trade_industry", rebalanceYearMonth);
            query = getQueryAppendIndustryCondition(query, backtestIndicatorsDto.getIndustries());
            tradeDataset = getDataSet(spark, query);
        } else {
            query = getQueryTradeView("trade", rebalanceYearMonth);
            tradeDataset = getDataSet(spark, query);
        }

        log.info("============= rebalanceYearMonth size : {}  ============", rebalanceYearMonth.size());
        for (YearMonth yearMonth : rebalanceYearMonth) {
//            List<Trade> trades;

            Dataset<Row> trade;
            if (industries.size() != industryAllList.size() && industries.size() != 0) {
                trade = tradeDataset.filter((tradeDataset.col("year").equalTo(yearMonth.getYear())
                        .and(tradeDataset.col("month").equalTo(yearMonth.getMonth())))
                        .and(tradeDataset.col("industry_id").isInCollection(backtestIndicatorsDto.getIndustries())));
                log.info("============= trade count : {}  ============", trade.count());
            } else {
                trade = tradeDataset.filter((tradeDataset.col("year").equalTo(yearMonth.getYear()).and(tradeDataset.col("month").$eq$eq$eq(yearMonth.getMonth()))));
                log.info("============= trade count : {}  ============", trade.count());
            }
            trade.show();
            System.out.println(trade.count());

            Double revenueByDataSet = getRevenueByDataSet(spark, trade, tradeDataset, backtestIndicatorsDto.getRebalance());
            strategyRateDtos.add(new ChangeRateDto(revenueByDataSet, backtestIndicatorsDto.getEndYear(), backtestIndicatorsDto.getEndMonth()));
        }
        tradeDataset.show();
        System.out.println(tradeDataset.count());


        //누적수익률 (단위 : % )
        List<ChangeRateDto> cumulativeReturn = getCumulativeReturn(strategyRateDtos, backtestIndicatorsDto.getFee());
        List<ChangeRateDto> marketCumulativeReturn = getCumulativeReturn(marketChangeRateDtos, backtestIndicatorsDto.getFee());
        backtestResult.setCumulativeReturnDtos(ChangeRateValueDto.mergeChangeRateDtos(cumulativeReturn, marketCumulativeReturn));

        //전략 지표들 계산
        backtestResult.setChangeRate(ChangeRateValueDto.mergeChangeRateDtos(strategyRateDtos, marketChangeRateDtos));

        CumulativeReturnDataDto cumulativeReturnDataDto =
                getCumulativeRuturnDataDto(
                        cumulativeReturn,
                        changeRates,
                        strategyRateDtos,
                        backtestIndicatorsDto,
                        marketChangeRateDtos,
                        marketCumulativeReturn,
                        marketChangeRates
                );
        backtestResult.setCumulativeReturnDataDto(cumulativeReturnDataDto);

        RevenueDataDto revenueDataDto = RevenueDataDto.builder()
                .strategyRevenue(winCount(changeRates))
                .marketRevenue(winCount(marketChangeRates))
                .totalMonth(rebalanceYearMonth.size())
                .build();
        backtestResult.setRevenueDataDto(revenueDataDto);

        backtestResult.setIndicators(indicators.stream().map(Indicator::getId).collect(Collectors.toList()));


        spark.close();
        return backtestResult;
    }

    private CumulativeReturnDataDto getCumulativeRuturnDataDto(List<ChangeRateDto> cumulativeReturn,
                                                               List<Double> changeRates,
                                                               List<ChangeRateDto> strategyRateDtos,
                                                               BacktestIndicatorsDto backtestIndicatorsDto,
                                                               List<ChangeRateDto> marketChangeRateDtos,
                                                               List<ChangeRateDto> marketCumulativeReturn,
                                                               List<Double> marketChangeRates) {

        return CumulativeReturnDataDto.builder()
                .strategyCumulativeReturn(cumulativeReturn.get(cumulativeReturn.size() - 1).getChangeRate())
                .strategyCagr(getAvg(changeRates))
                .strategySharpe(getSharpe(strategyRateDtos, backtestIndicatorsDto))
                .strategySortino(getSortino(strategyRateDtos, backtestIndicatorsDto))
                .strategyMDD(getMdd(cumulativeReturn))
                .marketCumulativeReturn(marketCumulativeReturn.get(marketCumulativeReturn.size() - 1).getChangeRate())
                .marketCagr(getAvg(marketChangeRates))
                .marketSharpe(getSharpe(marketChangeRateDtos, backtestIndicatorsDto))
                .marketSortino(getSortino(marketChangeRateDtos, backtestIndicatorsDto))
                .marketMDD(getMdd(marketCumulativeReturn))
                .build();

    }

    public Double getRevenueByDataSet(SparkSession spark, Dataset<Row> buy, Dataset<Row> trade, Integer rebalance) {

//        spark = SparkSession.builder()
//                .appName("becon_of_stock")
//                .config("spark.master", "local")
//                .getOrCreate();
//        rebalance = 3;
//        String query = "SELECT * FROM trade WHERE trade.year=" + 2010 + " AND trade.month = " + 1;
//        buy = spark
//                .read()
//                .format("jdbc")
//                .option("driver", "com.mysql.cj.jdbc.Driver")
//                .option("url", url)
//                .option("user", userName)
//                .option("password", password)
//                .option("query", query)
//                .load();
//        buy.show();
//        query = "SELECT * FROM trade WHERE trade.year=" + 2010 + " AND trade.month in (1, 2, 3 ,4)";
//        trade = spark
//                .read()
//                .format("jdbc")
//                .option("driver", "com.mysql.cj.jdbc.Driver")
//                .option("url", url)
//                .option("user", userName)
//                .option("password", password)
//                .option("query", query)
//                .load();
//        trade.show();


        Integer year = (Integer) buy.select("year").first().get(0);
        Integer month = (Integer) buy.select("month").first().get(0);
        month += rebalance;
        if (month > 12) {
            year++;
            month -= 12;
        }

        Dataset<Row> result = buy.as("buy")
                .join(trade.as("trade"), buy.col("corcode").equalTo(trade.col("corcode")))
                .where("trade.year = " + year + " AND trade.month =" + month)
                .select(
                        avg(
                                (trade.col("corclose").multiply(trade.col("stocks")))
                                        .divide
                                                (buy.col("corclose").multiply(buy.col("stocks")))).as("calibratedChangeRate")
                        ,
                        avg(trade.col("corclose").divide(buy.col("corclose"))).as("PriceChangeRate"),
                        avg(trade.col("marcap").divide(buy.col("marcap"))).as("MarcapChangeRate")
                );

//        result.show();
        Double calibratedChangeRate = result.first().getDouble(0);
        Double priceChangeRate = result.first().getDouble(1);
        Double marcapChangeRate = result.first().getDouble(2);

//        result.show();
//        System.out.println(calibratedChangeRate+", "+ priceChangeRate+", "+marcapChangeRate);

        if (priceChangeRate == null) {
            return 1D;
        }
        if (priceChangeRate > 2) {
            if (Math.abs(priceChangeRate / calibratedChangeRate - 1) > 0.3) {
                return 1D;
            }
        }
        return priceChangeRate;

//        return 1D;
    }

    private Dataset<Row> getDataSet(SparkSession spark, String query) {

        Dataset<Row> df = spark
                .read()
                .format("jdbc")
                .option("driver", "com.mysql.cj.jdbc.Driver")
                .option("url", url)
                .option("user", userName)
                .option("password", password)
                .option("query", query)
                .load();
        return df;
    }

    private String getQueryTradeView(String table, List<YearMonth> yearMonths) {

        StringBuilder sb = new StringBuilder();
        sb.append("select * from ").
                append(table).
                append(" where (( year = ").append(yearMonths.get(0).getYear()).
                append(" and month = ").append(yearMonths.get(0).getMonth()).append(")");
        for (int i = 1; i < yearMonths.size(); i++) {
            sb.append(" or").
                    append(" ( year = ").append(yearMonths.get(i).getYear()).
                    append(" and month = ").append(yearMonths.get(i).getMonth()).append(")");
        }
        sb.append(")");
        return sb.toString();
    }

    private String getQueryAppendIndustryCondition(String query, List<Long> industries) {
        StringBuilder sb = new StringBuilder(query);
        sb.append(" and (industry_id in (").
                append(industries.get(0));
        for (int i = 1; i < industries.size(); i++) {
            sb.append(", ").append(industries.get(i));
        }
        sb.append(")").append(")");
        return sb.toString();
    }

    //포스피리스트를 리벨런싱 기간메 맞게 ChangeRateDto리스트로 변환
    private List<ChangeRateDto> getKospiList(BacktestIndicatorsDto backtestIndicatorsDto, List<YearMonth> yearMonths) {
        List<ChangeRateDto> result = new ArrayList<>();

        List<Kospi> kospis = kospiRepository.findByStartYearAndEndYear(
                backtestIndicatorsDto.getStartYear(), backtestIndicatorsDto.getEndYear() + 1);
        int startIdx = 0;
        for (; startIdx < kospis.size(); startIdx++) {
            if (kospis.get(startIdx).getYear().equals(backtestIndicatorsDto.getStartYear()) &&
                    kospis.get(startIdx).getMonth().equals(backtestIndicatorsDto.getStartMonth())) {
                break;
            }
        }

        for (YearMonth yearMonth : yearMonths) {
            ChangeRateDto changeRateDto = new ChangeRateDto();
            changeRateDto.setYear(yearMonth.getYear());
            changeRateDto.setMonth(yearMonth.getMonth());
            Double val = 1D;
            for (int i = 0; i < backtestIndicatorsDto.getRebalance(); i++) {
                val = val * ((100 + kospis.get(startIdx).getVolatility()) / 100.0);
                startIdx++;
            }
            changeRateDto.setChangeRate(val);
            result.add(changeRateDto);
        }


        return result;
    }


    //todo fix logic
    private Double getMdd(List<ChangeRateDto> cumulativeReturn) {
        double mdd = 100D;
        double max = 0D;
        double min = 99999999D;
        for (ChangeRateDto changeRateDto : cumulativeReturn) {
            if (max < changeRateDto.getChangeRate()) {
                max = changeRateDto.getChangeRate();
                min = 99999999D;
                continue;
            }

            if (min > changeRateDto.getChangeRate()) {
                min = changeRateDto.getChangeRate();
                double temp = (min - max) / max;
                if (mdd > temp) {
                    mdd = temp;
                }
            }
        }
        if (mdd == 100D) {
            return 0D;
        }
        return mdd * 100D;
    }


    //리벨런스 횟수 계산
    private int getRebalanceCount(int startYear, int startMonth,
                                  int endYear, int endMonth, int rebalance) {
        int result = 0;
        result = endMonth - startMonth + (endYear - startYear) * 12;
        result = result / rebalance;
//        int temp = result % rebalance;
//        if (temp != 0) {
//            result++;
//        }
        return result;
    }


    //샤프지수 계산
    private Double getSharpe(List<ChangeRateDto> changeRateDtos, BacktestIndicatorsDto backtestIndicatorsDto) {

        List<Double> changeRate = changeRateDtos.stream()
                .map(dto -> dto.getChangeRate())
                .collect(Collectors.toList());
        Double deviation = getDeviation(changeRate);
        Double result = getRevenueMinusInterest(changeRateDtos, backtestIndicatorsDto);
        result = 1.0 * result / deviation;
        return Math.abs(result);
    }

    //소티노 계산
    private Double getSortino(List<ChangeRateDto> changeRateDtos, BacktestIndicatorsDto backtestIndicatorsDto) {

        List<Double> changeRate = changeRateDtos.stream()
                .map(dto -> dto.getChangeRate())
                .collect(Collectors.toList());
        Double nagativeDeviation = getNagativeDeviation(changeRate);
        Double result = getRevenueMinusInterest(changeRateDtos, backtestIndicatorsDto);
        result = 1.0 * result / nagativeDeviation;
        return Math.abs(result);
    }

    //샤프,소티노 공통부분 분리
    private Double getRevenueMinusInterest(List<ChangeRateDto> changeRateDtos, BacktestIndicatorsDto backtestIndicatorsDto) {

        List<Double> changeRate = changeRateDtos.stream()
                .map(dto -> dto.getChangeRate())
                .collect(Collectors.toList());

        List<InterestRate> interestRates =
                interestRateRepository.findByYearMonthList(
                        backtestIndicatorsDto.getStartYear(),
                        backtestIndicatorsDto.getEndYear());

        Double avgRevenue = getAvg(changeRate);

        List<Double> interests = new ArrayList<>();

        for (ChangeRateDto changeRateDto : changeRateDtos) {
            InterestRate interestRate =
                    getInterestRateByYearMonth(interestRates, changeRateDto.getYear(), changeRateDto.getMonth());
            interests.add(interestRate.getInterestRate());
        }
        Double avgInterest = getAvg(interests);
        avgInterest = 1D + (avgInterest / 100D);
//        System.out.println("avgRevenue: " + avgRevenue + ", avgInterest: " + avgInterest);
        return avgRevenue - avgInterest;
    }

    //기간동안 매수가 생기는 연도,월 반환
    private List<YearMonth> getRebalanceYearMonth(int startYear, int startMonth,
                                                  int endYear, int endMonth, int rebalance) {
        List<YearMonth> result = new ArrayList<>();
        int thisYear = startYear;
        int thisMonth = startMonth;
        result.add(new YearMonth(thisYear, thisMonth));
        for (int start = 0; start < getRebalanceCount(startYear, startMonth, endYear, endMonth, rebalance); start++) {
            thisMonth += rebalance;
            if (thisMonth > 12) {
                thisYear++;
                thisMonth = thisMonth - 12;
            }
            result.add(new YearMonth(thisYear, thisMonth));
        }
        return result;
    }

    //기간동안 매수가 생기는 연도,월 반환
    private List<YearMonth> getRebalanceYearMonth(BacktestIndicatorsDto backtestIndicatorsDto) {
        Integer startYear = backtestIndicatorsDto.getStartYear();
        Integer startMonth = backtestIndicatorsDto.getStartMonth();
        Integer endYear = backtestIndicatorsDto.getEndYear();
        Integer endMonth = backtestIndicatorsDto.getEndMonth();
        Integer rebalance = backtestIndicatorsDto.getRebalance();
        return getRebalanceYearMonth(startYear, startMonth, endYear, endMonth, rebalance);
    }

    //누적 수익률 계산
    private List<ChangeRateDto> getCumulativeReturn(List<ChangeRateDto> changeRateDtos, double fee) {
        List<ChangeRateDto> result = new ArrayList<>();
        double now = 100D;
        for (ChangeRateDto chageRateDto : changeRateDtos) {
            if (chageRateDto.getChangeRate().equals(0D)) {
                now = now * ((100.0 - fee) / 100);
            } else {
                now = now * chageRateDto.getChangeRate() * ((100.0 - fee) / 100);
            }
            result.add(new ChangeRateDto(now, chageRateDto.getYear(), chageRateDto.getMonth()));
        }
        return result;
    }

    //기간평균수익률
    private Double getAvg(List<Double> changeRate) {
        Double sum = 0D;
        for (Double rate : changeRate) {
            sum += rate;
        }
        return sum / (double) changeRate.size();
    }

    //ChangDto리스트의 변화량을 Double리스트로 변환
    private List<Double> changDtoToDoubleList(List<ChangeRateDto> changeRateDtos) {
        return changeRateDtos.stream()
                .map(changeRateDto -> changeRateDto.getChangeRate())
                .collect(Collectors.toList());

    }


    //분산계산
    private Double getDeviation(List<Double> list) {
        Double avg = getAvg(list);
        Double sum = 0D;
        for (Double rate : list) {
            Double temp = rate - avg;
            sum += temp * temp;
        }
        sum = 1.0 * sum / (double) list.size();
//        System.out.println("sum: " + sum + ", devi: " + Math.sqrt(sum));

        return Math.sqrt(sum);
    }

    private Double getNagativeDeviation(List<Double> list) {
        Double avg = getAvg(list);
        Double sum = 0D;
        for (Double rate : list) {
            if (rate >= 1) {
                continue;
            }
            Double temp = rate - avg;
            sum += temp * temp;
        }
        sum = 1.0 * sum / (double) list.size();
        System.out.println("sum: " + sum + ", devi: " + Math.sqrt(sum));
        return Math.sqrt(sum);
    }


    //양수 카운트
    private Integer winCount(List<Double> chageRate) {
        int sum = 0;
        for (Double rate : chageRate) {
            if (rate >= 1) {
                sum++;
            }
        }
        return sum;
    }

    //배열에서 해당 연월의 금리 구하기
    private InterestRate getInterestRateByYearMonth(List<InterestRate> list, Integer year, Integer month) {
        InterestRate result = null;
        for (InterestRate interestRate : list) {
            if (interestRate.getYear().equals(year) && interestRate.getMonth().equals(month)) {
                result = interestRate;
                break;
            }
        }
        return result;
    }


    // TODO : 각 지표 정렬, 합산 수행
    private List<Trade> calcTradesIndicator(List<Trade> trades, List<Indicator> indicators, int maxNum) {

        for (Indicator indicator : indicators) {
            trades.sort(sortByIndicator(indicator));
            calSumTradesIndicator(trades, indicator);
        }
        calAverageRanking(trades);
        trades.sort(sortByIndicator(new Indicator("ranking")));

//        maxnum만큼 잘라서 반환
        if (maxNum > trades.size()) {
            maxNum = trades.size();
        }
        trades = trades.subList(0, maxNum);

        return trades;
    }

    private void calAverageRanking(List<Trade> trades) {
        for (Trade trade : trades) {
            if (trade.getCnt() == 0) {
                trade.setRanking(999);
            } else {
                trade.setRanking(trade.getRanking() / trade.getCnt());
            }
        }
    }

    private void calSumTradesIndicator(List<Trade> trades, Indicator indicator) {

        int idx = 0;
        switch (indicator.getTitle()) {
            case "pricePER":
                for (int i = 0; i < trades.size(); i++) {
                    if (trades.get(i).getPricePER() > 0) {
                        idx = i;
                        break;
                    }
                }
                break;
            case "pricePBR":
                for (int i = 0; i < trades.size(); i++) {
                    if (trades.get(i).getPricePBR() > 0) {
                        idx = i;
                        break;
                    }
                }
                break;
            case "pricePSR":
                for (int i = 0; i < trades.size(); i++) {
                    if (trades.get(i).getPricePSR() > 0) {
                        idx = i;
                        break;
                    }
                }
                break;
            case "pricePOR":
                for (int i = 0; i < trades.size(); i++) {
                    if (trades.get(i).getPricePOR() > 0) {
                        idx = i;
                        break;
                    }
                }
                break;
        }

        for (int i = idx, j = 1; i < trades.size(); i++, j++) {
            trades.get(i).addRanking(j);
            trades.get(i).addCnt();
        }

    }

    private Comparator<Trade> sortByIndicator(Indicator indicator) {
        switch (indicator.getTitle()) {
            case "pricePER":
                return Comparator.comparing(Trade::getPricePER);
            case "pricePBR":
                return Comparator.comparing(Trade::getPricePBR);
            case "pricePSR":
                return Comparator.comparing(Trade::getPricePSR);
            case "pricePOR":
                return Comparator.comparing(Trade::getPricePOR);
            case "qualityROE":
                return Comparator.comparing(Trade::getQualityROE).reversed();
            case "qualityROA":
                return Comparator.comparing(Trade::getQualityROA).reversed();
            case "growth3MonthTake":
                return Comparator.comparing(Trade::getGrowth3MonthTake).reversed();
            case "growth3MonthOperatingProfit":
                return Comparator.comparing(Trade::getGrowth3MonthOperatingProfit).reversed();
            case "growth3MonthNetProfit":
                return Comparator.comparing(Trade::getGrowth3MonthNetProfit).reversed();
            default:
                return Comparator.comparing(Trade::getRanking);
        }

    }

    //구매한 종목이 한주기 다음 수익이 얼마인지 계산
    private Double getRevenue(YearMonth yearMonth, List<Trade> list, int rebalance, List<List<BuySellDto>> history, List<List<Double>> distHistory) {

        Double result = 0D;
        List<Double> dist = new ArrayList<>();
        List<BuySellDto> tradeList = new ArrayList<>();
        int useYear = yearMonth.getYear();
        int useMonth = yearMonth.getMonth();

        useMonth += rebalance;
        if (useMonth > 12) {
            useYear++;
            useMonth -= 12;
        }
        List<String> corcodes = list.stream().map(Trade::getCorcode).collect(Collectors.toList());

        List<Trade> byYearAndMonthAndCorcodeList = tradeRepository.findByYearAndMonthAndCorcodeList(useYear, useMonth, corcodes);

        for (Trade trade : list) {
            Trade find = findByCorcode(trade.getCorcode(), byYearAndMonthAndCorcodeList);
            if (find == null) {
                dist.add(1D);
                continue;
            }

//            System.out.println("start: " + trade.getCorname() + ", " + (find.getMarcap().doubleValue() / 100D) + ", " + trade.getYear() + " " + trade.getMonth() + ", end : "
//                    + find.getCorname() + ", " + (trade.getMarcap().doubleValue() / 100D) + ", " + find.getYear() + " " + find.getMonth());

            tradeList.add(new BuySellDto(trade, find));

            double temp = (find.getCorclose().doubleValue()) / (trade.getCorclose().doubleValue());
            if (temp > 2) {
                double check = find.getMarcap().doubleValue() / trade.getMarcap().doubleValue();
                if (Math.abs(temp / check - 1) > 0.3) {
                    continue;
                }
            }
//            double temp = (find.getCorclose().doubleValue() / trade.getCorclose().doubleValue());
            dist.add(temp);
        }

        history.add(tradeList);
        distHistory.add(dist);
//        System.out.println("dist: " + dist);

        result = getAvg(dist);
        if (result == 0 || result.equals(Double.NaN)) {
            return 1D;
        }
        return result;
    }

    private Trade findByCorcode(String corcode, List<Trade> trades) {
        Trade result = null;
        for (Trade trade : trades) {
            if (trade.getCorcode().equals(corcode)) {
                result = trade;
                break;
            }
        }
        return result;
    }
}