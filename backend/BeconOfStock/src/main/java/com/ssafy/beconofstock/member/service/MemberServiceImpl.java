package com.ssafy.beconofstock.member.service;

import com.ssafy.beconofstock.board.repository.BoardRepository;
import com.ssafy.beconofstock.contest.dto.ContestHistoryDto;
import com.ssafy.beconofstock.contest.entity.ContestMember;
import com.ssafy.beconofstock.contest.repository.ContestMemberRepository;
import com.ssafy.beconofstock.exception.NotFoundException;
import com.ssafy.beconofstock.exception.UserNotFoundException;
import com.ssafy.beconofstock.member.dto.FollowedDto;
import com.ssafy.beconofstock.member.dto.UserInfoDto;
import com.ssafy.beconofstock.member.entity.Follow;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.member.repository.FollowRepository;
import com.ssafy.beconofstock.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ContestMemberRepository contestMemberRepository;
    private final FollowRepository followRepository;
    private final BoardRepository boardRepository;

    @Override
    public UserInfoDto getUserInfoDto(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(UserNotFoundException::new);

        List<ContestMember> contestHistoryDtoList = contestMemberRepository.findByMemberFetch(member);

        List<ContestHistoryDto> contestHistoryDtos = contestHistoryDtoList.stream()
                .map(ContestHistoryDto::new).collect(Collectors.toList());

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setNickname(member.getNickname());
        userInfoDto.setFollowNum(member.getFollowNum());
        userInfoDto.setFollowerNum(member.getFollowerNum());
        userInfoDto.setContestHistory(contestHistoryDtos);
        userInfoDto.setPostNum(boardRepository.countByMember(member));
        return userInfoDto;
    }

    @Override
    public UserInfoDto updateUserInfo(Member member, UserInfoDto userInfoDto) {

        member.setNickname(userInfoDto.getNickname());
        return new UserInfoDto(memberRepository.save(member));
    }

    @Override
    public void deleteUser(Member member) {

        member.setExpired(true);
        memberRepository.save(member);
    }

    @Override
    public List<FollowedDto> getFollows(Member member) {

        List<Follow> follows = followRepository.findByFollowing(member);

        List<FollowedDto> result = follows.stream()
                .map(FollowedDto::new)
                .collect(Collectors.toList());
        return result;
    }

    @Override
    @Transactional
    public void saveFollow(Member member, long userId) {

        member.setFollowNum(member.getFollowNum() + 1);
        memberRepository.save(member);

        Member followedMember = memberRepository.findById(userId).orElseThrow(NotFoundException::new);
        followedMember.setFollowerNum(followedMember.getFollowNum() + 1);

        Follow follow = Follow.builder().following(member).followed(followedMember).build();

        if (followRepository.findByFollowingAndFollowed(member, followedMember) == null) {
            followRepository.save(follow);
        }


    }

    @Override
    @Transactional
    public void deleteFollow(Member member, long followedId) {

        member.setFollowNum(member.getFollowNum() - 1);
        memberRepository.save(member);

        Member followedMember = memberRepository.findById(followedId).orElseThrow(NotFoundException::new);

        Follow follow = followRepository.findByFollowingAndFollowed(member, followedMember);
        followedMember.setFollowerNum(followedMember.getFollowerNum() - 1);

        if (follow.getId() != null) {
            followRepository.delete(follow);
        }
    }


}
