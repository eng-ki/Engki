package com.ssafy.engki.dto;

import java.math.BigDecimal;

public interface AvgEmotion {
	BigDecimal getAngry();

	BigDecimal getDisgusting();

	BigDecimal getFearful();

	BigDecimal getNeutral();

	BigDecimal getHappy();

	BigDecimal getSad();

	BigDecimal getSurprising();
}
