package com.ssafy.engki.dto;

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

		@ApiModelProperty(value = "image file path", example = "inputs\\bicycle\\bicycle1.jpg")
		private String filePath;
	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Image {
		@ApiModelProperty(value = "word", example = "bicycle")
		private String word;

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
	}
}
