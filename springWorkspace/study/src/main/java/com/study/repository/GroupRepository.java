package com.study.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.study.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{
}
