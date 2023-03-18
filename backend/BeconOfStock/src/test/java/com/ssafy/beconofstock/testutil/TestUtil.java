package com.ssafy.beconofstock.testutil;

import com.ssafy.beconofstock.contest.entity.Contest;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.member.entity.Role;
import com.ssafy.beconofstock.strategy.entity.AccessType;
import com.ssafy.beconofstock.strategy.entity.Strategy;

public class TestUtil {


    static public Member getMember(){
        return Member.builder()
                .id(1L)
                .providerId("kakao_1231245123")
                .role(Role.USER)
                .followNum(0L)
                .build();
    }

    static public Member getMember(Long id, String providerId){
        return Member.builder()
                .id(id)
                .providerId(providerId)
                .role(Role.USER)
                .followNum(0L)
                .build();
    }

    static public Strategy getStrategy(Member member, AccessType accessType){
        return Strategy.builder()
                .id(1L)
                .accessType(accessType)
                .member(member)
                .pricePer(true)
                .build();
    }

    static public Contest getContest(){
        return Contest.builder()
                .id(1L)
                .title("title")
                .content("content")
                .build();
    }

    static public ContestMember getContestMember(Contest contest, Member member, Strategy strategy){
        return ContestMember.builder()
                .id(1L)
                .member(member)
                .contest(contest)
                .strategy( strategy)
                .ranking(1L)
                .build();
    }
}