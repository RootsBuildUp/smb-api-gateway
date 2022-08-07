package com.smb.springcloudgateway;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class NumUtilsTest {

	@Test
	void testIsZeroBigDecimal() {
		BigDecimal two = BigDecimal.ONE;
		BigDecimal zero = BigDecimal.ZERO;
		Assertions.assertTrue(NumUtils.isZero(zero));
		Assertions.assertFalse(NumUtils.isZero(two));
	}

	@Test
	void testIsZeroDouble() {
		Double x = null;
		Assertions.assertFalse(NumUtils.isZero(x));
		Assertions.assertTrue(NumUtils.isZero(0.0d));
		Assertions.assertFalse(NumUtils.isZero(0.01d));
		Assertions.assertTrue(NumUtils.isZero(Double.MIN_VALUE));

	}

	@Test
	void testIsNullOrZeroBigDecimal() {
		BigDecimal nullval = null;
		BigDecimal two = BigDecimal.ONE;
		BigDecimal zero = BigDecimal.ZERO;
		Assertions.assertTrue(NumUtils.isNullOrZero(nullval));
		Assertions.assertTrue(NumUtils.isNullOrZero(zero));
		Assertions.assertFalse(NumUtils.isNullOrZero(two));
	}

	@Test
	void testIsNullOrZeroDouble() {
		Double x = null;
		Assertions.assertTrue(NumUtils.isNullOrZero(x));
		Assertions.assertTrue(NumUtils.isNullOrZero(0.0d));
		Assertions.assertFalse(NumUtils.isNullOrZero(0.01d));
		Assertions.assertTrue(NumUtils.isNullOrZero(Double.MIN_VALUE));
	}

}
