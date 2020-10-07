package com.ssafy.engki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.ImageCaption;
import com.ssafy.engki.entity.ImageCaptionId;

public interface ImageCaptionRepository extends JpaRepository<ImageCaption, ImageCaptionId> {
	@Query(value = "select * from image_caption "
		+ "where word_id = :wordId "
		+ "order by rand() limit 1;",
		nativeQuery = true)
	ImageCaption getRandomByWord(long wordId);

	@Query(value = "select * from image_caption "
		+ "where image_id <> :imageId "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<ImageCaption> getRandomListExceptImage(long imageId, int num);

	@Query(value = "select * from image_caption ic inner join word w on ic.word_id = w.id "
		+ "where w.word <> :word "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<ImageCaption> getRandomListExceptWord(String word, int num);
}
