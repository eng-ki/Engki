package com.ssafy.engki.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;

@Getter
@Embeddable
public class CustomImageWordId implements Serializable {
	@Column(nullable = false, updatable = false)
	private long imageId;

	@Column(nullable = false, updatable = false, length = 20)
	private String word;
}
