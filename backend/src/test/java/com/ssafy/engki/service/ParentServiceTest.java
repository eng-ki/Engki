package com.ssafy.engki.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ParentService.class})
class ParentServiceTest {
	@Autowired
	ParentService parentService;

	@Test
	void findById() {
	}

	@Test
	void login() {

	}
}
