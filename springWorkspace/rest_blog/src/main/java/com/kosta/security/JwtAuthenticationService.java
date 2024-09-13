package com.kosta.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.kosta.domain.response.LoginResponse;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;
import com.kosta.util.TokenUtils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// 목적 - 인증이 성공했을떄 JWT 토큰을 생성하고 클라이언트에게 응답
// 작동방식
// - 사용자 정보를 기반으로 액세스 및 리프레시 토큰 생성
// - 리프레시 토큰을 DB 에 저장하고 쿠키로 클라이언트에게 전송
// 액에스 토큰은 LoginResponse 에 포함되어 JSON 응답으로 전송

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {
	private final TokenUtils tokenUtils;
	private final UserRepository userRepository;

	void successAuthentication(HttpServletResponse response, Authentication authResult) throws IOException {
		User user = (User) authResult.getPrincipal();

		// 토큰 생성
		Map<String, String> tokenMap = tokenUtils.generateToken(user);
		String accessToken = tokenMap.get("accessToken");
		String refreshToken = tokenMap.get("refreshToken");

		// 리프레시 토큰을 DB에 저장
		user.setRefreshToken(refreshToken);
		userRepository.save(user);

		// 생성한 리프레시 토큰을 COOKIE 에 담아 작성
		tokenUtils.setRefreshTokenCookie(response, refreshToken);

		// 생성된 액세스 토큰을 LoginResponse 에 담아 응답 JSON에 포함
		LoginResponse loginResponse = LoginResponse.builder().accessToken(accessToken).build();
		tokenUtils.writeResponse(response, loginResponse);

	};
}
