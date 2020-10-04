package com.giuffrida.ffs.main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
		HashMap<String, String> members = new HashMap<>();
		ArrayList<String> yearsPlayed = new ArrayList<>();

		try {
			context = stats.getLeagueHistory();
			String leagueHistory = IOUtils.toString(context.getEntity().getContent(), "UTF-8");
			System.out.println("Response from GET: " + leagueHistory);

			JsonNode rootArray = getRootArray(leagueHistory);

			for (JsonNode jsonNode : rootArray) {
				members = getMembers(jsonNode);
				yearsPlayed.add(getYearPlayed(jsonNode));
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private static JsonNode getRootArray(String leagueHistory)
			throws JsonProcessingException, JsonMappingException, Exception {
		JsonFactory jsonFactory = new JsonFactory();
		ObjectMapper objectMapper = new ObjectMapper(jsonFactory);

		JsonNode rootArray = objectMapper.readTree(leagueHistory);

		if (!rootArray.elements().hasNext()) {
			throw new Exception("root array has no elements!");
		}

		if (!rootArray.isArray()) {
			throw new Exception("root array is not an array!");
		}
		return rootArray;
	}

	private static String getYearPlayed(JsonNode nodeFromRoot) {
		return nodeFromRoot.get("seasonId").asText();
	}

	private static HashMap<String, String> getMembers(JsonNode nodeFromRoot) throws Exception {
		JsonNode membersArray = nodeFromRoot.get("members");
		HashMap<String, String> namesAndIds = new HashMap<>();

		for (JsonNode member : membersArray) {
			namesAndIds.put(member.path("id").toString(), member.path("displayName").toString());
		}
		return namesAndIds;
	}

	public HttpResponse getLeagueHistory() throws Exception {
		Map<String, String> headers = new HashMap<>();

		headers.put("espn_s2",
				"AEB2R3iZrFhgkdKBrP71dcb4GY4gkxClY%2BzErrhGgJYgMeFvfwCfVPVhg56p3ys%2BuonSsEm2f0qVV%2FkTJCQxjuqOrbbexiAxjQKsCo5Pclj%2Fsl%2F%2FE5pKurw1DboN2BJ7IEOmCHHvXtq7mrCF9WZTvJudqS5s6QcYPfLmOYif7F3s2VuFGm6uMTASiDsMTM4wk6sH5Rbl6s9McpK2d06tejWxo6Dpp35Fwz28G%2F1Uxw%2FgtFyFFYSiNQ1%2BJq3ITiKCHmDEGbrPceUlwRhLCecfs3M4mm%2FqO9kQtE9r8I39XEsNkw%3D%3D");
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

	public static String retrieveYearPlayed(JsonNode jsonNode) {
		return getYearPlayed(jsonNode);
	}

	public static JsonNode retrieveRootArray(String leagueHistory)
			throws JsonMappingException, JsonProcessingException, Exception {
		return getRootArray(leagueHistory);
	}

	public static HashMap<String, String> retieveMembers(JsonNode nodeFromRoot) throws Exception {
		return getMembers(nodeFromRoot);
	}
}
