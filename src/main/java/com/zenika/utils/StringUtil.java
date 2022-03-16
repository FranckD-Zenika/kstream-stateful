package com.zenika.utils;

import java.util.Optional;

public class StringUtil {

	public static String nullAsStringIfEmpty(String string) {
		return Optional.ofNullable(string)
			.map(s -> "\"".concat(s).concat("\""))
			.orElse("null");
	}

	private StringUtil() {}
}
