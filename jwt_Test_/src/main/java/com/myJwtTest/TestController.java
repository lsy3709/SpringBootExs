package com.myJwtTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestController {
	// 참고 블로그
	// https://velog.io/@haerong22/JWT
	// 공식 문서 자바 버전 jwt 샘플 코드
	//https://github.com/auth0/java-jwt

	/*
	 * 인증 타입 
	 * Basic 사용자 아이디와 암호를 Base64로 인코딩한 값을 토큰으로 사용한다. (RFC 7617)
	 * 
	 * Bearer 
	 * JWT 혹은 OAuth에 대한 토큰을 사용한다. (RFC 6750)
	 */

	//  생성자 호출시 임의의 유저를 한명 만듦.  kim/1234
	private final TestRepository testRepository;

	@PostMapping("/login")
	// 요청에서 username : kim, password : 1234 요청을 받으면
	// testRepository.login(loginReq) 에서 판별 
	// 참이면
	// ResponseEntity.status(HttpStatus.OK) 형으로 반환
	// 헤더에 추가 
	// 토큰 키 : token , 값 : JwtUtils.generateToken(loginReq.getUsername())
	//
	public ResponseEntity<?> login(LoginReq loginReq) {
		return testRepository.login(loginReq)
				? ResponseEntity.status(HttpStatus.OK).header("token", JwtUtils.generateToken(loginReq.getUsername()))
						.body("success")
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
	}

	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}
}