package com.ssafy.engki.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Parent {
	@Id
	@Column(nullable = false, updatable = false)
	private long id;

	@Column(nullable = false, updatable = false, length = 20)
	private String name;

	@Column(length = 50)
	private String email;

	@Column(nullable = false)
	private boolean receiveEmailFlag;

	@OneToMany(
		cascade = CascadeType.ALL,
		mappedBy = "parentId")
	private List<Kid> kids;
}
