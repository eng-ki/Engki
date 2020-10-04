package com.ssafy.engki.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ssafy.engki.dto.KidDto;
import com.ssafy.engki.entity.Kid;

@Mapper
public interface KidMapper {
	KidMapper INSTANCE = Mappers.getMapper(KidMapper.class);

	KidDto.KidInfo toInfo(Kid kid);

	List<KidDto.KidInfo> toInfoList(List<Kid> kids);
}
