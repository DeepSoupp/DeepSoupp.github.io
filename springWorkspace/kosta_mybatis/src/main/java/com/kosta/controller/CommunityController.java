package com.kosta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.dto.Community;
import com.kosta.service.CommunityService;

@Controller
@RequestMapping("/community/*")
public class CommunityController {
	@Autowired
	private CommunityService cs;

	@GetMapping("/list")
	public ModelAndView list() throws Exception {
		ModelAndView mv = new ModelAndView("community/communitylist");
		mv.addObject("menu", "community");
		List<Community> communityList = cs.getAllCommunity();
		mv.addObject("list", communityList);
		return mv;
	}

	@GetMapping("/add")
	public ModelAndView addForm() {
		ModelAndView mv = new ModelAndView("community/communityform");
		mv.addObject("menu", "community");
		return mv;
	}

	@PostMapping("/add")
	public String add(Community community, @RequestParam("creatorId") int id,
			@RequestParam("files") List<MultipartFile> files) throws Exception {
		cs.add(community, id, files);
		return "redirect:/community/list";
	}

	@GetMapping("/detail/{id}")
	public ModelAndView detail(@PathVariable("id") int id) throws Exception {
		ModelAndView mv = new ModelAndView("community/communitydetail");
		mv.addObject("menu", "community");
		Community community = cs.getCommunityById(id);
		return mv;
	}

	@DeleteMapping("/delete")
	public String dlelete() {
		return "redirect:/community/list";
	}

}
