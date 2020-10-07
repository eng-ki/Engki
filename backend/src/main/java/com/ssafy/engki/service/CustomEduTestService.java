package com.ssafy.engki.service;

import static com.ssafy.engki.service.Util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.CustomEduDto;
import com.ssafy.engki.entity.CustomImageCaption;
import com.ssafy.engki.entity.CustomImageCaptionId;
import com.ssafy.engki.entity.CustomImageWord;
import com.ssafy.engki.entity.CustomImageWordId;
import com.ssafy.engki.entity.ImageWord;
import com.ssafy.engki.entity.Word;
import com.ssafy.engki.repository.CustomImageCaptionRepository;
import com.ssafy.engki.repository.CustomImageRepository;
import com.ssafy.engki.repository.CustomImageWordRepository;
import com.ssafy.engki.repository.ImageCaptionRepository;
import com.ssafy.engki.repository.ImageWordRepository;
import com.ssafy.engki.repository.KidRepository;
import com.ssafy.engki.repository.ParentRepository;
import com.ssafy.engki.repository.WordRepository;

@RequiredArgsConstructor
@Service
public class CustomEduTestService {
	private final ParentRepository parentRepository;
	private final KidRepository kidRepository;
	private final CustomImageRepository customImageRepository;
	private final CustomImageWordRepository customImageWordRepository;
	private final CustomImageCaptionRepository customImageCaptionRepository;
	private final ImageWordRepository imageWordRepository;
	private final WordRepository wordRepository;
	private final ImageCaptionRepository imageCaptionRepository;

	public CustomEduDto.CQuiz2Response getRandomImages(long imageId, String word, long parentId) {
		CustomEduDto.CQuiz2Response response = new CustomEduDto.CQuiz2Response();

		CustomImageWord customImageWord = customImageWordRepository.getOne(new CustomImageWordId(imageId, word));
		response.getImages().add(CustomEduDto.CImage.builder()
			.word(customImageWord.getId().getWord())
			.wordKor(customImageWord.getWordKor())
			.filePath(customImageWord.getImage().getFilePath())
			.build());

		// word의 이미지 최대 3개
		List<CustomImageWord> wordImages
			= customImageWordRepository.getImagesOfWordExceptImage(word, parentId, imageId);
		Util.getRandomNumbers(2, wordImages.size()).forEach(idx ->
			response.getImages().add(CustomEduDto.CImage.builder()
				.word(wordImages.get(idx).getId().getWord())
				.wordKor(wordImages.get(idx).getWordKor())
				.filePath(wordImages.get(idx).getImage().getFilePath())
				.build())
		);
		response.setAnswerNum(Math.min(3, wordImages.size() + 1));

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

	public CustomEduDto.Segmentation getSegmentation(long imageId, String word) {
		Random rand = new Random(System.currentTimeMillis());

		CustomImageWord customImageWord
			= customImageWordRepository.getOne(new CustomImageWordId(imageId, word));

		List<Word> randomWords = wordRepository.getWordsExceptWord(word);
		Word randomWord = randomWords.get(rand.nextInt(randomWords.size()));

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
		List<Word> wordsExceptWord = wordRepository.getWordsExceptWord(customImageCaption.getId().getWord());
		Util.getRandomNumbers(3, wordsExceptWord.size()).forEach(idx ->
			randomWords.add(wordsExceptWord.get(idx).getWord())
		);
		Collections.shuffle(randomWords);

		List<String> captionsExceptWord
			= imageCaptionRepository.getWordsExceptWord(customImageCaption.getId().getWord());
		List<String> randomCaptions = new ArrayList<>(Collections.singletonList(customImageCaption.getCaption()));
		Util.getRandomNumbers(3, captionsExceptWord.size()).forEach(idx ->
			randomCaptions.add(captionsExceptWord.get(idx))
		);
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
