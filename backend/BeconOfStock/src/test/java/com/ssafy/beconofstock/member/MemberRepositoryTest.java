package com.ssafy.beconofstock.member;

import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.member.entity.Role;
import com.ssafy.beconofstock.member.repository.MemberRepository;
import com.ssafy.beconofstock.testutil.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;


    @BeforeEach
    void memberSetting(){
        Member member1 = TestUtil.getMember(1L,"kakao_1","nick1");
        Member member2 = TestUtil.getMember(2L,"kakao_2","nick2");

        memberRepository.save(member1);
        memberRepository.save(member2);

    }

    @Test
    @DisplayName("findByNickname")
    void findMemberByNickname(){

        Member member = memberRepository.findByNickname("nick1").get();
        System.out.println("=====================");
        System.out.println(member.getNickname());
        Assertions.assertThat(member.getId()).isEqualTo(1L);

    }




}
