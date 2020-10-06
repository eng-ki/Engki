package com.ssafy.engki.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CustomEduDto {
	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CImage {
		@ApiModelProperty(value = "word", example = "traffic light")
		private String word;

		@ApiModelProperty(value = "word 한국어", example = "신호등")
		private String wordKor;

		@ApiModelProperty(value = "image file path",
			example = "custom/1486633352/inputs/9C81ED1A-D227-4AA0-B052-7A8C251AF091._1006131950.jpg")
		private String filePath;
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CQuiz2Response {
		@ApiModelProperty(value = "정답 이미지 개수", example = "3")
		private int answerNum;

		@ApiModelProperty(value = "랜덤 이미지 목록")
		private List<CImage> images = new ArrayList<>();
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Segmentation {
		@ApiModelProperty(value = "original image file path",
			example = "custom/1486633352/inputs/9C81ED1A-D227-4AA0-B052-7A8C251AF091._1006131950.jpg")
		private String filePath;

		@ApiModelProperty(value = "classified image file path",
			example = "custom/1486633352/outputs/9C81ED1A-D227-4AA0-B052-7A8C251AF091._1006131950_traffic light.jpg")
		private String segFilePath;

		@ApiModelProperty(value = "보기로 사용할 랜덤 단어", example = "car")
		private String randomWord;
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Caption {
		@ApiModelProperty(value = "original image file path",
			example = "custom/1486633352/inputs/9C81ED1A-D227-4AA0-B052-7A8C251AF091._1006131950.jpg")
		private String filePath;

		@ApiModelProperty(value = "image caption", example = "a traffic light with a street light and street sign .")
		private String caption;

		@ApiModelProperty(value = "image caption 한국어 번역", example = "가로등과 가로 표지판이 있는 신호등")
		private String captionKor;

		@ApiModelProperty(value = "random word list (보기로 사용)",
			example = "['bicycle', "
				+ "'truck', "
				+ "'traffic light', "
				+ "'train' ]")
		private List<String> randomWords;

		@ApiModelProperty(value = "random caption list (보기로 사용)",
			example = "['a man on a horse in the snow .', "
				+ "'a bike parked next to a metal bench .', "
				+ "'a traffic light with a street light and street sign .', "
				+ "'a man on a bike is riding a rail with a dog .' ]")
		private List<String> randomCaptions;

		@ApiModelProperty(value = "caption의 token 목록",
			example = "["
				+ "{ 'token': 'street', 'order': 6},"
				+ "{ 'token': 'street', 'order': 9},"
				+ "{ 'token': 'with', 'order': 4 },"
				+ "{ 'token': 'a', 'order': 0 },"
				+ "{ 'token': 'traffic', 'order': 2 },"
				+ "{ 'token': 'sign', 'order': 10},"
				+ "{ 'token': 'light', 'order': 7 },"
				+ "{ 'token': 'and', 'order': 8 },"
				+ "{ 'token': 'light', 'order': 3 },"
				+ "{ 'token': 'a', 'order': 5 },"
				+ "{ 'token': 'a', 'order': 1 } ]")
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
		@ApiModelProperty(value = "추가될 exp", example = "21")
		private int exp;
	}
}
