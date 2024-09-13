package com.kosta.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import com.kosta.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 설명 - JWT 토큰 생성 및 검증 기능을 제공
// 작동방식
// - 액세스 및 리프레시 토큰을 생성
// - 토큰을 검증하고, 클레임을 추출하여 사용자 정보를 가져옴


@Slf4j
@Service
@RequiredArgsConstructor
public class JwtProvider {
	private final UserDetailsService userDetailsService;
	// JWT 관련 설정 정보 객체 주입
	private final JwtProperties jwtProperties;

	// JWT Access 토큰 생성
	public String generateAccessToken(User user) {
		log.info("[generateAccessToken] 엑세스 토큰 생성");
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + jwtProperties.getAccessDuration());
		return makeToken(user, expiredDate);
	}

	// JWT Refresh 토큰 생성 메소드
	public String generateRefreshToken(User user) {
		log.info("[generateRefreshToken] 리프레쉬 토큰 생성");
		Date now = new Date();
		Date expiredDate = new Date(now.getTime() + jwtProperties.getRefreshDuration());
		return makeToken(user, expiredDate);
	}

	// 토큰 생성 공통 메소드 (실제로 JWT 토큰 생성)
	private String makeToken(User user, Date expiredDate) {
		String token = Jwts.builder()
				.header().add("typ", "JWT")
				.and()
				.issuer(jwtProperties.getIssuer())
				.issuedAt(new Date())
				.expiration(expiredDate)
				.subject(user.getEmail())
				.claim("id", user.getId())
				.claim("role", user.getRole())
				.signWith(getSecretKey(), Jwts.SIG.HS256)
				.compact();
		log.info("완성된 토큰 : {}", token);
		return token;
	}

	// 비밀키 만드는 메소드
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());
	}

	// 토큰이 유효한지 검증 메소드
	public boolean validateToken(String token) {
		log.info("토큰 검증 시작1");
		try {
			Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
			log.info("토큰 검증 성공1");
			return true;
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
		log.info("토큰 검증 실패1");
		return false;
	}

	// 토큰에서 정보(Claim) 추출 메소드
	private Claims getClaims(String token) {
		return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
	}

	// 토큰에서 인즈 정보 반환하는 메소드
	public Authentication getAuthenticationByToken(String token) {
		String userEmail = getUserEmailByToken(token);
		User user = (User) userDetailsService.loadUserByUsername(userEmail);
		Authentication authentication = new UsernamePasswordAuthenticationToken(user, token, user.getAuthorities());
		return authentication;
	}

	// 토큰에서 사용자 이메일만 추출하는 메소드
	public String getUserEmailByToken(String token) {
		Claims claims = getClaims(token);
		String email = claims.get("sub", String.class);
		return email;
	}

}
