package com.ssafy.engki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.ImageCaption;
import com.ssafy.engki.entity.ImageCaptionId;

public interface ImageCaptionRepository extends JpaRepository<ImageCaption, ImageCaptionId> {
	@Query(value = "select ic from ImageCaption ic where ic.id.wordId = :wordId")
	List<ImageCaption> getAllByWordId(long wordId);

	@Query(value = "select ic.caption from ImageCaption ic where ic.id.wordId <> :wordId")
	List<String> findExceptWord(long wordId);
}
