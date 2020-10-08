package com.ssafy.engki.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ssafy.engki.dto.ParentDto;
import com.ssafy.engki.entity.Parent;

class ParentMapperTest {

	@Test
	void toInfo() {
		Parent parent = new Parent(123456789, "name", "email", true, null, null);
		ParentDto.ParentInfo parentInfo = ParentMapper.INSTANCE.to(parent);

		assertEquals(parentInfo.getId(), parent.getId());
		assertEquals(parentInfo.getName(), parent.getName());
		assertEquals(parentInfo.getEmail(), parent.getEmail());
		assertEquals(parentInfo.isReceiveEmailFlag(), parent.isReceiveEmailFlag());
	}
}
