package com.ssafy.engki.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	@Query(value = "select * from image i "
		+ "where i.id in ("
		+ "select iw.image_id from image_word iw where iw.word_id = :wordId) "
		+ "order by rand() limit 1",
		nativeQuery = true)
	Image getRandomByWord(long wordId);

	@Query(value = "select * from image i "
		+ "where i.id in ("
		+ "select iw.image_id from image_word iw where iw.word_id = :wordId "
		+ "and 1 = (select count(*) from image_word iw2 where iw2.image_id = iw.image_id)) "
		+ "order by rand() limit 1",
		nativeQuery = true)
	Optional<Image> getRandomOneObjectByWord(long wordId);

	@Query(value = "select * from image i "
		+ "where i.id in ("
		+ "select iw.image_id from image_word iw where iw.word_id = :wordId "
		+ "and 1 = (select count(*) from image_word iw2 where iw2.image_id = iw.image_id)) "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<Image> getRandomListOneObjectByWord(long wordId, int num);
}
