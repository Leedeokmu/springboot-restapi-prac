package com.freeefly.restapiprac.config.security;

import com.freeefly.restapiprac.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final Long tokenValidMilisecond = 1000L * 60 * 60;
    private String authTokenHeader = "X-AUTH-TOKEN";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // 토큰 생성
    public String createToken(String userPk, List<UserRole> roles){
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles.stream().map(UserRole::name).collect(Collectors.toList()));
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
                ;
    }

    // Jwt 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // Jwt 토큰에서 회원 구별 정보 추츨
    private String getUserPk(String token) {
        return getClaims(token).getBody().getSubject();
    }

    // request의 Header  에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authTokenHeader);
    }

    // Jwt 토큰의 유효성 검증(유효성 + 만료일자)
    public boolean validateToken(String jwtToken) {
        try {
            Date now = new Date();
            Jws<Claims> claims = getClaims(jwtToken);
            return !claims.getBody().getExpiration().before(now);
        } catch (Exception e) {
            return false;
        }
    }

    private Jws<Claims> getClaims(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }
}
