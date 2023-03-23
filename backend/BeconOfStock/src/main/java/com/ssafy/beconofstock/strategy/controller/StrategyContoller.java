package com.ssafy.beconofstock.strategy.controller;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.strategy.dto.StrategyAddDto;
import com.ssafy.beconofstock.strategy.entity.Indicator;
import com.ssafy.beconofstock.strategy.service.StrategyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
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

    @PatchMapping("/strategies/{strategyId}")
    public ResponseEntity<?> patchStrategy(@AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                           @RequestBody StrategyAddDto strategyAddDto,
                                           @PathVariable("strategyId") Long strategyId) {

        strategyService.patchStrategy(oAuth2User.getMember(), strategyAddDto, strategyId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/strategies/{strategyId}")
    public ResponseEntity<?> deleteStrategy(@AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                            @PathVariable("strategyId") Long strategyId){

        strategyService.deleteStrategy(oAuth2User.getMember(), strategyId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
