package com.ssafy.beconofstock.member;

import com.ssafy.beconofstock.board.repository.BoardRepository;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.contest.repository.ContestMemberRepository;
import com.ssafy.beconofstock.member.dto.FollowedDto;
import com.ssafy.beconofstock.member.dto.UserInfoDto;
import com.ssafy.beconofstock.member.entity.Follow;
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

import javax.persistence.SecondaryTable;
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
    @Mock
    private BoardRepository boardRepository;
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
        memberService = new MemberServiceImpl(memberRepository,contestMemberRepository,followRepository,boardRepository);

        //when
        UserInfoDto userInfoDto = memberService.getUserInfoDto(1L);

        //then
        Assertions.assertThat(userInfoDto.getNickname()).isEqualTo("test");
        Assertions.assertThat(userInfoDto.getContestHistory().get(0).getContestId()).isEqualTo(0L);
        Assertions.assertThat(userInfoDto.getContestHistory().get(0).getStrategyId()).isEqualTo(0L);

    }

    @Test
    void getFollowTest(){
        //given
        Member following = TestUtil.getMember(1L,"kakao_1","following");
        Member followed = TestUtil.getMember(2L,"kakao_2","followed");
        Follow follow = TestUtil.getFollow(1L,following, followed);

        Mockito.lenient().doReturn(List.of(follow)).when(followRepository).findByFollowing(following);
        memberService = new MemberServiceImpl(memberRepository,contestMemberRepository,followRepository, boardRepository);
        //when
        List<FollowedDto> follows = memberService.getFollows(following);

        //then
        Assertions.assertThat(follows.get(0).getNickname()).isEqualTo("followed");

    }
}
