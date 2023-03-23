package com.ssafy.beconofstock.member.controller;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.member.dto.FollowedDto;
import com.ssafy.beconofstock.member.dto.UserInfoDto;
import com.ssafy.beconofstock.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Member 관련 API"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "회원 정보 조회",  notes =
            "회원 정보를 조회합니다.")
    @GetMapping("user/{userId}")
    public ResponseEntity<UserInfoDto> getUserHistoryInfo(@PathVariable("userId")Long userId){

        UserInfoDto userInfoDto = memberService.getUserInfoDto(userId);

        return new ResponseEntity<>(userInfoDto,HttpStatus.OK);
    }

    @ApiOperation(value = "회원 정보 수정",  notes =
            "회원 정보를 수정합니다.")
    @PutMapping("user")
    public ResponseEntity<UserInfoDto> updateUserInfo(@RequestBody UserInfoDto userInfoDto, @AuthenticationPrincipal OAuth2UserImpl oAuth2User){

        memberService.updateUserInfo(oAuth2User.getMember(), userInfoDto);

        return new ResponseEntity<>(userInfoDto, HttpStatus.CREATED);
    }
    @ApiOperation(value = "회원 탈퇴",  notes =
            "회원을 탈퇴합니다.")
    @DeleteMapping("user")
    public ResponseEntity<UserInfoDto> deleteUser(@AuthenticationPrincipal OAuth2UserImpl oAuth2User){

        memberService.deleteUser(oAuth2User.getMember());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "팔로우 목록 조회",  notes =
            "팔로우 목록을 조회합니다.")
    @GetMapping("/follows")
    public ResponseEntity<?> getFollows(@AuthenticationPrincipal OAuth2UserImpl oAuth2User){

        List<FollowedDto> follows = memberService.getFollows(oAuth2User.getMember());

        return new ResponseEntity<>(follows,HttpStatus.OK);
    }

    @ApiOperation(value = "팔로우 대상 추가", notes=
            "팔로우 대상을 추가합니다.")
    @PostMapping("/follows/{userId}")
    public ResponseEntity<?> saveFollow(@PathVariable("userId") long userId, @AuthenticationPrincipal OAuth2UserImpl oAuth2User){

        memberService.saveFollow(oAuth2User.getMember(), userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "팔로우 대상 삭제", notes=
            "팔로우 대상을 삭제합니다.")
    @DeleteMapping("/follows/{userId}")
    public ResponseEntity<?> deleteFollow(@PathVariable("userId") long userId, @AuthenticationPrincipal OAuth2UserImpl oAuth2User){

        memberService.deleteFollow(oAuth2User.getMember(), userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
