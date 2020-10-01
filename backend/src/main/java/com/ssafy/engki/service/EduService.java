package com.ssafy.engki.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.EduDto;
import com.ssafy.engki.entity.Image;
import com.ssafy.engki.mapper.EduMapper;
import com.ssafy.engki.repository.ImageRepository;
import com.ssafy.engki.repository.ThemeRepository;
import com.ssafy.engki.repository.WordRepository;

@RequiredArgsConstructor
@Service
public class EduService {
	private final ThemeRepository themeRepository;
	private final WordRepository wordRepository;
	private final ImageRepository imageRepository;

	public List<EduDto.Theme> getThemes() {
		return EduMapper.INSTANCE.toTheme(themeRepository.findAll());
	}

	public EduDto.Word getRandomImage(long themeId, long kidId) {
		Random rand = new Random(System.currentTimeMillis());

		// themeId의 단어 목록 중, 1. 아이가 배우지 않은 단어 중 랜덤
		List<com.ssafy.engki.entity.Word> words = wordRepository.getUnlearnedWord(themeId, kidId);
		com.ssafy.engki.entity.Word word;
		if (!words.isEmpty()) {
			word = words.get(rand.nextInt(words.size()));
		} else { // 배우지 않은 단어가 없음
			// 2. 가장 오래전에 배운 단어 고르기
			word = wordRepository.getOldestLearnedWord(themeId, kidId);
			System.out.println(word);
		}

		// 이 단어에 해당하는 사진 중, 이 단어만 있는 사진(다른 word가 없는 것) 고르기
		List<Image> images = imageRepository.getOneObjectImages(word.getId());
		Image image = images.get(rand.nextInt(images.size()));

		return EduDto.Word.builder()
			.id(word.getId())
			.word(word.getWord())
			.filePath(image.getFilePath())
			.build();
	}
}
