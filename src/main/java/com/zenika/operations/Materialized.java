package com.zenika.operations;

import com.zenika.models.HighScores;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.state.KeyValueStore;

import static com.zenika.serdes.CustomSerdes.highScores;
import static org.apache.kafka.common.serialization.Serdes.String;

public class Materialized {

	public static org.apache.kafka.streams.kstream.Materialized<String, HighScores, KeyValueStore<Bytes, byte[]>> as(String topicName) {
		return org.apache.kafka.streams.kstream.Materialized.<String, HighScores, KeyValueStore<Bytes, byte[]>>as(topicName)
			.withKeySerde(String())
			.withValueSerde(highScores());
	}

	private Materialized() {}
}
