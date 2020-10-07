package com.ssafy.engki.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CustomImageCaptionId implements Serializable {
	@Column(nullable = false, updatable = false)
	private long imageId;

	@Column(nullable = false, updatable = false, length = 20)
	private String word;
}
