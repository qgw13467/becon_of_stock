package com.ssafy.beconofstock.spark.controller;

import com.ssafy.beconofstock.backtest.dto.BacktestIndicatorsDto;
import com.ssafy.beconofstock.spark.dto.TestDto;
import com.ssafy.beconofstock.spark.service.SparkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(tags = {"Spark 관련 API"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class SparkController {

    private final SparkService sparkService;

    @ApiOperation(value = "스파크 테스트", notes = "trade 테이블 개수를 리턴합니다.")
    @GetMapping("/spark/test")
    @ApiResponses({@ApiResponse(code = 200, message = "성공입니다."),})
    public ResponseEntity<TestDto> doSparkTest(
            @ModelAttribute BacktestIndicatorsDto backtestIndicatorsDto) {

        log.info("========= doSparkTest : {} ", backtestIndicatorsDto.toString());

        TestDto testDto = sparkService.getConnectionTest(backtestIndicatorsDto);

        return new ResponseEntity<>(testDto, HttpStatus.OK);
    }

}
