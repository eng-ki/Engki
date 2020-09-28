package com.ssafy.engki.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccessTokenDto {
	@ApiModelProperty(value = "카카오 로그인 후 받는 access token")
	private String accessToken;
}
