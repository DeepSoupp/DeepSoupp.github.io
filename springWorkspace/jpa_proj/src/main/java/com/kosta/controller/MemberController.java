package com.kosta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.entity.Member;
import com.kosta.service.MemberService;

@Controller
public class MemberController {
	@Autowired
	private MemberService ms;

	@GetMapping("/list")
	public ModelAndView listPage() throws Exception {
		ModelAndView mav = new ModelAndView("member/mlist");
		List<Member> mList = ms.getAll();
		mav.addObject("list", mList);
		return mav;
	}

	@GetMapping("/add")
	public ModelAndView addPage() throws Exception {
		ModelAndView mav = new ModelAndView("member/madd");
		return mav;
	}

	@PostMapping("/add")
	public String addMember(Member member) throws Exception {
		ms.insertMember(member);
		return "redirect:/list";
	}

	@GetMapping("/delete/{id}")
	public String deleteMember(@PathVariable("id") int id) throws Exception {
		ms.deleteMemberById(id);
		return "redirect:/list";
	}

	@GetMapping("/modify/{id}")
	public String modifyPage(@PathVariable("id") int id, Model model) throws Exception {
		Member member = ms.getMemberById(id);
		model.addAttribute("member", member);
		return "member/madd";
	}

	@PostMapping("/modify")
	public String modifyMember(Member member) throws Exception {
		ms.modifyMember(member);
		return "redirect:/list";
	}

	@GetMapping("/search")
	public String searchMember(@RequestParam("keyword") String keyword,Model model) throws Exception {
		List<Member> memberSearchResult = ms.searchMember(keyword);
		model.addAttribute("list",memberSearchResult);
		return "member/mlist";
	}
}
