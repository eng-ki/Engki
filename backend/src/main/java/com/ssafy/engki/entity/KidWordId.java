package com.ssafy.engki.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class KidWordId implements Serializable {
	@Column(nullable = false, updatable = false)
	private long kidId;

	@Column(nullable = false, updatable = false)
	private long wordId;
}
