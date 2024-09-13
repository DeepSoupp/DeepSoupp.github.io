package com.kosta.service;

import java.util.List;
import java.util.Map;

import com.kosta.domain.request.UserDeleteRequest;
import com.kosta.domain.request.UserSignUpRequest;
import com.kosta.domain.request.UserUpdateRequest;
import com.kosta.domain.response.LoginResponse;
import com.kosta.domain.response.UserResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	UserResponse signUp(UserSignUpRequest sign);

	List<UserResponse> getUserList();

	UserResponse updateUser(UserUpdateRequest update);

	void deleteUser(UserDeleteRequest userDeleteRequest);
	
	boolean duplicateCheckEmail(String email);
	// 토큰 갱신
	Map<String, String> refreshToken(HttpServletRequest req);
}
