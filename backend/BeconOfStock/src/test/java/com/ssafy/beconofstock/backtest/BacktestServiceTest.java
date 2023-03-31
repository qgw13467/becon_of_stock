//package com.ssafy.beconofstock.backtest;
//
//import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
//import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
//import com.ssafy.beconofstock.backtest.dto.ChangeRateDto;
//import com.ssafy.beconofstock.backtest.dto.YearMonth;
//import com.ssafy.beconofstock.backtest.repository.*;
//import com.ssafy.beconofstock.backtest.service.BacktestService;
//import com.ssafy.beconofstock.backtest.service.BacktestServiceImpl;
//import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//public class BacktestServiceTest {
//
//    @Mock   // 가짜 객체
//    private TradeRepository tradeRepository;
//    @Mock
//    private FinanceRepository financeRepository;
//    @Mock
//    private IndicatorRepository indicatorRepository;
//    @Mock
//    private BackIndustryRepository backIndustryRepository;
//    @Mock
//    private InterestRateRepository interestRateRepository;
//    @Mock
//    private KospiRepository kospiRepository;
//    @InjectMocks
//    private BacktestServiceImpl backtestService;
//
//
//    @Test
//    public void testGetBacktestResult() {
//        // given
//        BacktestIndicatorsDto backtestIndicatorsDto = new BacktestIndicatorsDto();
//        backtestIndicatorsDto.setIndicators(Arrays.asList((long) 1, (long) 2, (long) 3));
//        backtestIndicatorsDto.setIndustries(Arrays.asList((long) 1, (long) 2, (long) 3));
//        backtestIndicatorsDto.setFee(0.01);
//        backtestIndicatorsDto.setMaxStocks(10);
//        backtestIndicatorsDto.setRebalance(3);
//    }
//
//    @Test
//    public void testGetRebalanceYearMonth() {
//        // given
//        BacktestIndicatorsDto backtestIndicatorsDto = new BacktestIndicatorsDto();
//        backtestIndicatorsDto.setIndicators(Arrays.asList((long) 1, (long) 2, (long) 3));
//        backtestIndicatorsDto.setIndustries(Arrays.asList((long) 1, (long) 2, (long) 3));
//        backtestIndicatorsDto.setFee(0.01);
//        backtestIndicatorsDto.setMaxStocks(10);
//        backtestIndicatorsDto.setRebalance(3);
//
//        LocalDate startDate = LocalDate.of(2015, 1, 1);
//        LocalDate endDate = LocalDate.of(2021, 12, 31);
//
//        // when
//        List<YearMonth> yearMonthList = backtestService.getRebalanceYearMonth(backtestIndicatorsDto);
//
//        // then
//        assertEquals(24, yearMonthList.size());
//    }
//}
//
//
////    @Test
////    @DisplayName("getBacktestResult")
////    void getBacktestResult() {
////
////        //given
////        BacktestIndicatorsDto backtestIndicatorsDto = new BacktestIndicatorsDto();
////
////
////        List<YearMonth> rebalanceYearMonth = new ArrayList<>();
////        // add YearMonth objects to the list
////        // ...
////
////        List<ChangeRateDto> marketChangeRateDtos = new ArrayList<>();
////        // add ChangeRateDto objects to the list
////        // ...
////
////        List<ChangeRateDto> cumulativeReturn = new ArrayList<>();
////        // add ChangeRateDto objects to the list
////        // ...
////
////        List<ChangeRateDto> marketCumulativeReturn = new ArrayList<>();
////        // add ChangeRateDto objects to the list
////        // ...
////
////        // set up behavior for each Mock object
////        when(tradeRepository.findByYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
////        when(tradeRepository.findByYearAndMonthAndIndustryList(anyInt(), anyInt(), anyList())).thenReturn(new ArrayList<>());
////        when(indicatorRepository.findByIdIn(anyList())).thenReturn(new ArrayList<>());
////        when(backIndustryRepository.findByIdIn(anyList())).thenReturn(new ArrayList<>());
////        when(backIndustryRepository.findAll()).thenReturn(new ArrayList<>());
////        when(kospiRepository.findByDateBetween(any(), any())).thenReturn(new ArrayList<>());
////
////        // when
////        BacktestResultDto resultDto = backtestService.getBacktestResult(backtestIndicatorsDto);
////
////        // then
////        assertNotNull(resultDto);
////        assertEquals(cumulativeReturn, resultDto.getStrategyValues());
////        assertEquals(marketCumulativeReturn, resultDto.getMarketValues());
////        // assert other properties of the resultDto
////    }