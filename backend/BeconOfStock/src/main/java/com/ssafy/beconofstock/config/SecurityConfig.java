package com.ssafy.beconofstock.config;

import com.ssafy.beconofstock.authentication.JwtAuthenticationFilter;
import com.ssafy.beconofstock.authentication.JwtTokenProvider;

import java.util.Arrays;
import java.util.Collections;

import com.ssafy.beconofstock.authentication.OAuth2AuthenticationSucessHandler;
import com.ssafy.beconofstock.authentication.user.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final PrincipalOAuth2UserService principalOAuth2UserService;
    private final OAuth2AuthenticationSucessHandler oAuth2AuthenticationSucessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                        .antMatchers("/login/oauth2/code/kakao").permitAll()
//                        .anyRequest().authenticated();
        http.authorizeRequests()
                        .anyRequest().authenticated();

        http
                .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.oauth2Login()
                .userInfoEndpoint() // 필수
                .userService(principalOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSucessHandler);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000", "http://j8d207.p.ssafy.io/", "http://localhost:8080"
        ));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Accept",
                "Accept-Language",
                "Authorization",
                "Content-Language",
                "Content-Type"
        ));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
