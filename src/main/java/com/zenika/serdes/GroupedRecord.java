package com.zenika.serdes;

import com.zenika.models.join.Enriched;
import org.apache.kafka.streams.kstream.Grouped;

import static org.apache.kafka.common.serialization.Serdes.String;

public class GroupedRecord {

	public static Grouped<String, Enriched> enriched() {
		return Grouped.with(String(), CustomSerdes.enriched());
	}

	private GroupedRecord() {}
}
