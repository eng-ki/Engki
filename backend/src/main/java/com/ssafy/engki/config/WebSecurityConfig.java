package com.ssafy.engki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import com.ssafy.engki.config.security.JwtAuthenticationFilter;
import com.ssafy.engki.config.security.JwtTokenProvider;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
			//swagger 관련 경로 무시
			.antMatchers("/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**", "/v3/api-docs/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
			.httpBasic().disable()
			// rest api이므로 csrf 보안은 필요 없음
			.csrf().disable()
			// form 기반 로그인 비활성화
			.formLogin().disable()
			// jwt token으로 인증하므로 세션은 필요 없음
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
			.antMatchers("/parents/login").permitAll() // 가입 및 인증 주소는 누구나 접근가능
			.anyRequest().authenticated() // 그외 나머지 요청은 모두 인증된 회원만 접근 가능
			// .and()
			// .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
				UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣는다
	}
}
