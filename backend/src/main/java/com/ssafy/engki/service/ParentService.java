package com.ssafy.engki.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.config.security.JwtTokenProvider;
import com.ssafy.engki.dto.KakaoUserDto;
import com.ssafy.engki.dto.ParentDto;
import com.ssafy.engki.entity.Parent;
import com.ssafy.engki.exception.ParentNotFoundException;
import com.ssafy.engki.mapper.ParentMapper;
import com.ssafy.engki.repository.ParentRepository;

@RequiredArgsConstructor
@Service
public class ParentService {
	private final ParentRepository parentRepository;

	private final JwtTokenProvider jwtTokenProvider;
	private final RestTemplate restTemplate = new RestTemplate(); // could not autowired
	private final Gson gson;
	@Value("${kakao.user.url}")
	private String kakaoUserUrl;

	public String login(String accessToken) {
		// Kakao 서버로 accessToken 보내서 user info 받아오기
		KakaoUserDto kakaoUserDto = getUserIdFromKakao(accessToken);

		// DB에 parent id가 없으면 새로 저장
		if (!parentRepository.existsById(kakaoUserDto.getId())) {
			save(kakaoUserDto.getId(), // id
				kakaoUserDto.getKakaoAccount().getProfile().getNickname(), // name
				kakaoUserDto.getKakaoAccount().getEmail(), // email
				true // initially true
			);
		}

		return jwtTokenProvider.createToken(kakaoUserDto.getId());
	}

	private Parent save(long id, String name, String email, boolean isAgreeToReceiveEmailReport) {
		Parent parent = new Parent(id, name, email,
			!email.isBlank() && isAgreeToReceiveEmailReport //if email is blank, flag should be false
		);
		return parentRepository.save(parent);
	}

	private KakaoUserDto getUserIdFromKakao(String accessToken) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
		String ret = restTemplate.exchange(kakaoUserUrl, HttpMethod.GET, httpEntity, String.class).getBody();

		return gson.fromJson(ret, KakaoUserDto.class);
	}

	public ParentDto.Info findById(long parentId) {
		return ParentMapper.INSTANCE.to(parentRepository.findById(parentId)
			.orElseThrow(() -> new ParentNotFoundException(parentId)));
	}

	public ParentDto.Info update(long parentId, ParentDto.Request parentReq) {
		Parent parent = new Parent(parentId, parentReq.getName(), parentReq.getEmail(),
			!parentReq.getEmail().isBlank() && parentReq.isReceiveEmailFlag());
		return ParentMapper.INSTANCE.to(parentRepository.save(parent));
	}

	public void withdrawal(long parentId) {
		parentRepository.deleteById(parentId);
	}
}
