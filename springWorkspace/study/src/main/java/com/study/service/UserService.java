package com.study.service;

import com.study.domain.UserDTO;
import com.study.entity.User;

public interface UserService {
//	// 사용자 정보 저장
	void save(User user);
	void join(UserDTO userDTO);
}
