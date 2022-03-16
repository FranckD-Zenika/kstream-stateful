package com.zenika.operations;

import com.zenika.models.HighScores;
import com.zenika.models.join.Enriched;
import org.apache.kafka.streams.kstream.Aggregator;

public class Aggregators {

	public static final Aggregator<String, Enriched, HighScores> highScoreAdder = (s, enriched, highScores) -> highScores.add(enriched);

	private Aggregators() {}
}
