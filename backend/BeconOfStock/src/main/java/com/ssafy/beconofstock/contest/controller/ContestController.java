package com.ssafy.beconofstock.contest.controller;

import com.ssafy.beconofstock.contest.dto.ContestRequestDto;
import com.ssafy.beconofstock.contest.dto.ContestResponseDto;
import com.ssafy.beconofstock.contest.entity.Contest;
import com.ssafy.beconofstock.contest.service.ContestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Contest API", tags = {"Contest"})
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/contests")
public class ContestController {

    private final ContestService contestService;

    @GetMapping
    @ApiOperation(value = "전체 대회 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getAllContests() {
        List<ContestResponseDto> contestList = contestService.getContestAllList();

        return new ResponseEntity<>(contestList, HttpStatus.OK);
    }

    @GetMapping("/{contestId}")
    @ApiOperation(value = "전체 대회 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getContests(@PathVariable Long contestId) {
        Contest contest = contestService.getContestDetail(contestId);
        return new ResponseEntity<>(contest, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "대회 생성")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> createContest(@RequestBody ContestRequestDto contestReq) {
        ContestResponseDto contestResponseDto = contestService.createContest(contestReq);

        return new ResponseEntity<>(contestResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{contestId}")
    @ApiOperation(value = "대회 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteContest(@PathVariable Long contestId) {
        contestService.deleteContest(contestId);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
