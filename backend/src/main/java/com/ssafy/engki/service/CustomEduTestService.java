package com.ssafy.engki.service;

import static com.ssafy.engki.service.Util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.CustomEduDto;
import com.ssafy.engki.entity.CustomImageCaption;
import com.ssafy.engki.entity.CustomImageCaptionId;
import com.ssafy.engki.entity.CustomImageWord;
import com.ssafy.engki.entity.CustomImageWordId;
import com.ssafy.engki.entity.Word;
import com.ssafy.engki.repository.CustomImageCaptionRepository;
import com.ssafy.engki.repository.CustomImageWordRepository;
import com.ssafy.engki.repository.ImageCaptionRepository;
import com.ssafy.engki.repository.ImageWordRepository;
import com.ssafy.engki.repository.WordRepository;

@RequiredArgsConstructor
@Service
public class CustomEduTestService {
	private final CustomImageWordRepository customImageWordRepository;
	private final CustomImageCaptionRepository customImageCaptionRepository;
	private final ImageWordRepository imageWordRepository;
	private final WordRepository wordRepository;
	private final ImageCaptionRepository imageCaptionRepository;

	public CustomEduDto.CQuiz2Response getRandomImages(long imageId, String word, long parentId) {
		CustomEduDto.CQuiz2Response response = new CustomEduDto.CQuiz2Response();

		// 지정 이미지 추가
		CustomImageWord customImageWord
			= customImageWordRepository.getOne(new CustomImageWordId(imageId, word));
		response.getImages().add(CustomEduDto.CImage.builder()
			.word(customImageWord.getId().getWord())
			.wordKor(customImageWord.getWordKor())
			.filePath(customImageWord.getImage().getFilePath())
			.build());

		// word의 이미지 최대 2개
		customImageWordRepository.getRandomListByWordExceptImage(word, parentId, imageId, 2)
			.forEach(wordImage ->
				response.getImages().add(CustomEduDto.CImage.builder()
					.word(wordImage.getId().getWord())
					.wordKor(wordImage.getWordKor())
					.filePath(wordImage.getImage().getFilePath())
					.build()));
		response.setAnswerNum(Math.min(3, response.getImages().size()));

		// word가 아닌 이미지 (6 - word 이미지 개수) 개
		imageWordRepository.getRandomListExceptWord(word, 6 - response.getAnswerNum())
			.forEach(notWordImage ->
				response.getImages().add(
					CustomEduDto.CImage.builder()
						.word(notWordImage.getWord().getWord())
						.wordKor(notWordImage.getWord().getWordKor())
						.filePath(notWordImage.getImage().getFilePath())
						.build()));

		Collections.shuffle(response.getImages());
		return response;
	}

	public CustomEduDto.Segmentation getSegmentation(long imageId, String word) {
		CustomImageWord customImageWord
			= customImageWordRepository.getOne(new CustomImageWordId(imageId, word));
		Word randomWord = wordRepository.getRandomExceptWord(word);

		return CustomEduDto.Segmentation.builder()
			.filePath(customImageWord.getImage().getFilePath())
			.segFilePath(customImageWord.getBoundary())
			.randomWord(randomWord.getWord())
			.build();
	}

	public CustomEduDto.Caption getCaption(long imageId, String word) {
		CustomImageCaption customImageCaption
			= customImageCaptionRepository.findById(new CustomImageCaptionId(imageId, word))
			.orElse(customImageCaptionRepository.getOne(imageId));

		List<String> randomWords = new ArrayList<>(Collections.singletonList(customImageCaption.getId().getWord()));
		wordRepository.getRandomListExceptWord(word, 3)
			.forEach(randomWord ->
				randomWords.add(randomWord.getWord()));

		List<String> randomCaptions = new ArrayList<>(Collections.singletonList(customImageCaption.getCaption()));
		imageCaptionRepository.getRandomListExceptWord(word, 3)
			.forEach(caption ->
				randomCaptions.add(caption.getCaption()));

		Collections.shuffle(randomWords);
		Collections.shuffle(randomCaptions);
		return CustomEduDto.Caption.builder()
			.filePath(customImageCaption.getImage().getFilePath())
			.caption(customImageCaption.getCaption())
			.captionKor(customImageCaption.getCaptionKor())
			.randomWords(randomWords)
			.randomCaptions(randomCaptions)
			.tokens(tokenize(customImageCaption.getCaption()))
			.build();
	}
}
