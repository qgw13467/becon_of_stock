package com.ssafy.beconofstock.authentication;

import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.authentication.user.PrincipalOAuth2UserService;
import com.ssafy.beconofstock.member.entity.Member;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final PrincipalOAuth2UserService principalOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        try {
            token = jwtTokenProvider.parseJwt(request);
            log.info("request: {}", request.getHeader("Authentication"));
            log.info("=============== token: {}", token);

            if (token != null) {
                String providerId = (String) jwtTokenProvider.getProviderId(token);
                log.info("providerId: {}", providerId);
                UserDetails userDetails = principalOAuth2UserService.loadUserByUsername(providerId);
                log.info("userDetail.getAuthorities: {}", userDetails.getAuthorities());
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

            }
        } catch (BadCredentialsException e) {
//            request.setAttribute("exception", "bad credential");
            loginFailure(request, response);
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
//            request.setAttribute("exception", "jwt expired");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        catch(NeedNicknameAndLocation e){
//            request.setAttribute("exception","NeedNicknameAndLocation");
//            request.setAttribute("token" , token);
//        }

        filterChain.doFilter(request, response);

    }

    protected void loginFailure(HttpServletRequest request, HttpServletResponse response) {
        try {
            HashMap<String, String> result = new HashMap<>();
            result.put("\"msg\"", "\"id or password missmatch\"");
            response.getWriter().print(result);
            response.setStatus(401);
            response.addHeader("msg", "id or password missmatch");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
