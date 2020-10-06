package com.ssafy.engki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.CustomImageCaption;
import com.ssafy.engki.entity.CustomImageCaptionId;

public interface CustomImageCaptionRepository extends JpaRepository<CustomImageCaption, CustomImageCaptionId> {
	@Query("select cic from CustomImageCaption cic where cic.id.word = :word "
		+ "and cic.id.imageId in (select ci.id from CustomImage ci where ci.parentId = :parentId)")
	List<CustomImageCaption> getImagesOfWord(String word, long parentId);
}
