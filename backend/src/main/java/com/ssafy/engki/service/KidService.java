package com.ssafy.engki.service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.KidDto;
import com.ssafy.engki.entity.Kid;
import com.ssafy.engki.mapper.KidMapper;
import com.ssafy.engki.repository.KidEmotionRepository;
import com.ssafy.engki.repository.KidRepository;
import com.ssafy.engki.repository.KidWordRepository;

@RequiredArgsConstructor
@Service
public class KidService {
	private final KidRepository kidRepository;
	private final KidWordRepository kidWordRepository;
	private final KidEmotionRepository kidEmotionRepository;

	public long add(KidDto.KidRequest kidRequest) {
		Kid kid = Kid.builder()
			.parentId(kidRequest.getParentId())
			.name(kidRequest.getName())
			.birthday(kidRequest.getBirthday())
			.icon(kidRequest.getIcon())
			.build();
		return kidRepository.save(kid).getId();
	}

	public String updateIcon(long kidId, KidDto.KidIcon icon) {
		Kid kid = kidRepository.getOne(kidId);
		kid.updateIcon(icon.getIcon());
		return kidRepository.save(kid).getIcon();
	}

	public void delete(long kidId) {
		kidRepository.deleteById(kidId);
	}

	public KidDto.KidInfo get(long kidId) {
		return KidMapper.INSTANCE.toInfo(
			kidRepository.getOne(kidId)
		);
	}

	public List<KidDto.StudyInfo> getStudyInfo(long kidId, int prev) {
		// 이번주 - prev 주의 월요일 구하기
		LocalDate lastMonday = LocalDate.now(ZoneId.of("Asia/Seoul"))
			.minusWeeks(prev).with(DayOfWeek.MONDAY);

		// 일주일치 (월 ~ 일) 학습 정보
		List<KidDto.StudyInfo> info = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			Date today = Date.valueOf(lastMonday.plusDays(i));
			info.add(KidDto.StudyInfo.builder()
				.date(today)
				.words(kidWordRepository.findWordByKidIdAndDate(kidId, today)
					.stream()
					.map(kw -> kw.getWord().getWord())
					.collect(
						Collectors.toList())) // 공부 정보
				.emotion(KidDto.Emotion.getEmotionOrNull(kidEmotionRepository.getByKidIdAndDate(kidId, today))) // 감정 평균
				.build());
		}

		return info;
	}
}
