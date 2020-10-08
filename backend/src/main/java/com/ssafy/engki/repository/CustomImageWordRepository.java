package com.ssafy.engki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.CustomImageWord;
import com.ssafy.engki.entity.CustomImageWordId;

public interface CustomImageWordRepository extends JpaRepository<CustomImageWord, CustomImageWordId> {
	@Query(value = "select * from custom_image_word ciw join custom_image_caption cic on ciw.word = cic.word "
		+ "where ciw.image_id in :imageIds and cic.image_id in :imageIds "
		+ "order by rand() limit 1",
		nativeQuery = true)
	CustomImageWord getRandom(List<Long> imageIds);

	@Query(value = "select * from custom_image_word ciw "
		+ "where ciw.word = :word "
		+ "and ciw.image_id in (select ci.id from custom_image ci where ci.parent_id = :parentId) "
		+ "order by rand() limit 1",
		nativeQuery = true)
	CustomImageWord getRandomByWord(String word, long parentId);

	@Query(value = "select * from custom_image_word ciw "
		+ "where ciw.word = :word "
		+ "and ciw.image_id in (select ci.id from custom_image ci where ci.parent_id = :parentId) "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<CustomImageWord> getRandomListByWord(String word, long parentId, int num);

	@Query(value = "select * from custom_image_word ciw where ciw.word = :word "
		+ "and ciw.image_id <> :imageId "
		+ "and ciw.image_id in (select ci.id from custom_image ci where ci.parent_id = :parentId) "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<CustomImageWord> getRandomListByWordExceptImage(String word, long parentId, long imageId, int num);
}
