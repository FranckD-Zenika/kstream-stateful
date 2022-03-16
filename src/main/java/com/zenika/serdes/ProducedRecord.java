package com.zenika.serdes;

import com.zenika.models.HighScores;
import com.zenika.models.join.Enriched;
import com.zenika.models.join.ScoreWithPlayer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Produced;

public class ProducedRecord {

	public static Produced<String, ScoreWithPlayer> scoreWithPlayer() {
		return Produced.with(Serdes.String(), CustomSerdes.scoreWithPlayer());
	}

	public static Produced<String, Enriched> enriched() {
		return Produced.with(Serdes.String(), CustomSerdes.enriched());
	}

	public static Produced<String, HighScores> highScores() {
		return Produced.with(Serdes.String(), CustomSerdes.highScores());
	}

	private ProducedRecord() {}
}
