package com.kosta.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.entity.Article;
import com.kosta.service.BlogService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/blog/*")
@RequiredArgsConstructor
public class BlogController {

	private final BlogService blogService;

	@GetMapping("/add")
	public String addPage() {
		return "form";
	}

	@PostMapping("/add")
	public String addArticle(Article article) {
		blogService.save(article);
		return "redirect:/blog/list";
	}

	@GetMapping("/list")
	public String listPage(@RequestParam(required = false, name = "keyword") String keyword,
			@RequestParam(required = false, name = "order") String order, Model model) {
		List<Article> articleList;
		if (keyword != null || order != null) {
			articleList = blogService.searchAndOrder(keyword, order);
		} else {
			articleList = blogService.findAll();
		}
		model.addAttribute("list", articleList);
		return "list";
	}

	@GetMapping("/detail/{id}")
	public String detailPage(@PathVariable("id") long id, Model model) {
		Article article;
		try {
			article = blogService.findById(id);
			model.addAttribute("article", article);
			return "detail";
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}

	@DeleteMapping("/delete/{id}")
	public String deleteArticle(@PathVariable("id") long id, Model model) {
		try {
			blogService.deleteById(id);
			return "redirect:/blog/list";
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}

	@GetMapping("/modify/{id}")
	public String updatePage(@PathVariable("id") long id, Model model) {
		Article article;
		try {
			article = blogService.findById(id);
			model.addAttribute("article", article);
			return "form";
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}

	@PostMapping("/modify")
	public String updateArticle(Article article, Model model) {
		try {
			blogService.update(article);
			return "redirect:/blog/detail/" + article.getId();
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}

}
