package com.ssafy.beconofstock.authentication;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.ssafy.beconofstock.member.entity.Member;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

    //    @Value("${JWT.SECRET}")
    private String securityKey ="as09df8h0e8fhs0d8fhs08fh0sd8fhse08fhs0ef8hse08fhse08fhse0f8hq08f";

    private final Long shortTokeneExpiredTime = 1000 * 60 * 60 * 24L;
    private final Long longTokenExpiredTime = 1000 * 60 * 60 * 24 * 7L;

    public String generateJwtToken(Member member) {
        Date now = new Date();
        log.info("[generate token]time={}", LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        return Jwts.builder()
                .setSubject(member.getNickname()) // 보통 username
                .setHeader(createHeader())
                .setClaims(createClaims(member)) // 클레임, 토큰에 포함될 정보
                .setExpiration(new Date(now.getTime() + shortTokeneExpiredTime)) // 만료일
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();
    }

    public String generateJwtFromToken(String token){
        Date now = new Date();
        return Jwts.builder()
                .setSubject(getUsername(token)) // 보통 username
                .setHeader(createHeader())
                .setClaims(createClaims(token)) // 클레임, 토큰에 포함될 정보
                .setExpiration(new Date(now.getTime() + shortTokeneExpiredTime)) // 만료일
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();

    }

    public String generateRefreshJwtToken(Member member) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(member.getNickname()) // 보통 username
                .setHeader(createHeader())
                .setClaims(createLongClaims()) // 클레임, 토큰에 포함될 정보
                .setExpiration(new Date(now.getTime() + longTokenExpiredTime)) // 만료일
                .signWith(SignatureAlgorithm.HS256, securityKey)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256"); // 해시 256 사용하여 암호화
        header.put("regDate", System.currentTimeMillis());
        return header;
    }

    private Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", member.getId());
        claims.put("providerId", member.getProviderId());
        claims.put("nickname", member.getNickname());
        claims.put("role", member.getRole());

        return claims;
    }

    private Map<String, Object> createClaims(String token){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", getId(token));
        claims.put("providerId", getProviderId(token));
        claims.put("nickname", getNickname(token));
        claims.put("role", getRole(token));
        return claims;

    }

    private Map<String, Object> createLongClaims() {
        Map<String, Object> claims = new HashMap<>();
        return claims;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(securityKey).build().parseClaimsJws(token).getBody();
    }
    public Object getId(String token){
        return getClaims(token).get("id");
    }
    public Object getProviderId(String token){
        return getClaims(token).get("providerId");
    }
    public Object getNickname(String token) {
        return getClaims(token).get("nickname");
    }

    public String getUsername(String token){
        return (String) getClaims(token).get("username");
    }
    public Object getProfile(String token){
        return getClaims(token).get("profile");
    }

    public Object getLocaltion(String token){
        return getClaims(token).get("localtion");
    }

    public String getRole(String token){
        return (String)getClaims(token).get("role");

    }

    public Boolean isValidate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(securityKey).build().parseClaimsJws(token).getBody();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.");
        }
        return false;
    }

    public String parseJwt(HttpServletRequest request){
        String headerAuth=null;

        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authentication")) {
                    headerAuth = cookie.getValue();
                    break;
                }
            }

            if(headerAuth!=null){
                return headerAuth;
            }
        }

        headerAuth = request.getHeader("Authentication");
        return headerAuth;

    }


}
