package com.ssafy.engki.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ImageWordId implements Serializable {
	@Column(nullable = false, updatable = false)
	private long imageId;

	@Column(nullable = false, updatable = false)
	private long wordId;
}
