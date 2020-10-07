package com.ssafy.engki.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CustomImageWord {
	@EmbeddedId
	CustomImageWordId id;

	@Column(nullable = false, updatable = false, length = 100)
	private String boundary;

	@Column(nullable = false, updatable = false, length = 200)
	private String wordKor;

	@ManyToOne
	@MapsId(value = "imageId")
	private CustomImage image;
}
