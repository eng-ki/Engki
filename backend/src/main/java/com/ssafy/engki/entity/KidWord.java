package com.ssafy.engki.entity;

import java.util.Date;

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
public class KidWord {
	@EmbeddedId
	private KidWordId id;

	@Column(nullable = false, updatable = false)
	private int count;

	@Column
	private Date recentDate;
}
