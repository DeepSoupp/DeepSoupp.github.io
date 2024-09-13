package com.study.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.domain.GroupDTO;
import com.study.domain.UserRole;
import com.study.entity.Group;
import com.study.entity.User;
import com.study.service.GroupService;
import com.study.service.MemberService;
import com.study.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/group/*")
@RequiredArgsConstructor
public class GroupController {
	private final GroupService gs;
	private final UserService us;
	private final MemberService ms;

	@GetMapping("/add")
	public String addPage() {
		return "/group/form";
	}

	@PostMapping("/add")
	public String addGroup(Group group, @AuthenticationPrincipal User user) {
		// 그룹저장
		group.setLeaderId(user);
		gs.save(group, user);

		// 역할 업데이트
		user.setRole(UserRole.LEADER);
		us.save(user);
		
		// 로그인한 상태에서 role 업데이트 (DB ㄴㄴ, 현재 로그인)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
		updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + UserRole.LEADER));
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		
		return "redirect:/group/list";
	}

	@GetMapping("/list")
	public String listPage(Model model) {
		List<Group> groupList;
		groupList = gs.findAll();
		model.addAttribute("list", groupList);
		return "/group/list";
	}

	@GetMapping("/detail/{id}")
	public String detailPage(@PathVariable("id") Long id,@AuthenticationPrincipal User user, Model model) {
		GroupDTO group;
		try {
			group = gs.findById(user.getId(), id);
			model.addAttribute("group", group);
			return "/group/detail";
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
	@DeleteMapping("/delete/{id}")
	public String deleteGroup(@PathVariable("id") Long id,@AuthenticationPrincipal User user,Model model) {
		try {
			gs.deleteById(id, user);
			return "redirect:/group/list";
		} catch (Exception e) {
			model.addAttribute("errMsg",e.getMessage());
			return "error";
		}
	}
	@GetMapping("/edit/{id}")
	public String editPage(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal User user) {
		GroupDTO group;
		try {
			group = gs.findById(user.getId(), id);
			model.addAttribute("group", group);
			return "/group/form";
		} catch (Exception e) {
			model.addAttribute("errMsg", e.getMessage());
			return "error";
		}
	}
	@PostMapping("/edit")
	public String editGroup(Group group, Model model) {
		try {
			System.out.println(group);
			gs.edit(group);
			return "redirect:/group/detail/" + group.getId();
		} catch (Exception e) {
			model.addAttribute("errMsg",e.getMessage());
			return "error";
		}
	}
	@GetMapping("/profile")
	public String profilePage(@AuthenticationPrincipal User user, Model model) {
	    model.addAttribute("user", user);
	    return "/group/profile";
	}
	
	@PostMapping("/profile")
	public String editProfile(@RequestParam("newNickname") String newNickname,@AuthenticationPrincipal User user,Model model) {
		user.setName(newNickname);
		us.save(user);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
		Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(newAuth);
		
		model.addAttribute("user", user);
		return "redirect:/group";
	}
	@GetMapping("/joingroup")
	public String joingroupList(Model model, @AuthenticationPrincipal User user) {
		// 나 라는 사용자가 가입한 모든 그룹 조회
		List<GroupDTO> groupList= gs.getJoinGroupByUserId(user.getId());
		
		model.addAttribute("grouplist",groupList);
		return "/group/joingroup";
	}
	
	@GetMapping("/mygroup")
	public String myGroup() {
		return "/group/mygroup";
	}

}
