package com.ssafy.engki.controller;

import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.CustomEduDto;
import com.ssafy.engki.service.CustomEduService;

@RequiredArgsConstructor
@RestController
@RequestMapping("custom")
@Api(tags = {"Custom Edu Controller"})
@Tag(name = "Custom Edu Controller", description = "커스텀 학습과 관련된 기능")
public class CustomEduController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final CustomEduService customEduService;

	@GetMapping("by/{parentId}")
	@ApiOperation(value = "Quiz 1 : 랜덤 이미지 조회",
		notes = "커스텀 학습에 추가한 이미지 중 랜덤 단어와 이미지를 조회한다.",
		response = CustomEduDto.CImage.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<CustomEduDto.CImage> getRandomImage(
		@ApiParam(value = "부모 id", example = "1486633352") @PathVariable long parentId) {
		logger.debug(String.format("get Random Image from parent {%d} 호출", parentId));
		return new ResponseEntity<>(customEduService.getRandomImage(parentId), HttpStatus.OK);
	}
}
