package com.ssafy.beconofstock.authentication.user;

import com.ssafy.beconofstock.authentication.provider.KakaoOAuthUserInfo;
import com.ssafy.beconofstock.authentication.provider.OAuthUserInfo;
import com.ssafy.beconofstock.member.entity.Member;
import com.ssafy.beconofstock.member.repository.MemberRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.query.spi.ReturnMetadata;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = getOAuth2User(userRequest);

        OAuthUserInfo oAuthUserInfo = null;

        String provider = userRequest.getClientRegistration().getRegistrationId();

        oAuthUserInfo = new KakaoOAuthUserInfo(oAuth2User.getAttributes());

        String providerId = provider + "_" + oAuthUserInfo.getProviderId();

        Optional<Member> findUser = memberRepository.findByProviderId(providerId);

        Member member;

        if (findUser.isEmpty()) {
            member = new Member(oAuthUserInfo);
            memberRepository.save(member);
        } else {
            member = findUser.get();
            if (!oAuthUserInfo.getProfileImg().equals("")) {
                member.setProfileImg(oAuthUserInfo.getProfileImg());
            }
        }

        return new OAuth2UserImpl(member, oAuthUserInfo);
    }

    public OAuth2User getOAuth2User(OAuth2UserRequest userRequest) {
        return super.loadUser(userRequest);
    }

    @Override
    public UserDetails loadUserByUsername(String providerId) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByProviderId(providerId);
        if (member.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        return new OAuth2UserImpl(member.get());


    }


}
