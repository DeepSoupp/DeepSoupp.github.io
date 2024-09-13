package com.kosta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kosta.entity.Article;
import com.kosta.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {
	private final BlogRepository blogRepository;

	@Override
	public Article save(Article article) {
		return blogRepository.save(article);
	}

	@Override
	public List<Article> findAll() {
		return blogRepository.findAll();
	}

	@Override
	public Article findById(long id) throws Exception {
		Optional<Article> optArticle = blogRepository.findById(id);
		Article article = optArticle.orElseThrow(() -> new Exception("없는 ID !"));
		return article;
	}

	@Override
	public void deleteById(long id) throws Exception {
		Article article = findById(id);
		blogRepository.deleteById(article.getId());
	}

	@Override
	public Article update(Article article) throws Exception {
		Article originArticle = findById(article.getId());
		originArticle.setTitle(article.getTitle());
		originArticle.setContent(article.getContent());

		return blogRepository.save(originArticle);
	}

	@Override
	public List<Article> searchAndOrder(String keyword, String order) {
		if (order.equals("desc")) {
			return blogRepository.findByTitleContainsOrContentContainsOrderByTitleDesc(keyword, keyword);
		}
		return blogRepository.findByTitleContainsOrContentContainsOrderByTitleAsc(keyword, keyword);
	}
}
