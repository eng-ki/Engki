package com.ssafy.engki.service;

import static com.ssafy.engki.service.Util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.CustomEduDto;
import com.ssafy.engki.entity.CustomImage;
import com.ssafy.engki.entity.CustomImageCaption;
import com.ssafy.engki.entity.CustomImageWord;
import com.ssafy.engki.entity.Kid;
import com.ssafy.engki.entity.Word;
import com.ssafy.engki.repository.CustomImageCaptionRepository;
import com.ssafy.engki.repository.CustomImageWordRepository;
import com.ssafy.engki.repository.ImageCaptionRepository;
import com.ssafy.engki.repository.ImageWordRepository;
import com.ssafy.engki.repository.KidRepository;
import com.ssafy.engki.repository.ParentRepository;
import com.ssafy.engki.repository.WordRepository;

@RequiredArgsConstructor
@Service
public class CustomEduService {
	private final ParentRepository parentRepository;
	private final KidRepository kidRepository;
	private final CustomImageWordRepository customImageWordRepository;
	private final CustomImageCaptionRepository customImageCaptionRepository;
	private final ImageWordRepository imageWordRepository;
	private final WordRepository wordRepository;
	private final ImageCaptionRepository imageCaptionRepository;

	public CustomEduDto.CImage getRandomImage(long parentId) {
		// parentId의 사진 목록
		List<Long> imageIds = parentRepository.getOne(parentId).getCustomImages().stream()
			.map(CustomImage::getId).collect(Collectors.toList());

		// 중 캡션에도 있고 단어에도 있는 랜덤 단어 & 사진
		CustomImageWord customImageWord = customImageWordRepository.getRandom(imageIds);

		return CustomEduDto.CImage.builder()
			.word(customImageWord.getId().getWord())
			.wordKor(customImageWord.getWordKor())
			.filePath(customImageWord.getImage().getFilePath())
			.build();
	}

	public CustomEduDto.CQuiz2Response getRandomImages(String word, long parentId) {
		CustomEduDto.CQuiz2Response response = new CustomEduDto.CQuiz2Response();

		// word의 이미지 최대 3개
		customImageWordRepository.getRandomListByWord(word, parentId, 3)
			.forEach(imageWord ->
				response.getImages().add(
					CustomEduDto.CImage.builder()
						.word(imageWord.getId().getWord())
						.wordKor(imageWord.getWordKor())
						.filePath(imageWord.getImage().getFilePath())
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
						.build()
				)
			);

		Collections.shuffle(response.getImages());
		return response;
	}

	public CustomEduDto.Segmentation getSegmentation(String word, long parentId) {
		CustomImageWord customImageWord
			= customImageWordRepository.getRandomByWord(word, parentId);
		Word randomWord = wordRepository.getRandomExceptWord(word);

		return CustomEduDto.Segmentation.builder()
			.filePath(customImageWord.getImage().getFilePath())
			.segFilePath(customImageWord.getBoundary())
			.randomWord(randomWord.getWord())
			.build();
	}

	public CustomEduDto.Caption getCaption(String word, long parentId) {
		CustomImageCaption customImageCaption
			= customImageCaptionRepository.getRandomByWord(word, parentId);

		List<String> randomWords = new ArrayList<>(Collections.singletonList(word));
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

	public void completeStudy(long kidId, CustomEduDto.CRequest studiedInfo) {
		Kid kid = kidRepository.getOne(kidId);
		kid.addExp(studiedInfo.getExp());
		kidRepository.save(kid);
	}
}
