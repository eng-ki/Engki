package com.ssafy.engki.exception;

public class ParentNotFoundException extends BusinessException {
	public ParentNotFoundException(long parentId) {
		super(String.format("parentId %d is not exist.", parentId));
	}
}
