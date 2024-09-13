package com.study.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.domain.GroupDTO;
import com.study.entity.Group;
import com.study.entity.User;
import com.study.service.GroupService;
import com.study.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/group/*")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService ms;
	private final GroupService gs;

	@PostMapping("/detail/{groupId}/join")
	public String joinGroup(@PathVariable("groupId") Long groupId, @AuthenticationPrincipal User user, Model model) {
		GroupDTO group;
		try {
			ms.joinGroup(user, groupId);
			return "redirect:/group/detail/" + groupId;
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}

	@PostMapping("/detail/{groupId}/leave")
	public String leaveGroup(@PathVariable("groupId") Long groupId, @AuthenticationPrincipal User user, Model model) {
		GroupDTO group;
		try {
			ms.leaveGroup(user, groupId);
			return "redirect:/group/detail/" + groupId;
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
}
