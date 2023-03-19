package com.ssafy.beconofstock.testutil;

import ch.qos.logback.classic.util.LoggerNameUtil;
import com.ssafy.beconofstock.contest.entity.Contest;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.member.entity.Follow;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.member.entity.Role;
import com.ssafy.beconofstock.strategy.entity.AccessType;
import com.ssafy.beconofstock.strategy.entity.Strategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {


    static public Member getMember(){
        return Member.builder()
                .id(1L)
                .providerId("kakao_1")
                .role(Role.USER)
                .followNum(0L)
                .nickname("test")
                .boards(new ArrayList<>())
                .strategies(new ArrayList<>())
                .build();
    }

    static public Member getMember(Long id, String providerId,String nickname){
        return Member.builder()
                .id(id)
                .providerId(providerId)
                .nickname(nickname)
                .role(Role.USER)
                .followNum(0L)
                .boards(new ArrayList<>())
                .strategies(new ArrayList<>())
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
    static public Strategy getStrategy(Long id, Member member){
        return Strategy.builder()
                .id(id)
                .accessType(AccessType.PUBLIC)
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

    static public Contest getContest(Long id){
        return Contest.builder()
                .id(id)
                .title("title")
                .content("content")
                .build();
    }

    static public ContestMember getContestMember(Contest contest, Member member, Strategy strategy){
        return ContestMember.builder()
                .id(1L)
                .member(member)
                .contest(contest)
                .strategy(strategy)
                .ranking(1L)
                .participateDateTime(LocalDateTime.now())
                .build();
    }

    static public List<ContestMember> getContestMember(){
        List<ContestMember> contestMembers = new ArrayList<>();
        Member member =getMember();
        for(Long i = 0L;i<3;i++){
            ContestMember contestMember = getContestMember(getContest(i),member,getStrategy(i,member));
            contestMembers.add(contestMember);
        }
        return contestMembers;

    }

    static public Follow getFollow(Long id,Member following, Member followed){
         return new Follow(id,following,followed);
    }


}