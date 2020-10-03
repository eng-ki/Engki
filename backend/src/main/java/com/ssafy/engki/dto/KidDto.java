package com.ssafy.engki.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class KidDto {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KidInfo {
		@ApiModelProperty(value = "Kid id", example = "1")
		private long id;

		@ApiModelProperty(value = "Kid name", example = "김아가")
		private String name;

		@JsonFormat(pattern = "yyyy-MM-dd")
		@ApiModelProperty(value = "Kid birthday", example = "2015-03-28")
		private Date birthday;

		@ApiModelProperty(value = "Kid icon file path", example = "/img/icon/fairytale/002-wizard.png")
		private String icon;

		@ApiModelProperty(value = "Kid exp", example = "0")
		private long exp;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KidRequest {
		@ApiModelProperty(value = "Parent id", example = "1486633352")
		private long parentId;

		@ApiModelProperty(value = "Kid name", example = "김아가")
		private String name;

		@JsonFormat(pattern = "yyyy-MM-dd")
		@ApiModelProperty(value = "Kid birthday", example = "2015-03-28")
		private Date birthday;

		@ApiModelProperty(value = "Kid icon file path", example = "/img/icon/fairytale/002-wizard.png")
		private String icon;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class KidIcon {
		@ApiModelProperty(value = "Kid icon file path", example = "/img/icon/fairytale/002-wizard.png")
		private String icon;
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class StudyInfo {
		@ApiModelProperty(value = "날짜")
		private Date date;

		@ApiModelProperty(value = "배운 단어 목록", example = "['bear', 'apple']")
		private List<String> words = new ArrayList<>();

		@ApiModelProperty(value = "감정 평균")
		private Emotion emotion;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Emotion {
		private BigDecimal angry;
		private BigDecimal disgusting;
		private BigDecimal fearful;
		private BigDecimal happy;
		private BigDecimal sad;
		private BigDecimal surprising;
		private BigDecimal neutral;

		public Emotion(AvgEmotion emotion) {
			angry = emotion.getAngry();
			disgusting = emotion.getDisgusting();
			fearful = emotion.getFearful();
			happy = emotion.getHappy();
			sad = emotion.getSad();
			surprising = emotion.getSurprising();
			neutral = emotion.getNeutral();
		}

		public static Emotion getEmotionOrNull(AvgEmotion emotion) {
			if (emotion.getAngry() == null) {
				return null;
			}
			return new Emotion(emotion);
		}
	}
}
