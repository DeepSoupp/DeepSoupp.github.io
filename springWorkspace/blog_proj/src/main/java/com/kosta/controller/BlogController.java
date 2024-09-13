package com.kosta.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kosta.entity.Article;
import com.kosta.entity.User;
import com.kosta.service.BlogService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/blog/*")
@RequiredArgsConstructor
public class BlogController {
	private final BlogService blogService;
	
	@GetMapping("/add")
	public String addPage() {
		return "/blog/form";
	}
	
	@PostMapping("/add")
	public String addArticle(Article article, @AuthenticationPrincipal User user) {
		article.setCreator(user);
		blogService.save(article,user);
		return "redirect:/blog/list";
	}
	
	@GetMapping("/list")
	public String listPage(
			@RequestParam(required = false, name = "keyword") String keyword,
			@RequestParam(required = false, name = "order") String order,
			Model model) {
		List<Article> articleList;
		if (keyword != null || order != null) {
			articleList = blogService.searchAndOrder(keyword, order);
		} else {
			articleList = blogService.findAll();
		}
		model.addAttribute("list", articleList);
		return "/blog/list";
	}
	
	@GetMapping("/detail/{id}")
	public String detailPage(@PathVariable("id") Long id, Model model) {
		try {
			Article article = blogService.findById(id);
			model.addAttribute("article", article);
			return "/blog/detail";
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteArticle(@PathVariable("id") Long id, Model model,@AuthenticationPrincipal User user) {
		try {
			blogService.deleteById(id,user);
			return "redirect:/blog/list";
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
	
	@GetMapping("/modify/{id}")
	public String modifyPage(@PathVariable("id") Long id, Model model) {
		try {
			Article article = blogService.findById(id);
			model.addAttribute("article", article);
			return "/blog/form";
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
	
	@PatchMapping("/modify")
	public String modifyArticle(Article article, Model model,@AuthenticationPrincipal User user) {
		try {
			blogService.update(article,user);
			return "redirect:/blog/detail/" + article.getId();
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
}
