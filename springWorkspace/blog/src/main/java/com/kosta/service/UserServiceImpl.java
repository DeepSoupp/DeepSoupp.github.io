package com.kosta.service;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	public void handleLoginFailure(String email) {
		User user = ur.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found : "+ email));
		user.setFailedCount(user.getFailedCount() + 1);
		user.setLastLoginAt(LocalDateTime.now());
		
		if (user.getFailedCount() > 3) {
			user.setLock(true);
			user.setLockCount(user.getLockCount()+1);
		}
		ur.save(user);
	}
}
