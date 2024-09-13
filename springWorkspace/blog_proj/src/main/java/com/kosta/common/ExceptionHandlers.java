package com.kosta.common;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlers {
	@ExceptionHandler(Exception.class)
	public String defaultExceptionHandler(Exception e, Model model) {
		log.error(e.getMessage());
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		model.addAttribute("errMsg", e.getMessage());
		model.addAttribute("stackTrace", sw);
		return "error";
	}
}
