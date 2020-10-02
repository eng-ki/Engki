package com.ssafy.engki.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class KidEmotion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private long id;

	@Column(nullable = false, updatable = false)
	private long kidId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	private Date evaluateTime;

	@Column(updatable = false, precision = 5, scale = 2)
	private BigDecimal angry;

	@Column(updatable = false, precision = 5, scale = 2)
	private BigDecimal disgusting;

	@Column(updatable = false, precision = 5, scale = 2)
	private BigDecimal fearful;

	@Column(updatable = false, precision = 5, scale = 2)
	private BigDecimal happy;

	@Column(updatable = false, precision = 5, scale = 2)
	private BigDecimal sad;

	@Column(updatable = false, precision = 5, scale = 2)
	private BigDecimal surprising;

	@Column(updatable = false, precision = 5, scale = 2)
	private BigDecimal neutral;
}
