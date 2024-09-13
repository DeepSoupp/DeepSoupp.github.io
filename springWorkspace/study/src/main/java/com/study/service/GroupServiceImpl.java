package com.study.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.study.domain.GroupDTO;
import com.study.domain.MemberDTO;
import com.study.domain.UserDTO;
import com.study.entity.Group;
import com.study.entity.Member;
import com.study.entity.User;
import com.study.repository.GroupRepository;
import com.study.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{
	private final GroupRepository gr;
	private final MemberRepository mr;

	@Override
	public Group save(Group group, User user) {
		group.setLeaderId(user);
		return gr.save(group);
	}

	@Override
	public List<Group> findAll() {
		return gr.findAll();
	}

	@Override
	public GroupDTO findById(Long userId, Long groupId) throws Exception {
		Optional<Group> optGroup = gr.findById(groupId);
		Group group = optGroup.orElseThrow(() -> new Exception("없는 게시물 입니다"));
		
		boolean joined = mr.findByGroupId(groupId).stream().anyMatch(m -> m.getMember().getId().equals(userId));
		boolean owned = group.getLeaderId().getId().equals(userId);
		
		GroupDTO result = GroupDTO.setGroupDTO(group);
		result.setJoined(joined);
		result.setOwned(owned);
		return result;
	}

	@Override
	public void deleteById(Long id, User user) throws Exception {
		gr.deleteById(id);
	}

	@Override
	public Group edit(Group group) throws Exception {
		Group originGroup = gr.findById(group.getId()).get();
		originGroup.setGroupTitle(group.getGroupTitle());
		originGroup.setGroupContent(group.getGroupContent());
		
		return gr.save(originGroup);
	}

	@Override
	public List<GroupDTO> getJoinGroupByUserId(Long userId) {
		List<Member> mList = mr.findAll();
		List<GroupDTO> list = mList.stream().filter(m -> m.getMember().getId().equals(userId)).map(
				m -> GroupDTO.setGroupDTO(m.getGroup())
		).toList();
		
		for(GroupDTO g : list) {
			g.setMemberList(mList.stream().filter(m -> m.getGroup().getId().equals(g.getId())).map(m -> UserDTO.setUserDTO(m.getMember())).toList());
		}
		return list;
	}
	
	
}
