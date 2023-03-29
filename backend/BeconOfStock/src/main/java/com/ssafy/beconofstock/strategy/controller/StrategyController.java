package com.ssafy.beconofstock.strategy.controller;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.strategy.dto.IndicatorsDto;
import com.ssafy.beconofstock.strategy.dto.StrategyAddDto;
import com.ssafy.beconofstock.strategy.dto.StrategyDetailDto;
import com.ssafy.beconofstock.strategy.dto.StrategyListDto;
import com.ssafy.beconofstock.strategy.entity.StrategyDibs;
import com.ssafy.beconofstock.strategy.service.StrategyDibsService;
import com.ssafy.beconofstock.strategy.service.StrategyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class StrategyController {
    private final StrategyService strategyService;

    private final StrategyDibsService strategyDibsService;

    @ApiOperation(value = "전략 상새보기", notes = "자신의 전략 또는 다른 사용자의 PUBLIC 전략을 상세보기 합니다")
    @GetMapping("/strategies/{strategyId}")
    public ResponseEntity<?> getStrategyDetails(@AuthenticationPrincipal OAuth2UserImpl oAuth2User,
                                                @PathVariable("strategyId") Long strategyId){

        StrategyDetailDto strategyDetailDto = strategyService.getStrategyDetail(oAuth2User.getMember(),strategyId);

        return new ResponseEntity<>(strategyDetailDto,HttpStatus.OK);
    }

    @GetMapping("/indicators")
    public ResponseEntity<?> getIndicators() {

        IndicatorsDto indicators = strategyService.getIndicators();
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

    @GetMapping("/strategies")
    @ApiOperation(value = "자신의 전략리스트 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Page<StrategyListDto>> getStrategyMyList(@AuthenticationPrincipal OAuth2UserImpl user, Pageable pageable) {

        return new ResponseEntity<>(strategyService.getStrategyMyList(user, pageable), HttpStatus.OK);
    }

//    @GetMapping("/strategies/dibs")   // 실패..
//    @ApiOperation(value = "자신의 찜 전략 조회")
//    @ApiResponses({
//            @ApiResponse(code = 200, message = "성공"),
//            @ApiResponse(code = 401, message = "인증 실패"),
//            @ApiResponse(code = 404, message = "사용자 없음"),
//            @ApiResponse(code = 500, message = "서버 오류")
//    })
//    public ResponseEntity<Page<StrategyDibs>> getStrategyDibsMyList(@AuthenticationPrincipal OAuth2UserImpl user, Pageable pageable) {
//        Page<StrategyDibs> result = strategyDibsService.getStrategyDibsMyList(user, pageable);
//        return ResponseEntity.ok(result);
//    }

    @PostMapping("/dibs/{strategyId}")
    @ApiOperation(value = "전략 찜")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> dibsStrategy(@AuthenticationPrincipal OAuth2UserImpl user, @PathVariable Long strategyId) {
        strategyDibsService.dibsStrategy(strategyId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/dibs/{strategyDibsId}")
    @ApiOperation(value = "전략 찜 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteStrategyDibs(@AuthenticationPrincipal OAuth2UserImpl user, @PathVariable Long strategyDibsId) {
        strategyDibsService.deleteDibs(strategyDibsId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
