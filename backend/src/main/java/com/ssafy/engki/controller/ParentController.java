package com.ssafy.engki.controller;

import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.AccessTokenDto;
import com.ssafy.engki.service.ParentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("parents")
@Api(tags = {"Parent Controller"})
@Tag(name = "Parent Controller", description = "부모 회원과 관련된 기능")
public class ParentController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ParentService parentService;

	@PostMapping("login")
	@ApiOperation(value = "카카오 로그인", notes = "카카오 API를 이용한 로그인", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<?> login(
		@ApiParam(required = true) @RequestBody AccessTokenDto accessToken) {
		logger.debug(String.format("login with {%s}호출", accessToken));
		return new ResponseEntity<>(parentService.login(accessToken.getAccessToken()), HttpStatus.OK);
	}
}
