package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController // 해당 클래스가 REST CONTROLLER 기능을 수행하도록 함

public class HelloController {
	@RequestMapping("/") // 메소드가 실핼할 수 있는 주소(경로) 설정
	public String hello() {
		return "졸려요!!";
	}

	@RequestMapping("/hello") // 메소드가 실핼할 수 있는 주소(경로) 설정
	public String hello2() {
		return "쉬는시간";
	}

}
