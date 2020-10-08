package com.ssafy.engki.exception;

import org.springframework.http.HttpStatus;

public class ParentNotFoundException extends BusinessException {
	public ParentNotFoundException(long parentId) {
		super(String.format("parentId %d is not exist.", parentId), HttpStatus.BAD_REQUEST);
	}
}
