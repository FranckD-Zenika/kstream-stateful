package com.zenika.models.join;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.zenika.models.Product;

import java.util.Objects;

@SuppressWarnings("unused")
public class Enriched2 implements Comparable<Enriched2> {
	private final Long playerId;
	private final Long productId;
	private final String playerName;
	private final String gameName;
	private final Double score;

	private Enriched2(ScoreWithPlayer scoreWithPlayer, Product product){
		this(scoreWithPlayer.player().id(),
			product.id(),
			scoreWithPlayer.player().name(),
			product.name(),
			scoreWithPlayer.scoreEvent().score());
	}

	Enriched2(Long playerId, Long productId, String playerName, String gameName, Double score) {
		this.playerId = playerId;
		this.productId = productId;
		this.playerName = playerName;
		this.gameName = gameName;
		this.score = score;
	}

	public static Enriched2 from(ScoreWithPlayer scoreWithPlayer, Product product) {
		return new Enriched2(scoreWithPlayer, product);
	}

	@JsonGetter("playerId")
	public Long playerId() {
		return playerId;
	}
	@JsonGetter("productId")
	public Long productId() {
		return productId;
	}
	@JsonGetter("playerName")
	public String playerName() {
		return playerName;
	}
	@JsonGetter("gameName")
	public String gameName() {
		return gameName;
	}
	@JsonGetter("score")
	public Double score() {
		return score;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Enriched2))
			return false;
		var enriched2 = (Enriched2) obj;
		return Objects.equals(playerId, enriched2.playerId)
			&& Objects.equals(productId, enriched2.productId)
			&& Objects.equals(playerName, enriched2.playerName)
			&& Objects.equals(gameName, enriched2.gameName)
			&& Objects.equals(score, enriched2.score);
	}

	@Override
	public int hashCode() {
		return Objects.hash(playerId, productId, playerName, gameName, score);
	}

	@Override
	public int compareTo(Enriched2 enriched2) {
		return Double.compare(score, enriched2.score);
	}

}
