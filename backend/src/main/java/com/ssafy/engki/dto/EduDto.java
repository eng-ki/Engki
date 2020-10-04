package com.ssafy.engki.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EduDto {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Theme {
		@ApiModelProperty(value = "theme id", example = "1")
		private long id;

		@ApiModelProperty(value = "theme name", example = "vehicle")
		private String name;

		@ApiModelProperty(value = "theme icon file path", example = "theme/transportation.png")
		private String icon;
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Word {
		@ApiModelProperty(value = "word id", example = "1")
		private long id;

		@ApiModelProperty(value = "word", example = "bicycle")
		private String word;

		@ApiModelProperty(value = "word 한국어", example = "자전거")
		private String wordKor;

		@ApiModelProperty(value = "image file path", example = "inputs\\bicycle\\bicycle1.jpg")
		private String filePath;
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Quiz2Response {
		@ApiModelProperty(value = "정답 이미지 개수", example = "3")
		private int answerNum;

		@ApiModelProperty(value = "랜덤 이미지 목록")
		private List<Image> images = new ArrayList<>();
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Image {
		@ApiModelProperty(value = "word", example = "bicycle")
		private String word;

		@ApiModelProperty(value = "word 한국어", example = "자전거")
		private String wordKor;

		@ApiModelProperty(value = "image file path", example = "inputs\\bicycle\\bicycle1.jpg")
		private String filePath;
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Segmentation {
		@ApiModelProperty(value = "original image file path", example = "inputs\\bicycle\\bicycle1.jpg")
		private String filePath;

		@ApiModelProperty(value = "classified image file path", example = "outputs/bicycle\\bicycle1_class_bicycle.png")
		private String segFilePath;

		@ApiModelProperty(value = "classified image의 word", example = "bicycle")
		private String randomWord;
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Caption {
		@ApiModelProperty(value = "original image file path", example = "inputs\\bicycle\\bicycle1.jpg")
		private String filePath;

		@ApiModelProperty(value = "image caption", example = "a bike parked next to a metal bench .")
		private String caption;

		@ApiModelProperty(value = "image caption 한국어 번역", example = "금속 벤치 옆에 주차된 자전거")
		private String captionKor;

		@ApiModelProperty(value = "random word list (보기로 사용)",
			example = "['bicycle', "
				+ "'truck', "
				+ "'airplane', "
				+ "'train' ]")
		private List<String> randomWords;

		@ApiModelProperty(value = "random caption list (보기로 사용)",
			example = "['a man on a horse in the snow .', "
				+ "'a bike parked next to a metal bench .', "
				+ "'a man with glasses and a tie is smiling', "
				+ "'a man on a bike is riding a rail with a dog .' ]")
		private List<String> randomCaptions;

		@ApiModelProperty(value = "caption의 token 목록",
			example = "["
				+ "{ 'token': 'metal', 'order': 6},"
				+ "{ 'token': 'to', 'order': 4 },"
				+ "{ 'token': 'a', 'order': 0 },"
				+ "{ 'token': 'parked', 'order': 2 },"
				+ "{ 'token': 'bench', 'order': 7 },"
				+ "{ 'token': 'next', 'order': 3 },"
				+ "{ 'token': 'a', 'order': 5 },"
				+ "{ 'token': 'bike', 'order': 1 } ]")
		private List<Token> tokens;
	}

	@Data
	@AllArgsConstructor
	public static class Token {
		private String token;
		private int order;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
		@ApiModelProperty(value = "배운 단어 id", example = "1")
		private long wordId;

		@ApiModelProperty(value = "추가될 exp", example = "21")
		private int exp;
	}
}
