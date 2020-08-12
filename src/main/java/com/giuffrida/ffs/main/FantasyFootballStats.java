package com.giuffrida.ffs.main;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.giuffrida.ffs.utils.HttpUtils;
import com.google.gson.Gson;

public class FantasyFootballStats {

	private String url = "https://fantasy.espn.com/apis/v3/games/ffl/leagueHistory/335710?seasonId?2019?view?mMatchup&espn_s2\\=AEB37Qk67gmkQ8rgUyGZeIIRiyM3vOXojQk5YaG3bLtyFkZsLjb8BcoewA35leBFdAhM9l/+Yd4ItheHr1krEzXrJ76WAw/Q/x7HICPzaAXgqbN7Ex0YF2m/3v0KaBiRyd6Pmr/h5R4gIiEVHcgijxuSPju7arCP1QaAwEhc0QMgRUzQI1ugKl3Kx6W1VN907oP3qvHlWpeyj8aPZDechSaGWf2tjPl/dEoUPW/mdwNdhwOPRr5tzlDUNv3ghRhxZHvLnf3z0hlxXURV0Tkq5fyI&swid\\={FED936E0-0A6F-43DB-87B5-ABE7B0A65799}";

	public String getUrl() {
		return url;
	}

	public static void main(String[] args) {
		FantasyFootballStats stats = new FantasyFootballStats();
		HttpResponse httpResponse;

		httpResponse = stats.getFantasyStats();
		Gson gson = new Gson();
		try {
			URLEncoder.encode(stats.getUrl(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

	public HttpResponse getFantasyStats() {
		Map<String, String> headers = new HashMap<>();
		
		// headers.put("espn_s2", "");
		// headers.put("swid", "");
		return HttpUtils.buildHttpRequest(this.getUrl(), headers);
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
			return FFTeamManager.getManagerFromGuid(guid).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return guid;
	}
}
