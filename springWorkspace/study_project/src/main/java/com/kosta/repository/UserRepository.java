package com.kosta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosta.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// 이메일로 사용자 조회
	// 이메일에 해당하는 사용자 정보를 담고있는 optional 객체
	Optional<User> findByEmail(String email);

	// 주어진 이메일로 사용자가 존재하는지 확인
	// 존재하면 true 없으면 false
	boolean existsByEmail(String email);
}
