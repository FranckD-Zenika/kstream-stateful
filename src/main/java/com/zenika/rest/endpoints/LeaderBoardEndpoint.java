package com.zenika.rest.endpoints;

import com.zenika.models.join.Enriched;
import com.zenika.rest.services.LeaderBoardService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@ApplicationScoped
@Path("/leaderboard")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class LeaderBoardEndpoint {

	private final LeaderBoardService leaderBoardService;

	@Inject
	LeaderBoardEndpoint(LeaderBoardService leaderBoardService) {
		this.leaderBoardService = leaderBoardService;
	}

	@GET
	public Map<String, List<Enriched>> all() {
		return leaderBoardService.all();
	}

	@DELETE
	@Path("/reset")
	public void reset() throws InterruptedException {
		leaderBoardService.reset();
	}

	@GET
	@Path("/{from}/{to}")
	public Map<String, List<Enriched>> range(@PathParam("from") String from,
	                                         @PathParam("to") String to) {
		return leaderBoardService.range(from, to);
	}

	@GET
	@Path("/count")
	@Produces(TEXT_PLAIN)
	public long count() {
		return leaderBoardService.count();
	}

	@GET
	@Path("/count/local")
	@Produces(TEXT_PLAIN)
	public long localCount() {
		return leaderBoardService.localCount();
	}

	@GET
	@Path("/{name}")
	public Response one(@PathParam("name") String gameId) {
		var one = leaderBoardService.one(gameId);
		if (one.isRedirect())
			return Response.seeOther(one.redirectURI()).build();
		if (one.hasNoResult())
			return Response.status(404).type(TEXT_PLAIN).entity("no game found for id " + gameId).build();
		return Response.ok(one.highScores()).build();
	}

}
