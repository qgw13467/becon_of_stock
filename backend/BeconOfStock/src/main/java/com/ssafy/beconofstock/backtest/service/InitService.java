package com.ssafy.beconofstock.backtest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitService implements ApplicationListener<ContextRefreshedEvent> {

    private final BacktestService backtestService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {


        for (Integer year = 2009; year <= 2015; year++) {
//            backtestService.mappingTradeFinance(year);
//            backtestService.preprocess(year);
        }

//        backtestService.preprocess(2012);

    }


}