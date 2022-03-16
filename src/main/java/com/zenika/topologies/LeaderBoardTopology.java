package com.zenika.topologies;

import com.zenika.models.HighScores;
import com.zenika.models.join.Enriched;
import com.zenika.models.join.ScoreWithPlayer;
import com.zenika.operations.Materialized;
import com.zenika.serdes.CustomSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Grouped;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import static com.zenika.operations.Aggregators.highScoreAdder;
import static com.zenika.operations.Joiners.scoreEventPlayerJoiner;
import static com.zenika.operations.KeyValueMappers.playerId;
import static com.zenika.operations.KeyValueMappers.productId;
import static com.zenika.serdes.ConsumedRecord.player;
import static com.zenika.serdes.ConsumedRecord.product;
import static com.zenika.serdes.ConsumedRecord.scoreEvent;
import static com.zenika.serdes.ProducedRecord.highScores;

@ApplicationScoped
public class LeaderBoardTopology {

    @Produces
    public Topology build() {
        var builder = new StreamsBuilder();

        // define streams and table
        var scoreEvents = builder.stream("score-events", scoreEvent()).selectKey(playerId);
        var players = builder.table("players", player());
        var products = builder.globalTable("products", product());

        // define topology
        scoreEvents.join(players, ScoreWithPlayer::from, scoreEventPlayerJoiner)
            .join(products, productId, Enriched::from)
            .groupBy(productId, Grouped.with(Serdes.String(), CustomSerdes.enriched()))
            .aggregate(HighScores::init, highScoreAdder, Materialized.as("leader-board"))
            .toStream()
            .to("high-scores", highScores());
        return builder.build();
    }

}
