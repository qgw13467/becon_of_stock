package com.ssafy.beconofstock.authentication;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSucessHandler implements AuthenticationSuccessHandler {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2UserImpl oAuth2User = (OAuth2UserImpl) authentication.getPrincipal();

        Optional<Member> memberOptional = memberRepository.findByProviderId(oAuth2User.getUsername());

        Member member = memberOptional.get();

        String shortToken = jwtTokenProvider.generateJwtToken(member);

        Cookie cookie = new Cookie("Authentication", shortToken);
        cookie.setPath("/");
        response.addCookie(cookie);
        cookie.setMaxAge(3600);
//            response.setStatus(302);
    }
}
