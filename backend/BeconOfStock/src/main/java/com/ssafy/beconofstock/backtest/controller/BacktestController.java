package com.ssafy.beconofstock.backtest.controller;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.backtest.dto.BacktestResultDto;
import com.ssafy.beconofstock.backtest.service.BacktestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BacktestController {

    private final BacktestService backtestService;

    @GetMapping("/backtest")
    public ResponseEntity<BacktestResultDto> doBacktest(
            @ModelAttribute BacktestIndicatorsDto backtestIndicatorsDto){

        log.info("========={}", backtestIndicatorsDto.toString());

        BacktestResultDto backtestResult = backtestService.getBacktestResult(backtestIndicatorsDto);


        return new ResponseEntity<BacktestResultDto>(backtestResult,HttpStatus.OK);
    }
}
