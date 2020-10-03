package com.ssafy.engki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.engki.entity.KidWord;

public interface KidWordRepository extends JpaRepository<KidWord, Long> {
}
