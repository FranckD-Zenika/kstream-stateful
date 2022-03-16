package com.zenika.operations;

import com.zenika.models.ScoreEvent;
import com.zenika.models.interfaces.ProductIdReferer;
import org.apache.kafka.streams.kstream.KeyValueMapper;

import java.util.Optional;

public class KeyValueMappers {
	public static final KeyValueMapper<byte[], ScoreEvent, String> playerId = (key, scoreEvent) -> Optional.ofNullable(scoreEvent)
		.map(ScoreEvent::playerId).orElse(-1L).toString();
	public static final KeyValueMapper<String, ? super ProductIdReferer, String> productId = (s, productIdReferer) -> String.valueOf(productIdReferer.pId());

	private KeyValueMappers() {}
}
