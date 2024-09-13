package com.kosta.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{

	List<Member> findByNameContains(String keyword);
}
