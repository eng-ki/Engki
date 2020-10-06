package com.ssafy.engki.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.engki.entity.CustomImageCaption;
import com.ssafy.engki.entity.CustomImageCaptionId;

public interface CustomImageCaptionRepository extends JpaRepository<CustomImageCaption, CustomImageCaptionId> {
}
