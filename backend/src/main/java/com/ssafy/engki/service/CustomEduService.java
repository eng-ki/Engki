package com.ssafy.engki.service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.CustomEduDto;
import com.ssafy.engki.entity.CustomImage;
import com.ssafy.engki.entity.CustomImageWord;
import com.ssafy.engki.entity.ImageWord;
import com.ssafy.engki.entity.Word;
import com.ssafy.engki.repository.CustomImageCaptionRepository;
import com.ssafy.engki.repository.CustomImageRepository;
import com.ssafy.engki.repository.CustomImageWordRepository;
import com.ssafy.engki.repository.ImageWordRepository;
import com.ssafy.engki.repository.KidRepository;
import com.ssafy.engki.repository.ParentRepository;
import com.ssafy.engki.repository.WordRepository;

@RequiredArgsConstructor
@Service
public class CustomEduService {
	private final ParentRepository parentRepository;
	private final KidRepository kidRepository;
	private final CustomImageRepository customImageRepository;
	private final CustomImageWordRepository customImageWordRepository;
	private final CustomImageCaptionRepository customImageCaptionRepository;
	private final ImageWordRepository imageWordRepository;
	private final WordRepository wordRepository;

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

	public CustomEduDto.CQuiz2Response getRandomImages(String word, long parentId) {
		CustomEduDto.CQuiz2Response response = new CustomEduDto.CQuiz2Response();

		// word의 이미지 최대 3개
		List<CustomImageWord> wordImages = customImageWordRepository.getImagesOfWord(word, parentId);
		Util.getRandomNumbers(3, wordImages.size()).forEach(idx ->
			response.getImages().add(CustomEduDto.CImage.builder()
				.word(wordImages.get(idx).getId().getWord())
				.wordKor(wordImages.get(idx).getWordKor())
				.filePath(wordImages.get(idx).getImage().getFilePath())
				.build())
		);
		response.setAnswerNum(Math.min(3, wordImages.size()));

		// word가 아닌 이미지 (6 - word 이미지 개수) 개
		List<ImageWord> notWordImages = imageWordRepository.getImagesNotWord(word);
		Util.getRandomNumbers(6 - response.getAnswerNum(), notWordImages.size()).forEach(idx ->
			response.getImages().add(CustomEduDto.CImage.builder()
				.word(notWordImages.get(idx).getWord().getWord())
				.wordKor(notWordImages.get(idx).getWord().getWordKor())
				.filePath(notWordImages.get(idx).getImage().getFilePath())
				.build()
			)
		);

		Collections.shuffle(response.getImages());

		// -> 6개 리턴
		return response;
	}

	public CustomEduDto.Segmentation getSegmentation(String word, long parentId) {
		Random rand = new Random(System.currentTimeMillis());

		List<CustomImageWord> imageWords = customImageWordRepository.getImagesOfWord(word, parentId);
		CustomImageWord customImageWord = imageWords.get(rand.nextInt(imageWords.size()));

		List<Word> randomWords = wordRepository.getWordsExceptWord(word);
		Word randomWord = randomWords.get(rand.nextInt(randomWords.size()));

		return CustomEduDto.Segmentation.builder()
			.filePath(customImageWord.getImage().getFilePath())
			.segFilePath(customImageWord.getBoundary())
			.randomWord(randomWord.getWord())
			.build();
	}
}
