package com.ssafy.beconofstock.contest.service;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.contest.dto.ContestRequestDto;
import com.ssafy.beconofstock.contest.dto.ContestResponseDto;
import com.ssafy.beconofstock.contest.entity.Contest;

import java.util.List;

public interface ContestService {

    /**
     * 대회 생성
     * @param contestReq
     * @return ContestResponseDto
     */
    ContestResponseDto createContest(ContestRequestDto contestReq);

    /**
     * 대회 리스트 가져오기
     * @return ContestResponseDto
     */
    List<ContestResponseDto> getContestList();

    /**
     * 대회 상세정보
     * @param contestId
     * @return
     */
    Contest getContestDetail(Long contestId);

    /**
     * 대회 삭제
     * @param contestId
     * @return boolean
     */
    void deleteContest(Long contestId);

    /**
     * 대회 수정
     * @param contestId
     * @return
     */
//    ContestResponseDto updateContest(Long contestId);
}
