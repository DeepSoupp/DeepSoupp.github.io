package com.kosta.service;

import java.util.List;

import com.kosta.entity.Member;

public interface MemberService {

	List<Member> getAll() throws Exception;

	void insertMember(Member member) throws Exception;

	void deleteMemberById(int id) throws Exception;

	Member getMemberById(int id) throws Exception;

	void modifyMember(Member member) throws Exception;

	List<Member> searchMember(String keyword)throws Exception;
	
}
