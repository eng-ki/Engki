package com.ssafy.engki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.engki.entity.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
}
