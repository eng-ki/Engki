package com.ssafy.engki.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ssafy.engki.dto.EduDto;
import com.ssafy.engki.entity.Theme;

@Mapper
public interface EduMapper {
	EduMapper INSTANCE = Mappers.getMapper(EduMapper.class);

	List<EduDto.Theme> toTheme(List<Theme> themes);
}
