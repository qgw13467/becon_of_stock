package com.ssafy.beconofstock.backtest.controller;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
import com.ssafy.beconofstock.backtest.service.BacktestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class BacktestController {

    private final BacktestService backtestService;

    @GetMapping("/backtest")
    public ResponseEntity<BacktestResultDto> doBacktest(@RequestBody BacktestIndicatorsDto backtestIndicatorsDto){

        BacktestResultDto backtestResult = backtestService.getBacktestResult(backtestIndicatorsDto);


        return new ResponseEntity<BacktestResultDto>(backtestResult, HttpStatus.OK);
    }
}
