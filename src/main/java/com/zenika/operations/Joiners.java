package com.zenika.operations;

import com.zenika.models.Player;
import com.zenika.models.ScoreEvent;
import org.apache.kafka.streams.kstream.Joined;

import static com.zenika.serdes.CustomSerdes.player;
import static com.zenika.serdes.CustomSerdes.scoreEvent;
import static org.apache.kafka.common.serialization.Serdes.String;

public class Joiners {

	public static final Joined<String, ScoreEvent, Player> scoreEventPlayerJoiner = Joined.with(String(), scoreEvent(), player());

	private Joiners() {}
}
