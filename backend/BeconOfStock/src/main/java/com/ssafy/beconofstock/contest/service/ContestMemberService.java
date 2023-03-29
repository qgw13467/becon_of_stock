package com.ssafy.beconofstock.contest.service;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContestMemberService {

    /**
     * 대회 참가
     * @param user
     * @param contestId
     * @param strategyId
     * @return
     */
    ContestMember joinContestMember(OAuth2UserImpl user, Long contestId, Long strategyId);

    /**
     * 참가 취소
     * @param user
     * @param contestMemberId
     * @return
     */
    Boolean deleteContestMember(OAuth2UserImpl user, Long contestMemberId);

//    Page<ContestMember> getContestStatus(Long contestId, Pageable pageable);
}
