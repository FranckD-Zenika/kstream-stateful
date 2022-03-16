package com.zenika.serdes;

import com.zenika.models.HighScores;
import com.zenika.models.Player;
import com.zenika.models.Product;
import com.zenika.models.ScoreEvent;
import com.zenika.models.join.Enriched;
import com.zenika.models.join.Enriched2;
import com.zenika.models.join.ScoreWithPlayer;
import com.zenika.utils.SerdesUtil;
import org.apache.kafka.common.serialization.Serde;

import static org.apache.kafka.common.serialization.Serdes.serdeFrom;

public class CustomSerdes {

    public static Serde<ScoreEvent> scoreEvent() {
        return serdeFrom(SerdesUtil.serializer(), SerdesUtil.deserializer(ScoreEvent.class));
    }

    public static Serde<Player> player() {
        return serdeFrom(SerdesUtil.serializer(), SerdesUtil.deserializer(Player.class));
    }

    public static Serde<Product> product() {
        return serdeFrom(SerdesUtil.serializer(), SerdesUtil.deserializer(Product.class));
    }

    public static Serde<Enriched> enriched() {
        return serdeFrom(SerdesUtil.serializer(), SerdesUtil.deserializer(Enriched.class));
    }

    public static Serde<Enriched2> enriched2() {
        return serdeFrom(SerdesUtil.serializer(), SerdesUtil.deserializer(Enriched2.class));
    }

    public static Serde<HighScores> highScores() {
        return serdeFrom(SerdesUtil.serializer(), SerdesUtil.deserializer(HighScores.class));
    }

    public static Serde<ScoreWithPlayer> scoreWithPlayer() {
        return serdeFrom(SerdesUtil.serializer(), SerdesUtil.deserializer(ScoreWithPlayer.class));
    }

    private CustomSerdes() {}
}
