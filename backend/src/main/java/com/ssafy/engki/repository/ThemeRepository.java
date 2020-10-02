package com.ssafy.engki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.engki.entity.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
