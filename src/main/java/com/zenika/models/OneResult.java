package com.zenika.models;

import com.zenika.models.join.Enriched;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface OneResult {
	List<Enriched> highScores();

	default URI redirectURI() {
		return null;
	}
	default boolean isRedirect() {
		return redirectURI() != null;
	}

	default boolean hasNoResult() {
		return highScores() == null || highScores().isEmpty();
	}

	static OneResult highScores(HighScores highScores) {
		return () -> Optional.ofNullable(highScores).map(HighScores::toList).orElseGet(List::of);
	}

	static OneResult seeOther(URI uri) {
		return new OneResult() {
			@Override
			public List<Enriched> highScores() {
				return List.of();
			}

			@Override
			public URI redirectURI() {
				return uri;
			}
		};
	}
}
