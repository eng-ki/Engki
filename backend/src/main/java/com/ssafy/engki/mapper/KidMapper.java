package com.ssafy.engki.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.ssafy.engki.dto.KidDto;
import com.ssafy.engki.entity.Kid;
import com.ssafy.engki.entity.Sticker;

@Mapper
public interface KidMapper {
	KidMapper INSTANCE = Mappers.getMapper(KidMapper.class);

	List<KidDto.KidInfo> toInfo(List<Kid> kids);

	List<KidDto.KidSticker> toSticker(List<Sticker> stickers);
}
