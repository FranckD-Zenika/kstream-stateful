package com.zenika.utils;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.ORDERED;

@SuppressWarnings("unused")
public class StreamUtil {

	public static <T> Stream<T> stream(Iterator<T> iterator) {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, ORDERED), false);
	}


	private StreamUtil() {}
}
