package com.zenika.models.join;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.zenika.models.Player;
import com.zenika.models.ScoreEvent;
import com.zenika.models.interfaces.ProductIdReferer;

import java.util.Objects;
import java.util.Optional;

@JsonDeserialize(builder = ScoreWithPlayer.Builder.class)
@SuppressWarnings("unused")
public interface ScoreWithPlayer extends ProductIdReferer {

    @JsonGetter("scoreEvent") ScoreEvent scoreEvent();
    @JsonGetter("player") Player player();

    @Override
    default Long pId() {
        return Optional.ofNullable(scoreEvent()).map(ScoreEvent::productId).orElse(null);
    }

    static ScoreWithPlayer from(ScoreEvent scoreEvent, Player player) {
        return new ScoreWithPlayer() {
            @Override
            public ScoreEvent scoreEvent() {
                return scoreEvent;
            }

            @Override
            public Player player() {
                return player;
            }

            @Override
            public int hashCode() {
                return Objects.hash(scoreEvent(), player());
            }

            @Override
            public boolean equals(Object obj) {
                if (obj == this)
                    return true;
                if (!(obj instanceof ScoreWithPlayer))
                    return false;
                var that = (ScoreWithPlayer) obj;
                return Objects.equals(scoreEvent(), that.scoreEvent())
                    && Objects.equals(player(), that.player());
            }

            @Override
            public String toString() {
                return "{\n" +
                    "\"scoreEvent\": " + scoreEvent() + ",\n" +
                    "\"player\": " + player() +
                    "\n}";
            }
        };
    }

    @JsonPOJOBuilder(withPrefix = "")
    class Builder {
        private ScoreEvent scoreEvent;
        private Player player;

        public Builder scoreEvent(ScoreEvent scoreEvent) {
            this.scoreEvent = scoreEvent;
            return this;
        }

        public Builder player(Player player) {
            this.player = player;
            return this;
        }

        public ScoreWithPlayer build() {
            return ScoreWithPlayer.from(scoreEvent, player);
        }

    }

}
