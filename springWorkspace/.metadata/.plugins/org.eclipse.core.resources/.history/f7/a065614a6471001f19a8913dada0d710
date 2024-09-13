package com.kosta.security;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.domain.request.LoginRequest;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

//목적 - 로그인 요청을 처리하고, 인증이 성공하면 JWT 토큰을 발급
//작동방식
//- 로그인 요청에서 사용자 정보를 읽어 UsernamePasswordAuthenticationToken 생성
//- 인증 매니저를 통해 인증을 수행하고, 성공적인 인증 후 JwtAuthenticationService 통해 JWT 토큰발행

@Slf4j
public class LoginCustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final AntPathRequestMatcher LOGIN_PATH = new AntPathRequestMatcher("/api/user/login", "POST");
	private JwtAuthenticationService jwtAuthenticationService;

	public LoginCustomAuthenticationFilter(AuthenticationManager authenticationManager,
			JwtAuthenticationService jwtAuthenticationService) {
		super(LOGIN_PATH);
		setAuthenticationManager(authenticationManager);
		this.jwtAuthenticationService = jwtAuthenticationService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// POST, /api/user/login 에 요청이 들어오면 진행되는 곳
		LoginRequest loginRequest = null;
		// 1. body 에 있는 로그인 정도
		try {
			log.info("[attemptAuthentication] 로그인 정보 가져오기");
			ObjectMapper objectMapper = new ObjectMapper();
			loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
		} catch (Exception e) {
			throw new RuntimeException("로그인 요청 파라미터 이름 확인 필요");
		}
		// 2. email password 를 기반으로 AuthenticationToken 생성
		log.info("[attemptAuthentication] 토큰생성");
		UsernamePasswordAuthenticationToken uPAT = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
				loginRequest.getPassword());
		// 3. 인증시작
		log.info("[attemptAuthentication] 인증 시작");
		Authentication authentication = getAuthenticationManager().authenticate(uPAT);
		return authentication;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		log.info("[successfulAuthentication] 로그인 성공");
		jwtAuthenticationService.successAuthentication(response, authResult);
	}

}
