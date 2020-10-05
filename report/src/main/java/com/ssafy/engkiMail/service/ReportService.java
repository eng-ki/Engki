package com.ssafy.engkiMail.service;

import java.util.List;

import com.ssafy.engkiMail.dto.ReportDto;

public interface ReportService {

	public List<ReportDto> selectAllKids();
	public List<String> selectWordByKidId(int kid_id);
	public String selectEmotionByKidId(int kid_id);
	
}
