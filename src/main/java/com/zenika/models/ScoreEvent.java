package com.zenika.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Objects;

@SuppressWarnings("unused")
@JsonDeserialize(builder = ScoreEvent.Builder.class)
public interface ScoreEvent {
    @JsonGetter("playerId") Long playerId();
    @JsonGetter("productId") Long productId();
    @JsonGetter("score") Double score();

    static ScoreEvent of(Long playerId, Long productId, Double score) {
        return new ScoreEvent() {
            @Override
            public Long playerId() {
                return playerId;
            }

            @Override
            public Long productId() {
                return productId;
            }

            @Override
            public Double score() {
                return score;
            }

            @Override
            public int hashCode() {
                return Objects.hash(playerId(), productId(), score());
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this)
                    return true;
                if(!(obj instanceof ScoreEvent))
                    return false;
                var that = (ScoreEvent) obj;
                return Objects.equals(playerId(), that.playerId())
                    && Objects.equals(productId(), that.productId())
                    && Objects.equals(score(), that.score());
            }

            @Override
            public String toString() {
                return "{\n" +
                    "\"playerId\": " + playerId() + ",\n" +
                    "\"productId\": " + productId() + ",\n" +
                    "\"score\": " + score() +
                    "\n}";
            }
        };
    }

    @JsonPOJOBuilder(withPrefix = "")
    class Builder {
        private Long playerId;
        private Long productId;
        private Double score;

        public Builder playerId(Long playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder productId(Long productId) {
            this.productId = productId;
            return this;
        }

        public Builder score(Double score) {
            this.score = score;
            return this;
        }

        public ScoreEvent build() {
            return ScoreEvent.of(playerId, productId, score);
        }
    }

}
