package com.ssafy.beconofstock.member;

import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.member.entity.Role;
import com.ssafy.beconofstock.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

//    @BeforeAll
//    void memberSetting(){
//        Member member1 = Member.builder()
//                .id(1L)
//                .nickname("nick1")
//                .providerId("kakao_1")
//                .role(Role.USER)
//                .followNum(0L)
//                .build();
//
//        Member member2 = Member.builder()
//                .id(2L)
//                .nickname("nick2")
//                .providerId("kakao_2")
//                .role(Role.USER)
//                .followNum(0L)
//                .build();
//    }



}
