package com.study.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.entity.Group;
import com.study.entity.Member;
import com.study.entity.User;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	boolean existsByMemberIdAndGroup(User user, Group group);

	Optional<Member> findByMemberIdAndGroupId(Long memberId, Long groupId);

	List<Member> findByGroupId(Long groupId);

	List<Member> findByMemberId(Long userId);
}
