package com.zerobase.stockdividend.Security;

import com.zerobase.stockdividend.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final MemberService memberService;

    private static final String KEY_ROLES = "roles";
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1 hour
    @Value("{spring.jwt.secret}")
    private String secretKey;

    public String generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now) // 토큰 생성 시간
            .setExpiration(expiredDate) // 토큰 만료 시간
            .signWith(SignatureAlgorithm.HS512, secretKey) // 사용할 암호화 알고리즘, 비밀키
            .compact();
    }

    // MemberEntity에서 @ElementCollection 사용하면 트랜잭션 열어야 읽기 가능.
//    @Transactional(readOnly = true)
    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = memberService.loadUserByUsername(getUserName(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
            userDetails.getAuthorities());
    }

    public String getUserName(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        var claims = parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
