package com.zenika.serdes;

import com.zenika.models.Player;
import com.zenika.models.Product;
import com.zenika.models.ScoreEvent;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.Consumed;

public class ConsumedRecord {
    public static Consumed<byte[], ScoreEvent> scoreEvent() {
        return Consumed.with(Serdes.ByteArray(), CustomSerdes.scoreEvent());
    }

    public static Consumed<String, Player> player() {
        return Consumed.with(Serdes.String(), CustomSerdes.player());
    }

    public static Consumed<String, Product> product() {
        return Consumed.with(Serdes.String(), CustomSerdes.product());
    }

    private ConsumedRecord() {}
}
