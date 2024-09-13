package com.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.study.domain.UserDTO;
import com.study.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService us;
	
	// 로그인 화면
	@GetMapping("/login")
	public String loginPage() {
		return "/user/login";
	}
	
	@GetMapping("/join")
	public String joinPage() {
		return "/user/join";
	}
	@PostMapping("/join")
	public String join(UserDTO userDTO) {
		us.join(userDTO);
		return "redirect:/"; 
			
	}
	@GetMapping("/group")
	public String groupPage() {
		return "/group/group";
	}
	

	
}
