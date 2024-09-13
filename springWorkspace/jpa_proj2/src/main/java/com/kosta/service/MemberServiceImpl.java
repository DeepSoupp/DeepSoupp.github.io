package com.kosta.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.entity.Member;
import com.kosta.repository.MemberRepository;


@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberRepository memberRepository;
	

	@Override
	public List<Member> allList() throws Exception {
		List<Member> all = memberRepository.findAll();
		return all;
	}


	@Override
	public void add(Member member) throws Exception{
		memberRepository.save(member);
	}


	@Override
	public void deleteById(int id) throws Exception{
		memberRepository.deleteById(id);
	}


	@Override
	public Member getMemberById(int id) throws Exception {
		Optional<Member> optMember = memberRepository.findById(id);
		Member member = optMember.orElseThrow(()-> new Exception("없는 아이디"));
		return member;
	}


	@Override
	public void modifyMember(Member member) throws Exception {
		Member exMember = getMemberById(member.getId());
		exMember.setName(member.getName());
		memberRepository.save(exMember);
	}


	@Override
	public List<Member> searchMember(String keyword) throws Exception {
		return memberRepository.findByNameContains(keyword);
	}

}
