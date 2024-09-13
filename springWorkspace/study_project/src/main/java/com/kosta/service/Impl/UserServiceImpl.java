package com.kosta.service.Impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.domain.request.UserDeleteRequest;
import com.kosta.domain.request.UserJoinRequest;
import com.kosta.domain.request.UserUpdateRequest;
import com.kosta.domain.response.UserResponse;
import com.kosta.entity.User;
import com.kosta.repository.UserRepository;
import com.kosta.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	// 비밀번호 암호화를 위한 클래스 BCryptPasswordEncoder
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	// 사용자 정보를 DB 저장하거나 조회 UserRepository
	private final UserRepository userRepository;

	// 회원가입
	@Override
	public UserResponse join(UserJoinRequest join) {
		// 비밀번호를 암호화 하여 저장
		String encodedPassword = bCryptPasswordEncoder.encode(join.getPassword());
		// User 객체를 생성 (이메일 , 이름 , 암호화된 비밀번호)
		log.info("[encodedPassword] 비밀번호 암호화 진행  입력된 암호 : " + join.getPassword() + " / 암호화 : " + encodedPassword);
		User user = User.builder().email(join.getEmail()).name(join.getName()).password(encodedPassword).build();
		log.info("회원가입 정보 : {}", user);
		// 사용자 정보를 DB 저장
		User savedUser = userRepository.save(user);
		// 저장된 사용자 정보를 UserResponse 객체로 변환하여 저장
		return UserResponse.toDTO(savedUser);
	}

	// 이메일 중복 체크
	@Override
	public boolean duplicateCheckEmail(String email) {
		// 이메일이 이미 존재하면 false 반환 , 존재하지 않으면 true 반환
		return !userRepository.existsByEmail(email);
	}

	// 사용자 정보 수정 기능
	@Override
	public UserResponse updateUser(UserUpdateRequest updateUser) {
		// email 로 사용자를 조회
		User user = userRepository.findByEmail(updateUser.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("없는 이메일"));
		log.info("[isMatchPassword] 비밀번호 확인 진행");
		// 비밀번호 확인
		boolean isMatchPassword = bCryptPasswordEncoder.matches(updateUser.getPassword(), user.getPassword());
		if (!isMatchPassword)
			throw new RuntimeException("비밀번호 불일치");
		// 새로운 이름으로 사용자 수정
		if (updateUser.getName() != null)
			user.setName(updateUser.getName());
		// 수정된 사용자 정보 DB에 저장
		User updatedUser = userRepository.save(user);
		// 수정된 사용자 정보 UserResponse 객체로 변환하여 반환
		return UserResponse.toDTO(updatedUser);
	}

	// 사용자 삭제 기능
	@Override
	public void deleteUser(UserDeleteRequest deleteUser) {
		// email 로 사용자를 조회
		User user = userRepository.findByEmail(deleteUser.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("없는 이메일"));
		log.info("[isMatchPassword] 비밀번호 확인 진행");
		// 비밀번호 확인
		boolean isMatchPassWord = bCryptPasswordEncoder.matches(deleteUser.getPassword(), user.getPassword());
		if (!isMatchPassWord)
			throw new RuntimeException("비밀번호 불일치");
		// 사용자 삭제
		userRepository.delete(user);
	}
}
