package com.ssafy.engki.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.entity.KidWord;

public interface KidWordRepository extends JpaRepository<KidWord, Long> {
	@Query(value = "select kw from KidWord kw where kw.kidId = :kidId "
		+ "and kw.studiedDate = :date order by kw.word.word")
	List<KidWord> findWordByKidIdAndDate(long kidId, Date date);
}
