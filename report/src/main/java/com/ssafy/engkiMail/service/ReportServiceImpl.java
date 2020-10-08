package com.ssafy.engkiMail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.engkiMail.dto.ReportDto;
import com.ssafy.engkiMail.mapper.ReportMapper;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportMapper reportMapper;

	@Override
	public List<ReportDto> selectAllKids() {
		return reportMapper.selectAllKids();
	}

	@Override
	public List<String> selectWordByKidId(int kid_id) {
		return reportMapper.selectWordByKidId(kid_id);
	}

	@Override
	public String selectEmotionByKidId(int kid_id) {
		return reportMapper.selectEmotionByKidId(kid_id);
	}

}
