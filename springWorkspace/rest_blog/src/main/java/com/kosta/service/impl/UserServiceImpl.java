package com.kosta.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.domain.request.UserDeleteRequest;
import com.kosta.domain.request.UserSignUpRequest;
import com.kosta.domain.request.UserUpdateRequest;
import com.kosta.domain.response.UserResponse;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;
import com.kosta.security.JwtProvider;
import com.kosta.service.UserService;
import com.kosta.util.TokenUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtProvider jwtProvider;
	private final TokenUtils tokenUtils;

	@Override
	public UserResponse signUp(UserSignUpRequest sign) {
		String encodedPassword = bCryptPasswordEncoder.encode(sign.getPassword());
		User user = User.builder().email(sign.getEmail()).name(sign.getName()).password(encodedPassword).build();
		User savedUser = userRepository.save(user);
		return UserResponse.toDTO(savedUser);

	}

	@Override
	public List<UserResponse> getUserList() {
		List<User> userList = userRepository.findAll();
		return userList.stream().map(UserResponse::toDTO).toList();
	}

	@Override
	public UserResponse updateUser(UserUpdateRequest update) {
		User user = userRepository.findByEmail(update.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("없는 이메일"));
		boolean isMatch = bCryptPasswordEncoder.matches(update.getPassword(), user.getPassword());
		if (!isMatch) {
			throw new RuntimeException("비밀번호 떙");
		}
		if (update.getName() != null) {
			user.setName(update.getName());
		}
		User updatedUser = userRepository.save(user);
		return UserResponse.toDTO(updatedUser);
	}

	@Override
	public void deleteUser(UserDeleteRequest userDeleteRequest) {
		User user = userRepository.findByEmail(userDeleteRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("실패!!!!"));
		boolean isMatch = bCryptPasswordEncoder.matches(userDeleteRequest.getPassword(), user.getPassword());
		if (!isMatch) {
			throw new RuntimeException("비밀번호 실패!!!");
		}
		userRepository.delete(user);
	}

	@Override
	public boolean duplicateCheckEmail(String email) {
		return !userRepository.existsByEmail(email);
	}

	private String extractRefreshTokenFromCookie(HttpServletRequest req) {
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("refreshToken")) {
					return c.getValue();

				}
			}
		}
		return null;
	}

	@Override
	public Map<String, String> refreshToken(HttpServletRequest req) {
		// 쿠키에서 RefreshToken 추출
		String refreshToken = extractRefreshTokenFromCookie(req);

		// 만약 토큰이 유효하지 않으면 null
		if (refreshToken == null || !jwtProvider.validateToken(refreshToken)) {
			return null;
		}
		//
		String userEmail = jwtProvider.getUserEmailByToken(refreshToken);
		User user = userRepository.findByEmail(userEmail).orElse(null);
		if (user == null || !user.getRefreshToken().equals(refreshToken)) {
			return null;
		}
		// 새로운 토큰 생성 및 저장
		Map<String, String> tokenMap = tokenUtils.generateToken(user);
		user.setRefreshToken(tokenMap.get("refreshToken"));
		userRepository.save(user);

		return tokenMap;
	}

}
