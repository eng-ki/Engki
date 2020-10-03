package com.ssafy.engki.controller;

import java.util.List;

import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.ssafy.engki.dto.KidDto;
import com.ssafy.engki.dto.ParentDto;
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
	private ResponseEntity<String> login(
		@ApiParam(required = true) @RequestBody AccessTokenDto accessToken) {
		logger.debug(String.format("login with {%s}호출", accessToken));
		return new ResponseEntity<>(parentService.login(accessToken.getAccessToken()), HttpStatus.OK);
	}

	@GetMapping("{parentId}")
	@ApiOperation(value = "회원 정보 조회", notes = "부모 id로 개인정보를 조회한다.", response = ParentDto.ParentInfo.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<ParentDto.ParentInfo> getParentInfo(
		@ApiParam(value = "부모 id", required = true, example = "1486633352") @PathVariable long parentId) {
		logger.debug(String.format("get info with %d 호출", parentId));
		return new ResponseEntity<>(parentService.findById(parentId), HttpStatus.OK);
	}

	@PutMapping("{parentId}")
	@ApiOperation(value = "회원 정보 수정", notes = "부모 id로 개인정보를 수정한다.", response = ParentDto.ParentInfo.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<ParentDto.ParentInfo> updateParentInfo(
		@ApiParam(value = "부모 id", required = true, example = "1486633352") @PathVariable long parentId,
		@ApiParam(value = "업데이트할 부모 정보", required = true) @RequestBody ParentDto.ParentRequest parentReq) {
		logger.debug(String.format("update info with %d 호출", parentId));
		return new ResponseEntity<>(parentService.update(parentId, parentReq), HttpStatus.OK);
	}

	@DeleteMapping("{parentId}")
	@ApiOperation(value = "회원 탈퇴", notes = "부모 id로 정보를 삭제한다.")
	@ApiResponses(value = {
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<?> withdrawal(
		@ApiParam(value = "부모 id", required = true, example = "1486633352") @PathVariable long parentId) {
		logger.debug(String.format("withdrawal with %d 호출", parentId));
		parentService.withdrawal(parentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("{parentId}/kids")
	@ApiOperation(value = "아이 목록 조회", notes = "부모 id로 아이 목록을 조회한다.", response = List.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<List<KidDto.KidInfo>> getKids(
		@ApiParam(value = "부모 id", required = true, example = "1486633352") @PathVariable long parentId) {
		logger.debug("아이 목록 조회 with %d", parentId);
		return new ResponseEntity<>(parentService.getKidList(parentId), HttpStatus.OK);
	}
}
