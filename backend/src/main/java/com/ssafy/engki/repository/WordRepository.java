package com.ssafy.engki.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.Word;

public interface WordRepository extends JpaRepository<Word, Long> {
	@Query(value = "select w from Word w where w.themeId = :themeId "
		+ "and not exists (select kw from KidWord kw where kw.kidId = :kidId)")
	List<Word> getUnlearnedWord(long themeId, long kidId);

	@Query(value = "select * from word w where w.theme_id = :themeId "
		+ "and w.id = (select kw.word_id from kid_word kw join word w2 on kw.word_id = w2.id "
		+ "where w2.theme_id = :themeId and kw.kid_id = :kidId order by kw.studied_date limit 1)",
		nativeQuery = true)
	Word getOldestLearnedWord(long themeId, long kidId);
}
