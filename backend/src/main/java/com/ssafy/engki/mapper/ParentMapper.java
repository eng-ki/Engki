package com.ssafy.engki.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ssafy.engki.dto.ParentDto;
import com.ssafy.engki.entity.Parent;

@Mapper
public interface ParentMapper {
	ParentMapper INSTANCE = Mappers.getMapper(ParentMapper.class);

	ParentDto.Info to(Parent parent);
}
