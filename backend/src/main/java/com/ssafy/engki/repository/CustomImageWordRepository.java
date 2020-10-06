package com.ssafy.engki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.engki.entity.CustomImageWord;
import com.ssafy.engki.entity.CustomImageWordId;

public interface CustomImageWordRepository extends JpaRepository<CustomImageWord, CustomImageWordId> {
}
