package com.kosta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.entity.Article;

public interface BlogRepository extends JpaRepository<Article, Long> {

	
	List<Article> findByTitleContainsOrContentContainsOrderByTitleDesc(String keyword, String keyword2);

	List<Article> findByTitleContainsOrContentContainsOrderByTitleAsc(String keyword, String keyword2);
	
}
