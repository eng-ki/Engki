package com.ssafy.engki.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.CustomEduDto;
import com.ssafy.engki.entity.CustomImage;
import com.ssafy.engki.entity.CustomImageWord;
import com.ssafy.engki.repository.CustomImageCaptionRepository;
import com.ssafy.engki.repository.CustomImageRepository;
import com.ssafy.engki.repository.CustomImageWordRepository;
import com.ssafy.engki.repository.KidRepository;
import com.ssafy.engki.repository.ParentRepository;

@RequiredArgsConstructor
@Service
public class CustomEduService {
	private final ParentRepository parentRepository;
	private final KidRepository kidRepository;
	private final CustomImageRepository customImageRepository;
	private final CustomImageWordRepository customImageWordRepository;
	private final CustomImageCaptionRepository customImageCaptionRepository;

	public CustomEduDto.CImage getRandomImage(long parentId) {
		Random rand = new Random(System.currentTimeMillis());

		// parentId의 사진 목록
		List<Long> imageIds = parentRepository.getOne(parentId).getCustomImages().stream()
			.map(CustomImage::getId).collect(Collectors.toList());

		// 중 캡션에도 있고 단어에도 있는 랜덤 단어 & 사진
		List<CustomImageWord> imageWords = customImageWordRepository.findAllWords(imageIds);
		CustomImageWord customImageWord = imageWords.get(rand.nextInt(imageWords.size()));

		return CustomEduDto.CImage.builder()
			.word(customImageWord.getId().getWord())
			.wordKor(customImageWord.getWordKor())
			.filePath(customImageWord.getImage().getFilePath())
			.build();
	}
}
