package com.kosta.service;

import com.kosta.entity.User;

public interface UserService {
	Long save(User user);

	boolean islogin();
}
