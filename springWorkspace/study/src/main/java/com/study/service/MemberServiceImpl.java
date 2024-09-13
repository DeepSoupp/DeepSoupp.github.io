package com.study.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.study.entity.Group;
import com.study.entity.Member;
import com.study.entity.User;
import com.study.repository.GroupRepository;
import com.study.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	private final MemberRepository mr;
	private final GroupRepository gr;
	
	@Override
	public Member joinGroup(User user, Long groupId) {
		Member member = new Member();
		member.setMember(user);
		member.setGroup(gr.findById(groupId).get());
		return mr.save(member);
	}

	@Override
	public void leaveGroup(User user, Long groupId) {
		Optional<Member> OptMember = mr.findByMemberIdAndGroupId(user.getId(), groupId);
		
		if (OptMember.isPresent()) {
			Member member = OptMember.get();
			mr.delete(member);
		} else {
			throw new RuntimeException("멤버가 아닙니다");
		}
	}

	@Override
	public List<Member> getMemberListByGroupId(Long groupId) {
		return mr.findByGroupId(groupId);
	}



}
