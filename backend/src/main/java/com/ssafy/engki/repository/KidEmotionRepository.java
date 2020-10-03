package com.ssafy.engki.repository;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.engki.dto.AvgEmotion;
import com.ssafy.engki.entity.KidEmotion;

public interface KidEmotionRepository extends JpaRepository<KidEmotion, Long> {
	@Query(nativeQuery = true, value =
		"select avg(angry) as angry, avg(disgusting) as disgusting, avg(fearful) as fearful, "
			+ "avg(happy) as happy, avg(neutral) as neutral, avg(sad) as sad, avg(surprising) as surprising "
			+ "from ENGKI.kid_emotion ke where kid_id = :kidId and date(evaluate_time) = date(:date)"
	)
	AvgEmotion getByKidIdAndDate(long kidId, Date date);
}
