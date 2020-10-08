package com.ssafy.engki.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.Word;

public interface WordRepository extends JpaRepository<Word, Long> {
	@Query(value = "select * from word w "
		+ "where w.theme_id = :themeId "
		+ "and not exists (select * from ENGKI.kid_word kw where kw.kid_id = :kidId and kw.word_id = w.id) "
		+ "order by rand() limit 1",
		nativeQuery = true)
	Optional<Word> getRandomUnlearned(long themeId, long kidId);

	@Query(value = "select * from word w "
		+ "where w.theme_id = :themeId "
		+ "order by rand() limit 1",
		nativeQuery = true)
	Word getRandomByTheme(long themeId);

	@Query(value = "select * from word w "
		+ "where w.word <> :word "
		+ "order by rand() limit 1",
		nativeQuery = true)
	Word getRandomExceptWord(String word);

	@Query(value = "select * from word w "
		+ "where w.word <> :word "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<Word> getRandomListExceptWord(String word, int num);

	@Query(value = "select * from word w "
		+ "where w.id <> :wordId "
		+ "and w.theme_id = :themeId "
		+ "order by rand() limit 1",
		nativeQuery = true)
	Optional<Word> getRandomByThemeExceptWord(long themeId, long wordId);

	@Query(value = "select * from word w "
		+ "where w.id <> :wordId "
		+ "and w.theme_id = :themeId "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<Word> getRandomListByThemeExceptWord(long themeId, long wordId, int num);

	@Query(value = "select * from word w "
		+ "where w.theme_id <> :themeId "
		+ "order by rand() limit 1",
		nativeQuery = true)
	Word getRandomExceptTheme(long themeId);

	@Query(value = "select * from word w "
		+ "where w.id <> :themeId "
		+ "order by rand() limit :num",
		nativeQuery = true)
	List<Word> getRandomListExceptTheme(long themeId, int num);
}
