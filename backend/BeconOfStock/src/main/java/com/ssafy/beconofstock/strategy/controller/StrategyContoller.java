package com.ssafy.beconofstock.strategy.controller;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.strategy.dto.StrategyAddDto;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.service.StrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class StrategyContoller {
    private final StrategyService strategyService;

    @GetMapping("/indicators")
    public ResponseEntity<?> getIndicators() {

        Map<String, List<Indicator>> indicators = strategyService.getIndicators();

        return new ResponseEntity<>(indicators, HttpStatus.OK);
    }


    @PostMapping("/strategies")
    public ResponseEntity<?> saveStrategy(@AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                          @RequestBody StrategyAddDto strategyAddDto) {


        strategyService.addStrategy(oAuth2User.getMember(), strategyAddDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
