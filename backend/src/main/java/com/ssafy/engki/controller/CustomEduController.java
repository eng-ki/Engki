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

	@GetMapping("{word}/images/by/{parentId}")
	@ApiOperation(value = "Quiz 2 : 랜덤 이미지 목록 조회",
		notes = "선택한 단어의 이미지 목록을 조회한다.\n",
		response = CustomEduDto.CQuiz2Response.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<CustomEduDto.CQuiz2Response> getRandomImagesWithWord(
		@ApiParam(value = "배울 word", example = "traffic light") @PathVariable String word,
		@ApiParam(value = "부모 id", example = "1486633352") @PathVariable long parentId) {
		logger.debug(String.format("get Random Images with word %s 호출", word));
		return new ResponseEntity<>(customEduService.getRandomImages(word, parentId), HttpStatus.OK);
	}

	@GetMapping("{word}/seg/by/{parentId}")
	@ApiOperation(value = "Quiz 3 : 세그멘테이션 이미지 조회",
		notes = "선택한 단어의 오리지널 이미지와 segmetation이 적용된 이미지, 보기로 사용할 랜덤 단어 하나를 조회한다.",
		response = CustomEduDto.Segmentation.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<CustomEduDto.Segmentation> getSegmentation(
		@ApiParam(value = "배울 word", example = "traffic light") @PathVariable String word,
		@ApiParam(value = "부모 id", example = "1486633352") @PathVariable long parentId) {
		logger.debug(String.format("get Segmentation image with word %s 호출", word));
		return new ResponseEntity<>(customEduService.getSegmentation(word, parentId), HttpStatus.OK);
	}

	@GetMapping("{word}/captions/by/{parentId}")
	@ApiOperation(value = "Quiz 4,5,6 : 이미지 캡션 조회",
		notes = "1. Quiz 4 : 오리지널 이미지의 캡션, (랜덤 순서로 섞인) 랜덤 단어 3개 + 정답 단어\n"
			+ "2. Quiz 5 : (랜덤 순서로 섞인) 같은 단어를 포함하지 않는 랜덤 캡션 3개 + 정답 캡션\n"
			+ "3. Quiz 6 : 오리지널 이미지 캡션의 (랜덤 순서로 섞인) Token(+원래 순서) 목록\n"
			+ "을 조회한다.",
		response = CustomEduDto.Caption.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 401, message = "Unauthorized"),
		@ApiResponse(code = 403, message = "Forbidden"),
		@ApiResponse(code = 404, message = "Not Found")
	})
	private ResponseEntity<CustomEduDto.Caption> getCaption(
		@ApiParam(value = "word", example = "traffic light") @PathVariable String word,
		@ApiParam(value = "부모 id", example = "1486633352") @PathVariable long parentId) {
		logger.debug(String.format("get Caption with word %s 호출", word));
		return new ResponseEntity<>(customEduService.getCaption(word, parentId), HttpStatus.OK);
	}
}
