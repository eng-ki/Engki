package com.ssafy.engki.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ParentDto {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Info {
		@ApiModelProperty(value = "Parent id", example = "1486633352")
		private long id;

		@ApiModelProperty(value = "Parent name", example = "김싸피")
		private String name;

		@ApiModelProperty(value = "Parent email", example = "ssafy@ssafy.com")
		private String email;

		@ApiModelProperty(value = "Parent agreement to receive email report", example = "true")
		private boolean receiveEmailFlag;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
		@ApiModelProperty(value = "Parent name", example = "김싸피")
		private String name;

		@ApiModelProperty(value = "Parent email", example = "ssafy@ssafy.com")
		private String email;

		@ApiModelProperty(value = "Parent agreement to receive email report", example = "true")
		private boolean receiveEmailFlag;
	}
}
