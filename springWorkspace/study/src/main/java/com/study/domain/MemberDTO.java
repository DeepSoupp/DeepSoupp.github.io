package com.study.domain;

import com.study.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MemberDTO {

	private Long id;
	private UserDTO member;
	private GroupDTO group;
	
	public static MemberDTO setMemberDTO(Member m) {
		return MemberDTO.builder()
			.id(m.getId())
			.member(UserDTO.setUserDTO(m.getMember()))
			.group(GroupDTO.setGroupDTO(m.getGroup()))
			.build();
	}
}
