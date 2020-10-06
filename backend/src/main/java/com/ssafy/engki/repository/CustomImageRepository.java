package com.ssafy.engki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.engki.entity.CustomImage;

public interface CustomImageRepository extends JpaRepository<CustomImage, Long> {
}
