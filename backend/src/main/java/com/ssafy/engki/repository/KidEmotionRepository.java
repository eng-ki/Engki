package com.ssafy.engki.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.dto.AvgEmotion;
import com.ssafy.engki.entity.KidEmotion;

public interface KidEmotionRepository extends JpaRepository<KidEmotion, Long> {
	@Query(nativeQuery = true, value =
		"select truncate(avg(angry), 2) as angry, "
			+ "truncate(avg(disgusting), 2) as disgusting, truncate(avg(fearful), 2) as fearful, "
			+ "truncate(avg(happy), 2) as happy, truncate(avg(neutral), 2) as neutral, "
			+ "truncate(avg(sad), 2) as sad, truncate(avg(surprising), 2) as surprising "
			+ "from ENGKI.kid_emotion ke where kid_id = :kidId and date(evaluate_time) = date(:date)"
	)
	AvgEmotion getByKidIdAndDate(long kidId, Date date);
}
