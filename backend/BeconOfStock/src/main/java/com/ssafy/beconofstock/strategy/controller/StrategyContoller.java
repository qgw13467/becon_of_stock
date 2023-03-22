package com.ssafy.beconofstock.strategy.controller;

import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.service.StrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class StrategyContoller {
    private final StrategyService strategyService;

    @GetMapping("/indicators")
    public ResponseEntity<?> getIndicators(){

        List<Indicator> indicators = strategyService.getIndicators();

        return new ResponseEntity<>(indicators,HttpStatus.OK);
    }



}
