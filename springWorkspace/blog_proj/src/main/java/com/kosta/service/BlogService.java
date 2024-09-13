package com.kosta.service;

import java.util.List;

import com.kosta.entity.Article;
import com.kosta.entity.User;

public interface BlogService {
	Article save(Article article,User user);
	
	List<Article> findAll();
	
	Article findById(Long id) throws Exception;
	
	void deleteById(Long id,User user) throws Exception;
	
	Article update(Article article,User user) throws Exception;

	List<Article> searchAndOrder(String keyword, String order);
}
