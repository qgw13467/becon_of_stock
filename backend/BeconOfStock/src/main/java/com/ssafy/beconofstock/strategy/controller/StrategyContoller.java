package com.ssafy.beconofstock.strategy.controller;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.strategy.dto.IndicatorsDto;
import com.ssafy.beconofstock.strategy.dto.IndustriesDto;
import com.ssafy.beconofstock.strategy.dto.StrategyAddDto;
import com.ssafy.beconofstock.strategy.dto.StrategyDetailDto;
import com.ssafy.beconofstock.strategy.service.StrategyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class StrategyContoller {
    private final StrategyService strategyService;

    @ApiOperation(value = "전략 상새보기", notes = "자신의 전략 또는 다른 사용자의 PUBLIC 전략을 상세보기 합니다")
    @GetMapping("/strategies/{strategyId}")
    public ResponseEntity<?> getStrategyDetails(@AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                                @PathVariable("strategyId") Long strategyId) {

        StrategyDetailDto strategyDetailDto = strategyService.getStrategyDetail(oAuth2User.getMember(), strategyId);

        return new ResponseEntity<>(strategyDetailDto, HttpStatus.OK);
    }

    @GetMapping("/indicators")
    public ResponseEntity<?> getIndicators() {

        IndicatorsDto indicators = strategyService.getIndicators();
        return new ResponseEntity<>(indicators, HttpStatus.OK);
    }

    @ApiOperation(value = "산업군 조회", notes =
            "산업군 전체 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공입니다."),
    })
    @GetMapping("/industries")
    public ResponseEntity<?> getIndustries() {

        IndustriesDto industries = strategyService.getIndustries();
        return new ResponseEntity<>(industries, HttpStatus.OK);
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
                                            @PathVariable("strategyId") Long strategyId) {

        strategyService.deleteStrategy(oAuth2User.getMember(), strategyId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
