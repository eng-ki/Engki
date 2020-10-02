package com.ssafy.engki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.ImageWord;
import com.ssafy.engki.entity.ImageWordId;

public interface ImageWordRepository extends JpaRepository<ImageWord, ImageWordId> {
	@Query(value = "select iw from ImageWord iw join Word w on iw.id.wordId = w.id "
		+ "where iw.id.wordId <> :wordId "
		+ "and w.themeId = :themeId "
		+ "and 1 = (select count(iw2) from ImageWord iw2 where iw2.id.imageId = iw.id.imageId)")
	List<ImageWord> getOneObjectImagesFromThemeNotWord(long themeId, long wordId);

	@Query(value = "select iw from ImageWord iw where iw.id.wordId = :wordId")
	List<ImageWord> getAllByWordId(long wordId);
}
