package com.ssafy.engki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.CustomImageWord;
import com.ssafy.engki.entity.CustomImageWordId;

public interface CustomImageWordRepository extends JpaRepository<CustomImageWord, CustomImageWordId> {
	@Query("select ciw from CustomImageWord ciw join CustomImageCaption cic on ciw.id.word = cic.id.word "
		+ "where ciw.id.imageId in :ids and cic.id.imageId in :ids")
	List<CustomImageWord> findAllWords(List<Long> ids);

	@Query("select ciw from CustomImageWord ciw where ciw.id.word = :word "
		+ "and ciw.id.imageId in (select ci.id from CustomImage ci where ci.parentId = :parentId)")
	List<CustomImageWord> getImagesOfWord(String word, long parentId);
}
