package com.zenika.rest.services.implementations;

import com.zenika.models.LeaderBoard;
import com.zenika.models.OneResult;
import com.zenika.models.join.Enriched;
import com.zenika.rest.clients.LeaderBoardAPI;
import com.zenika.rest.services.LeaderBoardService;
import com.zenika.utils.StreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsMetadata;
import org.apache.kafka.streams.state.HostInfo;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zenika.operations.Stores.leaderBoardStore;
import static org.apache.kafka.streams.KafkaStreams.State.NOT_RUNNING;

@ApplicationScoped
public class DefaultLeaderBoardService implements LeaderBoardService {

	private static final int PORT = ConfigProvider.getConfig().getValue("quarkus.http.port", int.class);
	private static final int SSL_PORT = ConfigProvider.getConfig().getValue("quarkus.http.ssl-port", int.class);
	private static final List<HostInfo> localHosts  = List.of(
		new HostInfo("localhost", PORT),
		new HostInfo("localhost", SSL_PORT),
		new HostInfo(System.getProperty("host"), PORT),
		new HostInfo(System.getProperty("host"), SSL_PORT)
	);

	private final KafkaStreams streams;

	@Inject
	DefaultLeaderBoardService(KafkaStreams streams) {
		this.streams = streams;
	}

	@Override
	public Map<String, List<Enriched>> all() {
		try (var all = leaderBoardStore(streams).all()) {
			return StreamUtil.stream(all)
				.map(LeaderBoard::from)
				.collect(Collectors.toMap(LeaderBoard::gameName, LeaderBoard::highScores));
		}
	}

	@Override
	public Map<String, List<Enriched>> range(String from, String to) {
		try (var range = leaderBoardStore(streams).range(from, to)) {
			return StreamUtil.stream(range)
				.map(s -> LeaderBoard.from(s.key, s.value))
				.collect(Collectors.toMap(LeaderBoard::gameName, LeaderBoard::highScores));
		}
	}

	@Override
	public long count() {
		var metadata = streams.streamsMetadataForStore("leader-board");
		if (metadata == null || metadata.isEmpty())
			return localCount();
		return metadata
			.stream()
			.map(StreamsMetadata::hostInfo)
			.mapToLong(this::fetchCount)
			.sum();
	}

	@Override
	public long localCount() {
		return leaderBoardStore(streams).approximateNumEntries();
	}

	@Override
	public OneResult one(String name) {
		var metadata = streams.queryMetadataForKey("leader-board", name, Serdes.String().serializer());
		if (localHosts.contains(metadata.activeHost()) || metadata.activeHost().port() == -1)
			return OneResult.highScores(leaderBoardStore(streams).get(name));
		return OneResult.seeOther(getHost(metadata.activeHost()));
	}

	public void reset() throws InterruptedException {
		streams.close();
		while (streams.state() != NOT_RUNNING) {
			Thread.sleep(100);
		}
		streams.cleanUp();
	}

	private URI getHost(HostInfo hostInfo) {
		String scheme = hostInfo.port() == SSL_PORT ? "https" : "http";
		return URI.create(scheme + "://" + hostInfo.host() + ":" + hostInfo.port() + "/leaderboard/");
	}

	private long fetchCount(HostInfo hostInfo) {
		if (localHosts.contains(hostInfo))
			return localCount();
		return leaderBoardAPI(hostInfo).localCount();
	}

	private LeaderBoardAPI leaderBoardAPI(HostInfo hostInfo) {
		return RestClientBuilder.newBuilder()
			.baseUri(getHost(hostInfo))
			.build(LeaderBoardAPI.class);
	}

}
