package com.ssafy.engki.controller;

import java.util.List;

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

import com.ssafy.engki.dto.EduDto;
import com.ssafy.engki.service.EduService;

@RequiredArgsConstructor
@RestController
@RequestMapping("edu")
@Api(tags = {"Edu Controller"})
@Tag(name = "Edu Controller", description = "학습과 관련된 기능")
public class EduController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final EduService eduService;

	@GetMapping
	@ApiOperation(value = "테마 목록 조회", notes = "테마 목록을 조회한다.", response = List.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<?> getThemes() {
		logger.debug("get Themes 호출");
		return new ResponseEntity<>(eduService.getThemes(), HttpStatus.OK);
	}

	@GetMapping("{themeId}/with/{kidId}")
	@ApiOperation(value = "Quiz 1 : 랜덤 이미지 조회",
		notes = "선택한 테마의 랜덤 이미지를 조회한다.\n"
			+ "아이가 배우지 않은 단어 중 랜덤 단어를, 모두 배웠다면 가장 이전에 배운 단어를 선택한다.",
		response = EduDto.Word.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<?> getRandomImageFromTheme(
		@ApiParam(value = "선택한 테마 id", example = "1") @PathVariable long themeId,
		@ApiParam(value = "아이 id", example = "1") @PathVariable long kidId) {
		logger.debug(String.format("get Random Image from theme %d with kid %d 호출", themeId, kidId));
		return new ResponseEntity<>(eduService.getRandomImage(themeId, kidId), HttpStatus.OK);
	}
}
