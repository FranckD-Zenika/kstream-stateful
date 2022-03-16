package com.zenika.rest.services;

import com.zenika.models.OneResult;
import com.zenika.models.join.Enriched;

import java.util.List;
import java.util.Map;

public interface LeaderBoardService {

	Map<String, List<Enriched>> all();
	Map<String, List<Enriched>> range(String from, String to);
	long count();
	long localCount();
	OneResult one(String name);
	void reset() throws InterruptedException;

}
