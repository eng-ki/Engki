package com.ssafy.engki.controller;

import java.util.List;

import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

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
}
