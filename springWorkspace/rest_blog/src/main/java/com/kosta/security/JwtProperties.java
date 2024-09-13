package com.kosta.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

// 목적 = JWT 설정 정보를 application.yml 에서 로드하여 관리
// 작동방식
// - JWT 발급 관련 설정(issuer, secretKey, accessDuration, refreshDuration) 저장


@Getter
@Setter
@Component
@ConfigurationProperties("jwt") // application.yml 에 설정한 게 매핑
public class JwtProperties {
	private String issuer; // 토큰 발급자 
	private String secretKey; // JWT 서명 키
	private int accessDuration; // 액세스 토큰 유효 시간
	private int refreshDuration; // 리프레시 토큰 유효 시간
}
