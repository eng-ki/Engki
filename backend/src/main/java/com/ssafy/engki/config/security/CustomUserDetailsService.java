package com.ssafy.engki.config.security;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import com.ssafy.engki.exception.ParentNotFoundException;
import com.ssafy.engki.repository.ParentRepository;

@AllArgsConstructor
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private final ParentRepository parentRepository;

	@Override
	public CustomUserDetails loadUserByUsername(String userId) {
		return parentRepository.findById(Long.parseLong(userId))
			.map(u -> new CustomUserDetails(u,
				Collections.singleton(new SimpleGrantedAuthority("USER"))))
			.orElseThrow(() -> new ParentNotFoundException(Long.parseLong(userId)));
	}
}
