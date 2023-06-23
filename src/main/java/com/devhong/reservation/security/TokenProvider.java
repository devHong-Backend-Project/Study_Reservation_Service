package com.devhong.reservation.security;

import com.devhong.reservation.service.AuthService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1 hour 토큰 유효시간
    private static final String KEY_ROLES = "roles";

    private final AuthService authService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    /*
        jwt 토큰 생성
        jwt 바디에는 username, 토큰발행시간, 토큰만료시간, 유저의 권한들이  들어감
     */
    public String generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /*
        user가 가지고있는 권한들을 Authentication 객체에 주입해서 리턴
        클라이언트 요청시 JwtAuthenticationFilter에서 사용됨.
     */
    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = authService.loadUserByUsername(getUsername(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /*
        토큰의 바디에서 username 추출
     */
    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    /*
        토큰 유효성 검증 (클라이언트에서 api 요청시 사용됨)
        1. 토큰이 헤더에 안실려왔으면 false
        2. 토큰의 유효시간이 지났으면 false
     */
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) return false;

        Claims claims = parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    /*
        jwt 토큰의 바디내용 가져오는 메서드
     */
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
