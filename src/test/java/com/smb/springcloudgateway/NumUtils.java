package com.smb.springcloudgateway;

import org.apache.commons.lang.math.NumberUtils;

import java.math.BigDecimal;

/**
 * Provides extra functionality to Java Number classes.
 * 
 * @author Rashedul Islam
 * @since 2022-07-24
 */
public class NumUtils extends NumberUtils {

	public static boolean isNull(BigDecimal val) {
		return val == null;
	}

	public static boolean isNull(Double val) {
		return val == null;
	}

	public static boolean isZero(BigDecimal val) {
		return val != null && val.compareTo(BigDecimal.ZERO) == 0;
	}

	public static boolean isZero(Double val) {
		return val != null && Math.abs(val) < 2 * Double.MIN_VALUE;
	}

	/**
	 * Returns whether the provided {@link BigDecimal} value is null or zero.
	 * 
	 * @param val {@link BigDecimal} value
	 * @return <code>true</code> if value is null or zero, <code>false</code> otherwise
	 */
	public static boolean isNullOrZero(BigDecimal val) {
		if (val == null) return true;
		return isZero(val);
	}

	public static boolean isNullOrZero(Double val) {
		if (val == null) return true;
		return isZero(val);
	}

}
