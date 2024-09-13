package com.study.service;

import java.util.List;

import com.study.entity.Group;
import com.study.entity.Member;
import com.study.entity.User;

public interface MemberService {
	Member joinGroup(User user, Long groupdId);
	void leaveGroup(User user, Long groupdId);
	List<Member> getMemberListByGroupId(Long id);
}
