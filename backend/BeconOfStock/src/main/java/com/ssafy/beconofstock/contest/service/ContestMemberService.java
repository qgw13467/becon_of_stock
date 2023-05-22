package com.ssafy.beconofstock.contest.service;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.contest.dto.ContestMemberDto;
import com.ssafy.beconofstock.contest.dto.ContestMemberJoinReqDto;
import com.ssafy.beconofstock.contest.dto.ContestMemberJoinResDto;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContestMemberService {

    /**
     * 대회 참가
     * @param user
     * @param contestMemberJoinReqDto
     * @return ContestMemberJoinResDto
     */
    ContestMemberJoinResDto joinContestMember(OAuth2UserImpl user, ContestMemberJoinReqDto contestMemberJoinReqDto);

    /**
     * 참가 취소
     * @param user
     * @param contestMemberId
     * @return true, false
     */
    Boolean deleteContestMember(OAuth2UserImpl user, Long contestMemberId);

    /**
     * 대회 별 참가자
     * @param contestId
     * @param pageable
     * @return
     */
    Page<ContestMemberDto> getContestStatus(Long contestId, Pageable pageable);


    Boolean updateRankingByContest(Long contestId);
}
