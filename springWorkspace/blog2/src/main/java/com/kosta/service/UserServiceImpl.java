package com.kosta.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository ur;
	private final BCryptPasswordEncoder bc;
	
	@Override
	public Long save(User user) {
		user.setPassword(bc.encode(user.getPassword()));
		return ur.save(user).getId();
	}
}
