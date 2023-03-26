package com.ssafy.beconofstock.backtest.service;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
import com.ssafy.beconofstock.backtest.entity.Trade;
import com.ssafy.beconofstock.backtest.repository.FinanceRepository;
import com.ssafy.beconofstock.backtest.repository.TrateRepository;
import com.ssafy.beconofstock.strategy.repository.IndicatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class BacktestServiceImpl implements BacktestService {

    private final TrateRepository trateRepository;
    private final FinanceRepository financeRepository;
    private final IndicatorRepository indicatorRepository;

    @Override
    public BacktestResultDto getBacktestResult(BacktestIndicatorsDto backtestIndicatorsDto) {


        return null;
    }

    //산업으로 회사찾기
    private List<String> getCorpByIndustry(List<String> industries) {
        return new ArrayList<>();
    }

    //특정기간과 리벨런스기간 회사가격 가져오기
    private List<Trade> getTradeByStartTime(Integer year, Integer month, Integer rebalance) {
        List<Trade> startByYearAndMonth = trateRepository.findByYearAndMonth(year, month);
        List<Trade> endByYearAndMonth = new ArrayList<>();
        if (month + rebalance > 12) {
            year++;
            month = (month + rebalance) % 12;
            endByYearAndMonth = trateRepository.findByYearAndMonth(year, month);
        }
        List<Trade> result = new ArrayList<>();
        result.addAll(startByYearAndMonth);
        result.addAll(endByYearAndMonth);
        return result;
    }

}
