package com.zenika.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zenika.models.join.Enriched;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

@SuppressWarnings("unused")
public class HighScores {

	public static final Comparator<Enriched> ENRICHED_DECREASING_SCORES = (enriched, enriched2) -> Double.compare(enriched2.score(), enriched.score());
	private final TreeSet<Enriched> top3Scores = new TreeSet<>(ENRICHED_DECREASING_SCORES);

	private HighScores() {}

	public HighScores add(Enriched enriched) {
		top3Scores.add(enriched);
		if (top3Scores.size() > 3)
			top3Scores.remove(top3Scores.last());
		return this;
	}

	@JsonCreator
	static HighScores from(@JsonProperty("top3Scores") List<Enriched> top3Scores) {
		var highScore = init();
		highScore.top3Scores.addAll(top3Scores);
		return highScore;
	}

	@JsonGetter("top3Scores")
	public List<Enriched> toList() {
		return List.copyOf(top3Scores);
	}

	public static HighScores init() {
		return new HighScores();
	}

	@Override
	public String toString() {
		return "{" +
			"\"top3Scores\": " + toList() +
			'}';
	}
}
