package com.ssafy.engki.service;

import static com.ssafy.engki.service.Util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.EduDto;
import com.ssafy.engki.entity.Image;
import com.ssafy.engki.entity.ImageCaption;
import com.ssafy.engki.entity.ImageWord;
import com.ssafy.engki.entity.Kid;
import com.ssafy.engki.entity.KidWord;
import com.ssafy.engki.entity.Word;
import com.ssafy.engki.mapper.EduMapper;
import com.ssafy.engki.repository.ImageCaptionRepository;
import com.ssafy.engki.repository.ImageRepository;
import com.ssafy.engki.repository.ImageWordRepository;
import com.ssafy.engki.repository.KidRepository;
import com.ssafy.engki.repository.KidWordRepository;
import com.ssafy.engki.repository.ThemeRepository;
import com.ssafy.engki.repository.WordRepository;

@RequiredArgsConstructor
@Service
public class EduService {
	private final ThemeRepository themeRepository;
	private final WordRepository wordRepository;
	private final ImageRepository imageRepository;
	private final ImageWordRepository imageWordRepository;
	private final ImageCaptionRepository imageCaptionRepository;
	private final KidWordRepository kidWordRepository;
	private final KidRepository kidRepository;

	public List<EduDto.Theme> getThemes() {
		return EduMapper.INSTANCE.toTheme(themeRepository.findAll());
	}

	public EduDto.Word getRandomImage(long themeId, long kidId) {
		Random rand = new Random(System.currentTimeMillis());

		// themeId의 단어 목록 중, 1. 아이가 배우지 않은 단어 중 랜덤
		List<Word> words = wordRepository.getUnlearnedWord(themeId, kidId);
		Word word;
		if (!words.isEmpty()) {
			word = words.get(rand.nextInt(words.size()));
		} else { // 배우지 않은 단어가 없음
			// 2. 랜덤 단어 고르기
			word = wordRepository.getRandomWordByTheme(themeId);
		}

		// 이 단어에 해당하는 사진 중, 이 단어만 있는 사진(다른 word가 없는 것) 고르기
		List<Image> images = imageRepository.getOneObjectImagesOfWord(word.getId());
		if (images.isEmpty()) {
			images = imageRepository.getAllByWordId(word.getId());
		}
		Image image = images.get(rand.nextInt(images.size()));

		return EduDto.Word.builder()
			.id(word.getId())
			.word(word.getWord())
			.wordKor(word.getWordKor())
			.filePath(image.getFilePath())
			.build();
	}

	public EduDto.Quiz2Response getRandomImages(long wordId) {
		EduDto.Quiz2Response response = new EduDto.Quiz2Response();

		Word word = wordRepository.getOne(wordId);

		// word의 이미지 최대 3개
		List<Image> wordImages = imageRepository.getOneObjectImagesOfWord(wordId);
		Util.getRandomNumbers(3, wordImages.size()).forEach(idx ->
			response.getImages().add(EduDto.Image.builder()
				.word(word.getWord())
				.wordKor(word.getWordKor())
				.filePath(wordImages.get(idx).getFilePath())
				.build())
		);
		response.setAnswerNum(Math.min(3, wordImages.size()));

		// word와 같은 theme의 이미지 중, wordId가 아닌 이미지 (6 - word 이미지 개수) 개
		List<ImageWord> notWordImages = imageWordRepository.getOneObjectImagesFromThemeNotWord(word.getThemeId(),
			wordId);
		Util.getRandomNumbers(6 - response.getAnswerNum(), notWordImages.size()).forEach(idx ->
			response.getImages().add(EduDto.Image.builder()
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

	public EduDto.Segmentation getSegmentation(long wordId) {
		Random rand = new Random(System.currentTimeMillis());

		List<ImageWord> imageWords = imageWordRepository.getAllByWordId(wordId);
		ImageWord imageWord = imageWords.get(rand.nextInt(imageWords.size()));

		List<Word> randomWords = wordRepository.getWordsByThemeExceptWord(imageWord.getWord().getThemeId(), wordId);
		Word randomWord = randomWords.get(rand.nextInt(randomWords.size()));

		return EduDto.Segmentation.builder()
			.filePath(imageWord.getImage().getFilePath())
			.segFilePath(imageWord.getBoundary())
			.randomWord(randomWord.getWord())
			.build();
	}

	public EduDto.Caption getCaption(long wordId) {
		Random rand = new Random(System.currentTimeMillis());

		List<ImageCaption> imageCaptions = imageCaptionRepository.getAllByWordId(wordId);
		ImageCaption imageCaption = imageCaptions.get(rand.nextInt(imageCaptions.size()));

		List<String> randomWords = new ArrayList<>(Collections.singletonList(imageCaption.getWord().getWord()));
		List<Word> wordsExceptWord
			= wordRepository.getWordsByThemeExceptWord(imageCaption.getWord().getThemeId(),
			imageCaption.getWord().getId());
		Util.getRandomNumbers(3, wordsExceptWord.size()).forEach(idx ->
			randomWords.add(wordsExceptWord.get(idx).getWord())
		);
		Collections.shuffle(randomWords);

		List<String> captionsExceptWord = imageCaptionRepository.findExceptImage(imageCaption.getId().getImageId());
		List<String> randomCaptions = new ArrayList<>(Collections.singletonList(imageCaption.getCaption()));
		Util.getRandomNumbers(3, captionsExceptWord.size()).forEach(idx ->
			randomCaptions.add(captionsExceptWord.get(idx))
		);
		Collections.shuffle(randomCaptions);

		return EduDto.Caption.builder()
			.filePath(imageCaption.getImage().getFilePath())
			.caption(imageCaption.getCaption())
			.captionKor(imageCaption.getCaptionKor())
			.randomWords(randomWords)
			.randomCaptions(randomCaptions)
			.tokens(tokenize(imageCaption.getCaption()))
			.build();
	}

	public void completeStudy(long kidId, EduDto.Request studyInfo) {
		Kid kid = kidRepository.getOne(kidId);
		kid.addExp(studyInfo.getExp());
		kidRepository.save(kid);

		KidWord kidWord = KidWord.builder()
			.kidId(kidId)
			.word(Word.builder().id(studyInfo.getWordId()).build())
			.studiedDate(new Date())
			.build();

		try {
			kidWordRepository.save(kidWord);
		} catch (Exception e) {
			if (!e.getMessage().contains("constraint [kid_word_date_uq]")) { // 중복 단어 배제(에러아님)
				throw e;
			}
		}
	}
}
