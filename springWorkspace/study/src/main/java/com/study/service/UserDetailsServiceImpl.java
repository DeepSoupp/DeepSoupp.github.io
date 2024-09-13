package com.study.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.study.entity.User;
import com.study.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository ur;
	@Override
	public User loadUserByUsername(String email) {
		Optional<User> user = ur.findByEmail(email);
		if (user.isEmpty()) {
			System.out.println(email + "없");
			
		}
		return user.orElseThrow(() -> new IllegalArgumentException(email + "업성요"));
	}
}
