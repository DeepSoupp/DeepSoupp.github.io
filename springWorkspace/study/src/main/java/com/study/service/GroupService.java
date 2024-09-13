package com.study.service;

import java.util.List;

import com.study.domain.GroupDTO;
import com.study.entity.Group;
import com.study.entity.User;

public interface GroupService {
	Group save(Group group,User user);
	
	List<Group> findAll();
	
	GroupDTO findById(Long userId, Long groupId) throws Exception;
	
	void deleteById(Long id,User user) throws Exception;

	Group edit(Group group) throws Exception;

	List<GroupDTO> getJoinGroupByUserId(Long id);
}
