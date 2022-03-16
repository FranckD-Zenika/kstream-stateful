package com.zenika.operations;

import com.zenika.models.HighScores;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

public class Stores {

	public static ReadOnlyKeyValueStore<String, HighScores> leaderBoardStore(KafkaStreams streams) {
		return streams.store(StoreQueryParameters.fromNameAndType("leader-board", QueryableStoreTypes.keyValueStore()));
	}

	private Stores() {}
}
