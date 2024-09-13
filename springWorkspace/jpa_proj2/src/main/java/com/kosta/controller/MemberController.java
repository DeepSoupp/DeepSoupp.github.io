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
//	객체를 자동으로 주입
	@Autowired
	private MemberService ms;

//	get 은 조회할때
	@GetMapping("/list")
	public ModelAndView listPage() throws Exception {
		ModelAndView mav = new ModelAndView("member/mlist");
		List<Member> memberList = ms.allList();
		mav.addObject("list", memberList);
		return mav;
	}

	@GetMapping("/add")
	public ModelAndView addPage() throws Exception {
		ModelAndView mav = new ModelAndView("member/madd");
		return mav;
	}

	@PostMapping("/add")
	public String add(Member member) throws Exception {
		ms.add(member);
		return "redirect:/list";
	}

	@GetMapping("delete/{id}")
	public String deleteM(@PathVariable("id") int id) throws Exception {
		ms.deleteById(id);
		return "redirect:/list";
	}
	@GetMapping("/modify/{id}")
	public String modifyPage(@PathVariable("id") int id, Model model) throws Exception {
		Member member = ms.getMemberById(id);
		model.addAttribute("member",member);
		return "member/madd";
	}
	@PostMapping("/modify")
	public String modifyMember(Member member) throws Exception{
		ms.modifyMember(member);
		return "redirect:/list";
	}
	
	@GetMapping("/search")
	public String searchMember(@RequestParam("keyword")String keyword,Model model) throws Exception {
		List<Member> memberSerch = ms.searchMember(keyword);
		model.addAttribute("list",memberSerch);
		return "member/mlist";
	}
	
	
}
