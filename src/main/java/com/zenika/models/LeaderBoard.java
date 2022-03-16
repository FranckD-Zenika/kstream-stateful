package com.zenika.models;

import com.zenika.models.join.Enriched;
import org.apache.kafka.streams.KeyValue;

import java.util.List;
import java.util.Optional;

public interface LeaderBoard {

	String gameName();
	List<Enriched> highScores();

	static LeaderBoard from(KeyValue<String, HighScores> storeResult) {
		return new LeaderBoard() {
			@Override
			public String gameName() {
				return storeResult.key;
			}

			@Override
			public List<Enriched> highScores() {
				return Optional.ofNullable(storeResult.value).map(HighScores::toList).orElseGet(List::of);
			}
		};
	}

	static LeaderBoard from(String key, HighScores value) {
		return new LeaderBoard() {
			@Override
			public String gameName() {
				return key;
			}

			@Override
			public List<Enriched> highScores() {
				return Optional.ofNullable(value).map(HighScores::toList).orElseGet(List::of);
			}
		};
	}

}
