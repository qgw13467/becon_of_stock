package com.ssafy.beconofstock.contest;


import com.ssafy.beconofstock.contest.entity.Contest;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.contest.repository.ContestMemberRepository;

import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.member.repository.MemberRepository;
import com.ssafy.beconofstock.strategy.entity.AccessType;
import com.ssafy.beconofstock.strategy.entity.Strategy;
import com.ssafy.beconofstock.testutil.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ContestMemberRepositoryTest {

//    @Autowired
//    private ContestMemberRepository contestMemberRepository;


//    @Test
//    void findByMemberFetchTest(){
//        Member member = TestUtil.getMember();
//        Strategy strategy = TestUtil.getStrategy(member, AccessType.PUBLIC);
//        Contest contest = TestUtil.getContest();
//        ContestMember contestMember = TestUtil.getContestMember(contest,member,strategy);
//        contestMemberRepository.save(contestMember);
//
//        Member findParam= TestUtil.getMember();
//        Contest predict = TestUtil.getContest();
//
//        List<ContestMember> contestMemberList =
//                contestMemberRepository.findByMemberFetch("kakao_1");
//
//        Assertions.assertThat(contestMemberList.get(0).getMember()).isEqualTo(member);
//    }



}
