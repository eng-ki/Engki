package com.ssafy.engki.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.ssafy.engki.dto.EduDto;

public class Util {
	public static Stream<Integer> getRandomNumbers(int count, int upperbound) {
		if (upperbound < count) {
			return IntStream.range(0, upperbound).boxed();
		}

		Random rand = new Random(System.currentTimeMillis());
		Set<Integer> set = new HashSet<>();
		while (set.size() < count) {
			set.add(rand.nextInt(upperbound));
		}
		return set.stream();
	}

	public static List<EduDto.Token> tokenize(String caption) {
		caption = caption.replace(".", ""); //. 없애기
		List<EduDto.Token> tokens = new ArrayList<>();

		String[] splitCaption = caption.split(" ");
		for (int i = 0; i < splitCaption.length; i++) {
			tokens.add(new EduDto.Token(splitCaption[i], i));
		}

		Collections.shuffle(tokens, new Random(System.currentTimeMillis()));

		return tokens;
	}
}
