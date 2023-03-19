package com.ssafy.beconofstock.member;

import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.contest.repository.ContestMemberRepository;
import com.ssafy.beconofstock.member.dto.UserInfoDto;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.member.repository.FollowRepository;
import com.ssafy.beconofstock.member.repository.MemberRepository;
import com.ssafy.beconofstock.member.service.MemberService;
import com.ssafy.beconofstock.member.service.MemberServiceImpl;
import com.ssafy.beconofstock.testutil.TestUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ContestMemberRepository contestMemberRepository;
    @Mock
    private FollowRepository followRepository;
    @InjectMocks
    private MemberServiceImpl memberService;


    @Test
    @DisplayName("getUserInfoDtoTest")
    void getUserInfoDtoTest(){

        //given
        Member member = TestUtil.getMember();
        List<ContestMember> contestMembers = TestUtil.getContestMember();

        Mockito.lenient().doReturn(contestMembers).when(contestMemberRepository).findByMemberFetch(member);
        Mockito.lenient().doReturn(Optional.of(member)).when(memberRepository).findById(Mockito.any());
        memberService = new MemberServiceImpl(memberRepository,contestMemberRepository,followRepository);

        //when
        UserInfoDto userInfoDto = memberService.getUserInfoDto(1L);

        //then
        Assertions.assertThat(userInfoDto.getNickname()).isEqualTo("test");
        Assertions.assertThat(userInfoDto.getContestHistory().get(0).getContestId()).isEqualTo(0L);
        Assertions.assertThat(userInfoDto.getContestHistory().get(0).getStrategyId()).isEqualTo(0L);

    }
}
