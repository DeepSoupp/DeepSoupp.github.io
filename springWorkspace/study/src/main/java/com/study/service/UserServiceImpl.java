package com.study.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.domain.UserDTO;
import com.study.entity.User;
import com.study.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	// UserRepository 사용자 정보를 데이터베이스에 저장 , 조회
	private final UserRepository ur;
	private final BCryptPasswordEncoder bc;

	@Override
	public void join(UserDTO userDTO) {
		String password = userDTO.getPassword();
		String enecodePassword = bc.encode(password);
		userDTO.setPassword(enecodePassword);
		ur.save(userDTO.setUser());
	}

	@Override
	public void save(User user) {
		ur.save(user);
	}

}
