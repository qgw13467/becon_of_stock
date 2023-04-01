package com.ssafy.beconofstock.config;

import com.ssafy.beconofstock.authentication.JwtAuthenticationFilter;
import com.ssafy.beconofstock.authentication.JwtTokenProvider;

import java.util.Arrays;
import java.util.Collections;

import com.ssafy.beconofstock.authentication.OAuth2AuthenticationSucessHandler;
import com.ssafy.beconofstock.authentication.user.PrincipalOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final PrincipalOAuth2UserService principalOAuth2UserService;
    private final OAuth2AuthenticationSucessHandler oAuth2AuthenticationSucessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/oauth2/**").permitAll()
                .antMatchers("/login/oauth2/code/kakao").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers(
                        "/",
                        "/user/signup",
                        "/user/login",
                        "/user/oauth2",
                        "/oauth2/**",
                        "/user/refresh",    // accessToken 재발급
                        "/user/redirectTest",
                        "/user/check/**",
                        "/user/validate/**",
                        // Swagger 관련 URL
                        "/v2/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger/**",
                        "/sign-api/exception/**"
                ).permitAll()
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
                .authorizationEndpoint()
                .baseUri("/api/oauth2/authorization")
                .and()
                .userInfoEndpoint() // 필수
                .userService(principalOAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSucessHandler);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                });
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
                "Authentication",
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
