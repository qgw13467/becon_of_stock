package com.ssafy.beconofstock.member.service;

import com.ssafy.beconofstock.member.dto.UserInfoDto;

public interface MemberService {
    UserInfoDto getUserInfoDto(Long memberId);

}
