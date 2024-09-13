package com.kosta.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kosta.dto.User;

@Mapper
public interface UserMapper {
	void save(User user) throws Exception;

	void deleteById(int id) throws Exception;

	User findById(int id) throws Exception;

	List<User> findAll() throws Exception;

	void updateUser(User user) throws Exception;
}
