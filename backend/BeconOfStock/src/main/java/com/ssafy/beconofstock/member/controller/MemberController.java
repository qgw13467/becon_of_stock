package com.ssafy.beconofstock.member.controller;

import com.ssafy.beconofstock.member.dto.UserInfoDto;
import com.ssafy.beconofstock.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("user/{userId}")
    public ResponseEntity<UserInfoDto> getUserHistoryInfo(@PathVariable("userId")Long userId){

        UserInfoDto userInfoDto = memberService.getUserInfoDto(userId);


        return new ResponseEntity<>(userInfoDto,HttpStatus.OK);
    }
}
