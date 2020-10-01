package com.ssafy.engki.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.EduDto;
import com.ssafy.engki.mapper.EduMapper;
import com.ssafy.engki.repository.ThemeRepository;

@RequiredArgsConstructor
@Service
public class EduService {
	private final ThemeRepository themeRepository;

	public List<EduDto.Theme> getThemes() {
		return EduMapper.INSTANCE.toTheme(themeRepository.findAll());
	}
}
