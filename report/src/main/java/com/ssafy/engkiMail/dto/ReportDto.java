package com.ssafy.engkiMail.dto;

import java.util.List;

public class ReportDto {
	private int parent_id;
	private int kid_id;
	private String kid_name;
	private String email;
	private List<String> words;
	private String emotion;

	
	@Override
	public String toString() {
		return "ReportDto [kid_name=" + kid_name + ", email="
				+ email + ", words=" + words + ", emotion=" + emotion + "]";
	}

	public String getEmotion() {
		return emotion;
	}

	public ReportDto(int parent_id, int kid_id, String kid_name, String email, List<String> words, String emotion) {
		super();
		this.parent_id = parent_id;
		this.kid_id = kid_id;
		this.kid_name = kid_name;
		this.email = email;
		this.words = words;
		this.emotion = emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public ReportDto() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public int getKid_id() {
		return kid_id;
	}

	public void setKid_id(int kid_id) {
		this.kid_id = kid_id;
	}

	public String getKid_name() {
		return kid_name;
	}

	public void setKid_name(String kid_name) {
		this.kid_name = kid_name;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

}
