package com.kosta.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// 목적 - JWT 토큰을 검증하고, 유효한 경우 SecurityContext 에 인증 정보를 설정
// 작동방식
// - 요청 헤더에서 Authorization 헤더를 읽는다
// - Bearer 로 시작하는 토큰을 추출
// - JWT 토큰을 검증하고, 유효하면 Authentication 객체를 생성하여 SecurityContext에 설정



@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private final static String HEADER_AUTHORIZATION = "Authorization";
	private final static String TOKEN_PREFIX = "Bearer ";

	// HTTP 요청이 들어올 떄마다 실행되는 필터
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// Authorization 헤더에서 토큰 추출
		String header = request.getHeader(HEADER_AUTHORIZATION);
		String token = getAccessToken(header);
		
		// 토큰 검증 및 인증 정보 설정
		if (jwtProvider.validateToken(token)) {
			Authentication authentication = jwtProvider.getAuthenticationByToken(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		// 그 다음 요청 처리 체인을 이어서 진행
		filterChain.doFilter(request, response);
	}

	private String getAccessToken(String header) {
		log.info("[getAccessTOken] 토큰 값 추출, {}", header);
		if (header != null && header.startsWith(TOKEN_PREFIX)) {
			return header.substring(TOKEN_PREFIX.length());
		}
		return null;
	}
}
