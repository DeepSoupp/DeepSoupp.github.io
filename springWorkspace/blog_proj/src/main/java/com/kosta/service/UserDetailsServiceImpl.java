package com.kosta.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kosta.entity.User;
import com.kosta.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public User loadUserByUsername(String email) {
		User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(email));
		return userRepository.save(user);
	}
}
