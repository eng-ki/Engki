package com.ssafy.engki.controller;

import java.util.List;

import org.mariadb.jdbc.internal.logging.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	private ResponseEntity<List<EduDto.Theme>> getThemes() {
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
	private ResponseEntity<EduDto.Word> getRandomImageFromTheme(
		@ApiParam(value = "선택한 테마 id", example = "1") @PathVariable long themeId,
		@ApiParam(value = "아이 id", example = "1") @PathVariable long kidId) {
		logger.debug(String.format("get Random Image from theme %d with kid %d 호출", themeId, kidId));
		return new ResponseEntity<>(eduService.getRandomImage(themeId, kidId), HttpStatus.OK);
	}

	@GetMapping("{wordId}/images")
	@ApiOperation(value = "Quiz 2 : 랜덤 이미지 목록 조회",
		notes = "선택한 단어의 이미지 목록을 조회한다.\n",
		response = List.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<List<EduDto.Image>> getRandomImagesWithWord(
		@ApiParam(value = "word id", example = "1") @PathVariable long wordId) {
		logger.debug(String.format("get Random Images with word %d 호출", wordId));
		return new ResponseEntity<>(eduService.getRandomImages(wordId), HttpStatus.OK);
	}

	@GetMapping("{wordId}/seg")
	@ApiOperation(value = "Quiz 3 : 세그멘테이션 이미지 조회",
		notes = "선택한 단어의 오리지널 이미지와 segmetation이 적용된 이미지, 보기로 사용할 랜덤 단어 하나를 조회한다.",
		response = EduDto.Segmentation.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<EduDto.Segmentation> getSegmentation(
		@ApiParam(value = "word id", example = "1") @PathVariable long wordId) {
		logger.debug(String.format("get Segmentation image with word %d 호출", wordId));
		return new ResponseEntity<>(eduService.getSegmentation(wordId), HttpStatus.OK);
	}

	@GetMapping("{wordId}/captions")
	@ApiOperation(value = "Quiz 4,5,6 : 이미지 캡션 조회",
		notes = "1. Quiz 4 : 오리지널 이미지의 캡션, (랜덤 순서로 섞인) 랜덤 단어 3개 + 정답 단어\n"
			+ "2. Quiz 5 : (랜덤 순서로 섞인) 같은 단어를 포함하지 않는 랜덤 캡션 3개 + 정답 캡션\n"
			+ "3. Quiz 6 : 오리지널 이미지 캡션의 (랜덤 순서로 섞인) Token(+원래 순서) 목록\n"
			+ "을 조회한다.",
		response = EduDto.Caption.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<EduDto.Caption> getCaption(
		@ApiParam(value = "word id", example = "1") @PathVariable long wordId) {
		logger.debug(String.format("get Caption with word %d 호출", wordId));
		return new ResponseEntity<>(eduService.getCaption(wordId), HttpStatus.OK);
	}

	@PostMapping("{kidId}")
	@ApiOperation(value = "학습 정보 저장", notes = "아이가 오늘 학습한 내용을 저장한다.")
	@ApiResponses(value = {
		@ApiResponse(code = 201, message = "Created"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<?> completeStudy(
		@ApiParam(value = "kid id", example = "1") @PathVariable long kidId,
		@ApiParam(value = "학습 정보") @RequestBody EduDto.Request studiedInfo
	) {
		logger.debug(String.format("complete study with kid %d 호출", kidId));
		eduService.completeStudy(kidId, studiedInfo);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
