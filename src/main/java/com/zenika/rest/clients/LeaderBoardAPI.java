package com.zenika.rest.clients;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public interface LeaderBoardAPI {
	@GET
	@Path("/count/local")
	long localCount();
}
