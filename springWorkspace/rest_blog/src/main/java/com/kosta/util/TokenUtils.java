package com.kosta.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.domain.response.LoginResponse;
import com.kosta.entity.User;
import com.kosta.security.JwtProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenUtils {
	private final JwtProvider jwtProvider;

	// 토큰 생성
	public Map<String, String> generateToken(User user) {
		String accessToken = jwtProvider.generateAccessToken(user);
		String refreshToken = jwtProvider.generateRefreshToken(user);
		
		Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("accessToken", accessToken);
		tokenMap.put("refreshToken", refreshToken);
		return tokenMap;
	}

	// JSON 응답 전송
	public void writeResponse(HttpServletResponse response, LoginResponse loginResponse) throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = objectMapper.writeValueAsString(loginResponse);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonResponse);
	}

	public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
		Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
		refreshTokenCookie.setHttpOnly(true); // 자스에서 변경불가
		refreshTokenCookie.setSecure(false); // http 에서도 사용가능
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setMaxAge(1 * 24 * 60);
		response.addCookie(refreshTokenCookie);
		
	}
}
