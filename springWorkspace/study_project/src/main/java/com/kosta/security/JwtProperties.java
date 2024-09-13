package com.kosta.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {

	private String issuer; // 토큰 발급자
	private String secretKey; // JWT 서명 키
	private int accessDuration; // 액세스 토큰 유효 시간
	private int refreshDuration; // 리프레시 토큰 유효 시간

}
