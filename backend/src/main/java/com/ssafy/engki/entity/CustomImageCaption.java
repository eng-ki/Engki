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
public class CustomImageCaption {
	@EmbeddedId
	CustomImageCaptionId id;

	@Column(nullable = false, updatable = false, length = 100)
	private String caption;

	@Column(nullable = false, updatable = false, length = 200)
	private String captionKor;

	@ManyToOne
	@MapsId("imageId")
	private CustomImage image;
}
