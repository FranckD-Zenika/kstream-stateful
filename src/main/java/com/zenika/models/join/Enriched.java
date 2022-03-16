package com.zenika.models.join;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.zenika.models.Product;
import com.zenika.models.interfaces.ProductIdReferer;

import java.util.Objects;

import static com.zenika.utils.StringUtil.nullAsStringIfEmpty;

@SuppressWarnings("unused")
@JsonDeserialize(builder = Enriched.Builder.class)
public interface Enriched extends ProductIdReferer {
	@JsonGetter("playerId") Long playerId();
	@JsonGetter("productId") Long productId();
	@JsonGetter("playerName") String playerName();
	@JsonGetter("gameName") String gameName();
	@JsonGetter("score") Double score();

	@Override
	default Long pId() {
		return productId();
	}

	static Enriched from(ScoreWithPlayer scoreWithPlayer, Product product) {
		return from(scoreWithPlayer.player().id(),
			product.id(),
			scoreWithPlayer.player().name(),
			product.name(),
			scoreWithPlayer.scoreEvent().score());
	}

	static Enriched from(Long playerId,
	                     Long productId,
	                     String playerName,
	                     String gameName,
	                     Double score) {
		return new Enriched() {
			@Override
			public Long playerId() {
				return playerId;
			}

			@Override
			public Long productId() {
				return productId;
			}

			@Override
			public String playerName() {
				return playerName;
			}

			@Override
			public String gameName() {
				return gameName;
			}

			@Override
			public Double score() {
				return score;
			}

			@Override
			public int hashCode() {
				return Objects.hash(playerId(), productId(), playerName(), gameName(), score());
			}

			@Override
			public Long pId() {
				return productId();
			}
			@Override
			public boolean equals(Object obj) {
				if (obj == this)
					return true;
				if (!(obj instanceof Enriched))
					return false;
				var that = (Enriched) obj;
				return Objects.equals(playerId(), that.playerId())
					&& Objects.equals(productId(), that.productId())
					&& Objects.equals(playerName(), that.playerName())
					&& Objects.equals(gameName(), that.gameName())
					&& Objects.equals(score(), that.score());
			}

			@Override
			public String toString() {
				return "{\n" +
					"\"playerId\": " + playerId() + ",\n" +
					"\"productId\": " + productId() + ",\n" +
					"\"playerName\": " + nullAsStringIfEmpty(playerName()) + ",\n" +
					"\"gameName\": " + nullAsStringIfEmpty(gameName()) + ",\n" +
					"\"score\": " + score() +
					"\n}";
			}

		};
	}

	@JsonPOJOBuilder(withPrefix = "")
	class Builder {
		private Long playerId;
		private Long productId;
		private String playerName;
		private String gameName;
		private Double score;

		public Builder playerId(Long playerId) {
			this.playerId = playerId;
			return this;
		}

		public Builder productId(Long productId) {
			this.productId = productId;
			return this;
		}

		public Builder playerName(String playerName) {
			this.playerName = playerName;
			return this;
		}

		public Builder gameName(String gameName) {
			this.gameName = gameName;
			return this;
		}

		public Builder score(Double score) {
			this.score = score;
			return this;
		}

		public Enriched build() {
			return Enriched.from(playerId, productId, playerName, gameName, score);
		}

	}

}
