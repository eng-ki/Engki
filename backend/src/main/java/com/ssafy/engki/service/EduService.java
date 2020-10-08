package com.ssafy.engki.service;

import static com.ssafy.engki.service.Util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
		// themeId의 단어 목록 중, 1. 아이가 배우지 않은 단어 중 랜덤
		Word word = wordRepository.getRandomUnlearned(themeId, kidId)
			// 배우지 않은 단어가 없음 -> 2. 랜덤 단어 고르기
			.orElse(wordRepository.getRandomByTheme(themeId));

		// 이 단어에 해당하는 사진 중, 이 단어만 있는 사진(다른 word가 없는 것) 고르기
		Image image = imageRepository.getRandomOneObjectByWord(word.getId())
			// 없으면 단어를 포함하는 사진 중 랜덤
			.orElse(imageRepository.getRandomByWord(word.getId()));

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
		imageRepository.getRandomListOneObjectByWord(wordId, 3)
			.forEach(image ->
				response.getImages().add(
					EduDto.Image.builder()
						.word(word.getWord())
						.wordKor(word.getWordKor())
						.filePath(image.getFilePath())
						.build()));
		response.setAnswerNum(Math.min(3, response.getImages().size()));

		// word와 같은 theme의 이미지 중, wordId가 아닌 이미지 (6 - word 이미지 개수) 개
		imageWordRepository.getRandomListOneObjectByThemeExceptWord(
			word.getThemeId(), wordId, 6 - response.getAnswerNum())
			.forEach(notWordImage ->
				response.getImages().add(
					EduDto.Image.builder()
						.word(notWordImage.getWord().getWord())
						.wordKor(notWordImage.getWord().getWordKor())
						.filePath(notWordImage.getImage().getFilePath())
						.build()));

		// 같은 테마의 이미지가 부족하면 다른 테마에서 가져온다
		if (response.getImages().size() < 6) {
			imageWordRepository.getRandomListOneObjectExceptTheme(
				word.getThemeId(), 6 - response.getImages().size())
				.forEach(notWordImage ->
					response.getImages().add(
						EduDto.Image.builder()
							.word(notWordImage.getWord().getWord())
							.wordKor(notWordImage.getWord().getWordKor())
							.filePath(notWordImage.getImage().getFilePath())
							.build()));
		}

		Collections.shuffle(response.getImages());
		return response;
	}

	public EduDto.Segmentation getSegmentation(long wordId) {
		ImageWord imageWord = imageWordRepository.getRandomByWord(wordId);
		Word randomWord = wordRepository.getRandomByThemeExceptWord(imageWord.getWord().getThemeId(), wordId)
			// 테마에 다른 word가 없으면 다른 테마에서 랜덤으로 가져온다
			.orElse(wordRepository.getRandomExceptTheme(imageWord.getWord().getThemeId()));

		return EduDto.Segmentation.builder()
			.filePath(imageWord.getImage().getFilePath())
			.segFilePath(imageWord.getBoundary())
			.randomWord(randomWord.getWord())
			.build();
	}

	public EduDto.Caption getCaption(long wordId) {
		ImageCaption imageCaption = imageCaptionRepository.getRandomByWord(wordId);

		List<String> randomWords
			= new ArrayList<>(Collections.singletonList(imageCaption.getWord().getWord()));

		// 같은 테마의 Random word 3개 가져오기
		wordRepository.getRandomListByThemeExceptWord(
			imageCaption.getWord().getThemeId(), imageCaption.getWord().getId(), 3)
			.forEach(word ->
				randomWords.add(word.getWord()));

		// 가져온 Random word가 부족하면 다른 테마에서 추가 조회
		if (randomWords.size() < 4) {
			wordRepository.getRandomListExceptTheme(
				imageCaption.getWord().getThemeId(), 4 - randomWords.size())
				.forEach(word ->
					randomWords.add(word.getWord()));
		}

		List<String> randomCaptions
			= new ArrayList<>(Collections.singletonList(imageCaption.getCaption()));

		// Random caption 3개 가져오기 (다른 이미지)
		imageCaptionRepository.getRandomListExceptImage(imageCaption.getId().getImageId(), 3)
			.forEach(caption ->
				randomCaptions.add(caption.getCaption()));

		Collections.shuffle(randomWords);
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
