package com.kosta.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.kosta.domain.response.LoginResponse;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;
import com.kosta.utils.TokenUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {
	private final TokenUtils tokenUtils;
	private final UserRepository userRepository;

	void successAuthentication(HttpServletResponse res, Authentication authResult) throws IOException {
		User user = (User) authResult.getPrincipal();

		// 토큰 생성
		Map<String, String> tokenMap = tokenUtils.generateToken(user);
		String accessToken = tokenMap.get("accessToken");
		String refreshToken = tokenMap.get("refreshToken");

		// 리프레시 토큰을 DB 에 저장
		user.setRefreshToken(refreshToken);
		userRepository.save(user);

		// 생성한 리프레시 토큰을 COOKIE 에 담아 작성
		tokenUtils.setRefreshTokenCookie(res, refreshToken);

		// 생성한 액세스 토큰을 LoginResponse 에 담아 응답 JSON에 포함
		LoginResponse loginResponse = LoginResponse.builder().accessToken(accessToken).build();
		tokenUtils.writeResponse(res, loginResponse);
	}
}
