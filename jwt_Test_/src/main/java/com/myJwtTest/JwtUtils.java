package com.myJwtTest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {

    private JwtUtils() {}

    // 토큰 생성
    // 일단, 테스트용 으로 username : kim 으로 들어와야 알고리즘 만들기로 수행. 
    public static String generateToken(String username) {
        // HS256 알고리즘
    	// 임시 비밀키는 secret 
    	// withIssuer("k") : 발행자 k 임시로
    	// withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())) : 발행 시간 기본 시간을 객체로 전달.
    	// withExpiresAt(Date.from(LocalDateTime.now().plusDays(1L).atZone(ZoneId.systemDefault()).toInstant())) : 1L 하루 뒤 만료.
    	// withClaim("username", username) : kim 으로 페이로드에 담음
    	// 헤더와 페이로드 비밀키 3개를 가지고 HS256 알고리즘 를 사용하여 해싱함.-> 서명이 만들어짐. 
    	//토큰 구조 형식 : 헤더.페이로드.서명
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuer("k")
                .withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .withExpiresAt(Date.from(LocalDateTime.now().plusDays(1L).atZone(ZoneId.systemDefault()).toInstant()))
                .withClaim("username", username)
                .sign(algorithm);
    }

    // 토큰 검증
    // 공식 문서 기본 샘플 코드 
    public static boolean verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        try {
            DecodedJWT verify = JWT.require(algorithm)
                    .build().verify(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}