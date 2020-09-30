package com.ssafy.engki.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.dto.KidDto;
import com.ssafy.engki.entity.Kid;
import com.ssafy.engki.repository.KidRepository;

@RequiredArgsConstructor
@Service
public class KidService {
	private final KidRepository kidRepository;

	public long add(KidDto.KidRequest kidRequest) {
		Kid kid = new Kid(0, kidRequest.getParentId(), kidRequest.getName(), kidRequest.getBirthday(),
			kidRequest.getIcon());
		return kidRepository.save(kid).getId();

	}

	public String updateIcon(long kidId, KidDto.KidIcon icon) {
		Kid kid = kidRepository.getOne(kidId);
		kid.updateIcon(icon.getIcon());
		return kidRepository.save(kid).getIcon();
	}

	public void delete(long kidId) {
		kidRepository.deleteById(kidId);
	}
}
