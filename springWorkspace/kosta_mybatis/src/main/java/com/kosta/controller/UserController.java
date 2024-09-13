package com.kosta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.dto.User;
import com.kosta.service.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {
	@Autowired
	private UserService us;

	// 회원가입 화면
	@GetMapping("/add")
	public ModelAndView addForm() {
		ModelAndView mv = new ModelAndView("user/userform");
		mv.addObject("menu", "user");
		return mv;
	}

	// 회원가입 동작
	@PostMapping("/add")
	public String add(User user) {
		boolean isAdd = us.addUser(user);
		if (isAdd) {
			return "redirect:/user/list";
		}
		return "error";
	}

	// 회원 삭제 동작
	@DeleteMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		boolean isDelete = us.deleteUser(id);
		if (isDelete) {
			return "redirect:/user/list";
		}
		return "error";
	}

	// 특정 회원 조회
	@GetMapping("/detail/{id}")
	public ModelAndView detail(@PathVariable("id") int id) throws Exception {
		ModelAndView mv = new ModelAndView("user/userdetail");
		mv.addObject("menu", "user");
		User user = us.getUserById(id);
		mv.addObject("user", user);
		return mv;
	}

	// 회원 전체 조회
	@GetMapping("/list")
	public ModelAndView list() throws Exception {
		ModelAndView mv = new ModelAndView("user/userlist");
		mv.addObject("menu", "user");
		List<User> userList = us.getAll();
		mv.addObject("userList", userList);
		return mv;
	}

	// 회원수정 화면
	@GetMapping("/modify")
	public ModelAndView modify(@RequestParam("id") int id) throws Exception {
		ModelAndView mv = new ModelAndView("user/userform");
		mv.addObject("menu","user");
		User user = us.getUserById(id);
		mv.addObject("user", user);
		return mv;
	}

	// 회원수정 동작
	@PatchMapping("/modify")
	public String modify(User user) {
		User u = us.modifyUser(user);
		if (u == null) {
			return "redirect:/user/list";
		}
		return "redirect:/user/detail/" + user.getId();
	}
}