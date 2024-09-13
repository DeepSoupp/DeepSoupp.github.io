package com.kosta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	Optional<Member> findByName(String name);


	List<Member> findByNameContains(String string);
}
