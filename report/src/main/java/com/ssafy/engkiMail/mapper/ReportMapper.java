package com.ssafy.engkiMail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.engkiMail.dto.ReportDto;

@Mapper
public interface ReportMapper {
	
	public List<ReportDto> selectAllKids();
	public List<String> selectWordByKidId(int kid_id);
	public String selectEmotionByKidId(int kid_id);

}
