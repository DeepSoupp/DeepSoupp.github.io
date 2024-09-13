package com.thymeleaf.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.thymeleaf.dto.UserDTO;

// 컨트롤러만듬 >> 템플릿만듬 >> DTO만듬 !! >> 컨트롤에 DTO생성 
@Controller
public class ThymeleafController {
	@RequestMapping("/")
	public ModelAndView showMainPage() {
		ModelAndView mav = new ModelAndView("main");
		return mav;
	}

	// 타임리프는 자바 기반의 서버 템플릿 엔진이다
	// HTML 파일에서 서버 데이터를 표현하고 조작할 수 있도록 도와준다

	@RequestMapping("/variable")
	public ModelAndView varExpression() {
		ModelAndView mav = new ModelAndView("var");
		mav.addObject("variable", "변수 데이터야~");
		mav.addObject("variable2", "<strong>변수2</strong>");

		UserDTO userDTO = new UserDTO(1, "보라돌이", "텔레토비");
		mav.addObject("user", userDTO);

		return mav;
	}

	@RequestMapping("/search")
	public ModelAndView searchResult(@RequestParam("keyword") String keyword) {
		ModelAndView mav = new ModelAndView("result");
		mav.addObject("keyword", keyword);

		List<UserDTO> userList = new ArrayList<UserDTO>();
		userList.add(new UserDTO(2, "뚜비", "텔레토비"));
		userList.add(new UserDTO(3, "나나", "텔레토비"));
		userList.add(new UserDTO(4, "뽀", "텔레토비"));
		userList.add(new UserDTO(5, "오희재", "서울"));
		userList.add(new UserDTO(6, "디지몬", "디지몬월드"));

		mav.addObject("list", userList);
		return mav;
	}

}
