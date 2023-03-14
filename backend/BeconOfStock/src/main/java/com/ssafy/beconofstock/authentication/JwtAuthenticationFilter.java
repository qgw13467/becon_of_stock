package com.ssafy.beconofstock.authentication;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.beconofstock.authentication.user.OAuth2UserImpl;
import com.ssafy.beconofstock.authentication.user.PrincipalOAuth2UserService;
import com.ssafy.beconofstock.member.entity.Member;
import lombok.RequiredArgsConstructor;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final PrincipalOAuth2UserService principalOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        Member member = new Member();
        String token = "";
        try {
            token = jwtTokenProvider.parseJwt(request);
            if (token != null && jwtTokenProvider.isValidate(token)) {
                String username = jwtTokenProvider.getUsername(token);

                UserDetails userDetails = principalOAuth2UserService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                OAuth2UserImpl oAuth2User = (OAuth2UserImpl) userDetails;
                member = oAuth2User.getMember();

                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

            }
        } catch (BadCredentialsException e) {
            loginFailure(request, response);
        }
//        catch(NeedNicknameAndLocation e){
//            request.setAttribute("exception","NeedNicknameAndLocation");
//            request.setAttribute("token" , token);
//        }
        catch (Exception e) {
            e.printStackTrace();
        }

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
