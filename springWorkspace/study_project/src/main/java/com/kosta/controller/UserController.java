package com.kosta.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.domain.request.UserDeleteRequest;
import com.kosta.domain.request.UserJoinRequest;
import com.kosta.domain.request.UserUpdateRequest;
import com.kosta.domain.response.UserResponse;
import com.kosta.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	// 사용자의 가입 요청을 처리
	// UserJoinRequest 사용자의 가입 정보를 담고 있는 요청 객체
	// 가입된 사용자 정보를 담고 있는 ResponseEntity<UserResponse> 객체 반환
	@PostMapping("join")
	public ResponseEntity<UserResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
		// 요청 정보를 로그로 출력
		log.info("[userJoin] 회원가입 진행, 요청정보 : {}", userJoinRequest);
		// 사용자 가입 처리
		UserResponse joinUser = userService.join(userJoinRequest);
		// 가입된 사용자 정보를 포함한 응답을 생성 (HTTP 상태 코드: 201 Created)
		return ResponseEntity.status(HttpStatus.CREATED).body(joinUser);
	}

	// 이메일 중복 체크 API
	@GetMapping("/duplicate")
	public ResponseEntity<Boolean> emailCheck(@RequestParam("email") String email) {
		log.info("[emailCheck] 이메일 중복체크 진행, 요청정보 : {}", email);
		// UserService 를 통해 email 중복체크
		boolean isNotDuplicate = userService.duplicateCheckEmail(email);
		log.info("[emailCheck] 이메일 중복체크 결과 : " + isNotDuplicate);
		// 중복 여부를 포함한 응답을 반환
		return ResponseEntity.ok(isNotDuplicate);
	}

	// 회원 정보 수정 API
	@PatchMapping
	public ResponseEntity<UserResponse> updateUser(@RequestBody UserUpdateRequest updateRequest) {
		// 사용자 정보 수정 처리
		UserResponse updateUser = userService.updateUser(updateRequest);
		// 수정된 사용자 정보를 포함한 응답 반환
		return ResponseEntity.ok(updateUser);
	}

	// 회원 삭제 API
	@DeleteMapping("")
	public ResponseEntity<UserResponse> userDelete(@RequestBody UserDeleteRequest deleteRequest) {
		// 사용자 삭제 처리
		userService.deleteUser(deleteRequest);
		// 성공 응답 반환 (HTTP 상태 코드 : 200 OK)
		return ResponseEntity.ok(null);
	}
}
