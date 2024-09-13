package com.study.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.study.entity.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO {
	private Long id;
	private UserDTO leader;
	private String groupTitle;
	private String groupContent;
	private List<UserDTO> memberList;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private boolean joined;
	private boolean owned;
	
	public static GroupDTO setGroupDTO(Group g) {
		return GroupDTO.builder()
			.id(g.getId())
			.leader(UserDTO.setUserDTO(g.getLeaderId()))
			.groupTitle(g.getGroupTitle())
			.groupContent(g.getGroupContent())
			.build();
	}
}
