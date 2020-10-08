package com.ssafy.engki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.CustomImageCaption;
import com.ssafy.engki.entity.CustomImageCaptionId;

public interface CustomImageCaptionRepository extends JpaRepository<CustomImageCaption, CustomImageCaptionId> {
	@Query(value = "select * from custom_image_caption cic where cic.word = :word "
		+ "and cic.image_id in (select ci.id from custom_image ci where ci.parent_id = :parentId) "
		+ "order by rand() limit 1",
		nativeQuery = true)
	CustomImageCaption getRandomByWord(String word, long parentId);

	@Query(value = "select * from custom_image_caption cic where cic.image_id = :imageId "
		+ "limit 1",
		nativeQuery = true)
	CustomImageCaption getOne(long imageId);
}
