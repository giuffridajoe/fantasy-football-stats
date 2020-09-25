package com.giuffrida.ffs.main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giuffrida.ffs.utils.HttpUtils;

public class FantasyFootballStats {

	private String url = "https://fantasy.espn.com/apis/v3/games/ffl/leagueHistory/335710?seasonId?2019?view?mMatchup";

	public String getUrl() {
		return url;
	}

	public static void main(String[] args) {
		FantasyFootballStats stats = new FantasyFootballStats();
		HttpResponse context;
		ArrayList<FFTeamManager> managers = new ArrayList<>();

		try {
			context = stats.getFantasyStats();
			String response = IOUtils.toString(context.getEntity().getContent(), "UTF-8");
			System.out.println("Response from GET: " + response);
			JsonNode rootNode = new ObjectMapper().readTree(response);
			if (!rootNode.elements().hasNext()) {
				throw new Exception();
			}

			managers = createTeamManagers(rootNode);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private static ArrayList<FFTeamManager> createTeamManagers(JsonNode rootNode) throws Exception {
		JsonNode members = rootNode.get(0).path("members");
		ArrayList<FFTeamManager> tmList = new ArrayList<>();

		for (JsonNode member : members) {
			JsonNode id = member.path("id");
			if (FFManager.getManagerFromGuid(id.toString()).equals(FFManager.None)) {
				continue;
			}

			tmList.add(new FFTeamManager(id.toString()));
		}
		return tmList;
	}

	public HttpResponse getFantasyStats() throws Exception {
		Map<String, String> headers = new HashMap<>();

		headers.put("espn_s2",
				"AEBbxpZL%2FJkEwzuW59WQniSvoNvszFfuFQuceG3DJe0W5XdlPIY1FI6%2FKW4SLIavucEtZ%2BLmVb9WqHpbXvYWcet0s69Ux%2F6wm5k3FX5qfW4YdFTEt%2FEzRWV5k%2Fo3qJOLuMGLOZmlCj%2BEiaG6Tsjo1a5kg6LJUJGkvXdhwSLE2F4%2BXL3iHLaXM7PNUQEIwDNqwMewHiBwaZ0MeYbWVSrsj6e%2FdtOn9Pyj7JYZzb2Ukc3yOqtQQVj04k1fftB2Xz4kthl2PsM%2BcLTSobUFj%2Bj622bTGMg46fn2PJalFWbFgQQjJg%3D%3D");
		headers.put("swid", "{FED936E0-0A6F-43DB-87B5-ABE7B0A65799}");
		return HttpUtils.executeGet(getUrl(), headers);
	}

	public String getPlaceFinished(String placeFinished) {
		String placeAndOrdinal = "";
		if (placeFinished == "1") {
			placeAndOrdinal = placeFinished + "st place";
		} else if (placeFinished == "2") {
			placeAndOrdinal = placeFinished + "nd place";
		} else if (placeFinished == "3") {
			placeAndOrdinal = placeFinished + "rd place";
		} else {
			placeAndOrdinal = placeFinished + "th place";
		}
		return placeAndOrdinal;
	}

	public boolean playerFinishedInMoney(String placeFinished) {
		return placeFinished.equals("1") || placeFinished.equals("2") || placeFinished.equals("3");
	}

	public boolean playerFinishedInFirst(String placeFinished) {
		return placeFinished.equals("1");
	}

	public boolean playerFinishedInLast(String placeFinished) {
		return placeFinished.equals("10");
	}

	public String getPlayerName(String guid) {
		try {
			return FFManager.getManagerFromGuid(guid).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return guid;
	}
}
