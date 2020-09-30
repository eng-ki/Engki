package com.ssafy.engki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.engki.entity.Kid;

@Repository
public interface KidRepository extends JpaRepository<Kid, Long> {
}
