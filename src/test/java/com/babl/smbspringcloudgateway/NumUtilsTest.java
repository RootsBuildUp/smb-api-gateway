package com.babl.smbspringcloudgateway;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static com.babl.smbspringcloudgateway.NumUtils.*;

class NumUtilsTest {

	@Test
	void testIsZeroBigDecimal() {
		BigDecimal two = BigDecimal.ONE;
		BigDecimal zero = BigDecimal.ZERO;
		assertTrue(isZero(zero));
		assertFalse(isZero(two));
	}

	@Test
	void testIsZeroDouble() {
		Double x = null;
		assertFalse(isZero(x));
		assertTrue(isZero(0.0d));
		assertFalse(isZero(0.01d));
		assertTrue(isZero(Double.MIN_VALUE));

	}

	@Test
	void testIsNullOrZeroBigDecimal() {
		BigDecimal nullval = null;
		BigDecimal two = BigDecimal.ONE;
		BigDecimal zero = BigDecimal.ZERO;
		assertTrue(isNullOrZero(nullval));
		assertTrue(isNullOrZero(zero));
		assertFalse(isNullOrZero(two));
	}

	@Test
	void testIsNullOrZeroDouble() {
		Double x = null;
		assertTrue(isNullOrZero(x));
		assertTrue(isNullOrZero(0.0d));
		assertFalse(isNullOrZero(0.01d));
		assertTrue(isNullOrZero(Double.MIN_VALUE));
	}

}
