package com.kosta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.dto.User;
import com.kosta.mapper.UserMapper;

@Service
public class IUserService implements UserService {
	@Autowired
	private UserMapper um;
	
	@Override
	public boolean addUser(User user) {
		try {
			um.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteUser(int id) {
		try {
			um.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public User getUserById(int id) throws Exception {
		User user = um.findById(id);
		return user;
	}

	@Override
	public List<User> getAll() throws Exception {
		return um.findAll();
	}

	@Override
	public User modifyUser(User user) {
		try {
			um.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

}
