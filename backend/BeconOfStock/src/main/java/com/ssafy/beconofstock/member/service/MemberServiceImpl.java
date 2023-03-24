package com.ssafy.beconofstock.member.service;

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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final ContestMemberRepository contestMemberRepository;
    private final FollowRepository followRepository;

    @Override
    public UserInfoDto getUserInfoDto(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(UserNotFoundException::new);

        List<ContestMember> contestHistoryDtoList = contestMemberRepository.findByMemberFetch(member);

        List<ContestHistoryDto> contestHistoryDtos = contestHistoryDtoList.stream()
                .map(ContestHistoryDto::new).collect(Collectors.toList());

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setNickname(member.getNickname());
        userInfoDto.setFollowNum(member.getFollowNum());
        userInfoDto.setContestHistory(contestHistoryDtos);
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
    public void saveFollow(Member member, long userId) {

        Member followedMember = memberRepository.findById(userId).orElseThrow(() -> new NotFoundException());
        Follow follow = Follow.builder().following(member).followed(followedMember).build();

        if (followRepository.findByFollowingAndFollowed(member, followedMember) == null) {
            followRepository.save(follow);
        }
    }

    @Override
    public void deleteFollow(Member member, long followedId) {
        Member followedMember = memberRepository.findById(followedId).orElseThrow(() -> new NotFoundException());

        Follow follow = followRepository.findByFollowingAndFollowed(member, followedMember);

        if (follow.getId() != null) {
            followRepository.delete(follow);
        }
    }


}
