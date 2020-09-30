package com.ssafy.engki.controller;

import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import com.ssafy.engki.dto.KidDto;
import com.ssafy.engki.service.KidService;

@RequiredArgsConstructor
@RestController
@RequestMapping("kids")
@Api(tags = {"Kid Controller"})
@Tag(name = "Kid Controller", description = "아이와 관련된 기능")
public class KidController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final KidService kidService;

	@PostMapping
	@ApiOperation(value = "아이 등록", notes = "아이 회원을 추가한다.", response = String.class)
	@ApiResponses(value = {
		@ApiResponse(code = 201, message = "Created"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<?> addKid(
		@ApiParam(value = "추가하고 싶은 아이 정보", required = true) @RequestBody KidDto.KidRequest kidRequest) {
		logger.debug("add kid 호출\n" + kidRequest.toString());
		return new ResponseEntity<>(kidService.add(kidRequest), HttpStatus.CREATED);
	}

	@DeleteMapping("{kidId}")
	@ApiOperation(value = "아이 삭제", notes = "아이 정보를 삭제한다.")
	@ApiResponses(value = {
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<?> deleteKid(
		@ApiParam(value = "아이 id", required = true) @PathVariable long kidId) {
		logger.debug(String.format("delete Kid with %d 호출", kidId));
		kidService.delete(kidId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
