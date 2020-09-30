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

	@GetMapping("{kidId}/inventory")
	@ApiOperation(value = "아이 인벤토리 조회", notes = "아이의 인벤토리(스티커 목록)을 조회한다.", response = List.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<?> getKidInventory(
		@ApiParam(value = "아이 id", required = true, example = "1") @PathVariable long kidId) {
		logger.debug(String.format("get Kid {%d} inventory 호출", kidId));
		return new ResponseEntity<>(kidService.getInventory(kidId), HttpStatus.OK);
	}

	@PutMapping("{kidId}")
	@ApiOperation(value = "아이콘 수정", notes = "아이의 아이콘 정보를 수정한다..")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<?> updateKidIcon(
		@ApiParam(value = "아이 id", required = true, example = "1") @PathVariable long kidId,
		@ApiParam(value = "업데이트할 아이 아이콘 파일 경로", required = true) @RequestBody KidDto.KidIcon icon) {
		logger.debug(String.format("update Kid {%d} to icon {%s} 호출", kidId, icon.getIcon()));
		return new ResponseEntity<>(kidService.updateIcon(kidId, icon), HttpStatus.OK);
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
		@ApiParam(value = "아이 id", required = true, example = "1") @PathVariable long kidId) {
		logger.debug(String.format("delete Kid with %d 호출", kidId));
		kidService.delete(kidId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
