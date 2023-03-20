package com.ssafy.beconofstock.member.controller;

import com.ssafy.beconofstock.authentication.provider.OAuthUserInfo;
import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.member.dto.FollowedDto;
import com.ssafy.beconofstock.member.dto.UserInfoDto;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.stream.LongStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("user/{userId}")
    public ResponseEntity<UserInfoDto> getUserHistoryInfo(@PathVariable("userId")Long userId){

        UserInfoDto userInfoDto = memberService.getUserInfoDto(userId);

        return new ResponseEntity<>(userInfoDto,HttpStatus.OK);
    }

    @GetMapping("/follows")
    public ResponseEntity<?> getFollows(@AuthenticationPrincipal OAuth2UserImpl oAuth2User){

        log.info("member.getProviderId: {}",oAuth2User.getMember().getProviderId());
        List<FollowedDto> follows = memberService.getFollows(oAuth2User.getMember());

        return new ResponseEntity<>(follows,HttpStatus.OK);
    }


}
