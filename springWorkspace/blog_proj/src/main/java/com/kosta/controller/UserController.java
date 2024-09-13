package com.kosta.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kosta.entity.User;
import com.kosta.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	// "/login" 화면
	@GetMapping("/login")
	public String loginPage() {
		return userService.islogin() ? "redirect:/blog/list" : "/user/login";
	}

	// "/join" 화면
	@GetMapping("/join")
	public String joinPage() {
		return userService.islogin() ? "redirect:/blog/list" : "/user/join";
	}

	// "/logout" 동작
	@GetMapping("/logout")
	public String logout(HttpServletRequest req, HttpServletResponse res) {
		new SecurityContextLogoutHandler().logout(req, res, SecurityContextHolder.getContext().getAuthentication());
		return "redirect:/login";
	}

	// "/login" 동작
	@PostMapping("/join")
	public String join(User user) {
		userService.save(user);
		return "redirect:/login";
	}

}
