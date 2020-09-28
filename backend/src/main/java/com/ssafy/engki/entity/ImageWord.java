package com.ssafy.engki.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ImageWord {
	@EmbeddedId
	private ImageWordId id;

	@Column(nullable = false, updatable = false, length = 100)
	private String boundary;
}
