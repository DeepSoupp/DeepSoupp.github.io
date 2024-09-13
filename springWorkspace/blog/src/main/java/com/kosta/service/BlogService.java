package com.kosta.service;

import java.util.List;

import com.kosta.entity.Article;

public interface BlogService {
	Article save(Article article);

	List<Article> findAll();

	Article findById(long id) throws Exception;

	void deleteById(long id) throws Exception;

	Article update(Article article) throws Exception;

	List<Article> searchAndOrder(String keyword, String order);
}
