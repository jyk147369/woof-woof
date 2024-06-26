package com.woof.api.utils;

import com.woof.api.member.model.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


public class JwtUtils {

    // 일반 로그인 사용자 토큰 생성
    public static String generateAccessToken(Member member, String secretKey, Long expiredTimeMs) {

        Claims claims = Jwts.claims();
        claims.put("memberIdx", member.getIdx());
        claims.put("memberEmail", member.getMemberEmail());
        //claims.put("memberName", member.getMemberName());
        claims.put("memberNickname", member.getMemberNickname());
        claims.put("ROLE", member.getAuthority());

        byte[] secretBytes = secretKey.getBytes();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTimeMs))
                .signWith(Keys.hmacShaKeyFor(secretBytes), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    // 키 변환 메서드
    public static Key getSignKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 사용자 이름 가져오는 메서드
    public static String getUserMemberEmail(String token, String key) {
        return extractAllClaims(token, key).get("memberEmail", String.class);
    }

    public static String getMemberIdx(String token, String key) {
        return extractAllClaims(token, key).get("memberIdx", String.class);
    }

    public static String getAuthority(String token, String key) {
        return extractAllClaims(token, key).get("ROLE", String.class);
    }

    public static String checkJwtToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.split(" ")[1];
            return token;
        } /*else {
            throw MemberAccountException.forInvalidToken();
        }*/
        return token;
    }

    // 토근에서 정보를 가져오는 코드가 계속 중복되어 사용되기 때문에 별도의 메서드로 만들어서 사용하기 위한 것
    public static Claims extractAllClaims(String token, String key) {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey(key))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(getSignKey(key))
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (SignatureException e) {
//            throw MemberAccountException.forInvalidToken();
//        } catch (ExpiredJwtException e) {
//            throw MemberAccountException.forExpiredToken();
//        }
    }
}
