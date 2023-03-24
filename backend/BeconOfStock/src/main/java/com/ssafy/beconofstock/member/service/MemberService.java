package com.ssafy.beconofstock.member.service;

import com.ssafy.beconofstock.member.dto.FollowedDto;
import com.ssafy.beconofstock.member.dto.UserInfoDto;
import com.ssafy.beconofstock.member.entity.Member;

import java.util.List;

public interface MemberService {
    UserInfoDto getUserInfoDto(Long memberId);

    UserInfoDto updateUserInfo(Member member, UserInfoDto userInfoDto);

    List<FollowedDto> getFollows(Member member);

    void saveFollow(Member member, long userId);

    void deleteFollow(Member member, long userId);

    void deleteUser(Member member);
}
