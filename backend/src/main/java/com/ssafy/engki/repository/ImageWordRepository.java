package com.ssafy.engki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.ImageWord;
import com.ssafy.engki.entity.ImageWordId;

public interface ImageWordRepository extends JpaRepository<ImageWord, ImageWordId> {
	@Query(value = "select * from image_word iw "
		+ "where iw.word_id = :wordId "
		+ "order by rand() limit 1",
		nativeQuery = true)
	ImageWord getRandomByWord(long wordId);

	@Query(value = "select * from image_word iw join word w on iw.word_id = w.id "
		+ "where w.word <> :word "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<ImageWord> getRandomListExceptWord(String word, int num);

	@Query(value = "select * from image_word iw join word w on iw.word_id = w.id "
		+ "where iw.word_id <> :wordId "
		+ "and w.theme_id = :themeId "
		+ "and 1 = (select count(*) from image_word iw2 where iw2.image_id = iw.image_id) "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<ImageWord> getRandomListOneObjectByThemeExceptWord(long themeId, long wordId, int num);

	@Query(value = "select * from image_word iw join word w on iw.word_id = w.id "
		+ "where w.theme_id <> :themeId "
		+ "and 1 = (select count(*) from image_word iw2 where iw2.image_id = iw.image_id) "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<ImageWord> getRandomListOneObjectExceptTheme(long themeId, int num);
}
