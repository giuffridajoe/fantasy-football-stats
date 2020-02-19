package com.giuffrida.ffs.main;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.giuffrida.ffs.utils.HttpUtils;

public class FantasyFootballStats {

	private String url = "https://fantasy.espn.com/[insert base url here]/[league id here]?view=kona_history_standings";

	public String getUrl() {
		return url;
	}

	public static void main(String[] args) {
		FantasyFootballStats stats = new FantasyFootballStats();
		HttpResponse httpResponse;

		httpResponse = stats.getFantasyStats();

    }

	public HttpResponse getFantasyStats() {
		Map<String, String> headers = new HashMap<>();
		
		headers.put("espn_s2", "");
		headers.put("swid", "");
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
		String playerName = "";

		if (guid.equals("{CA683308-66E8-4172-9891-D8C2A8A09FE4}")) {
			playerName = "Bob";
		} else if (guid.equals("{797A0B2F-9B4F-4231-9264-5EAB154EEC2C}")) {
			playerName = "Steve";
		} else if (guid.equals("{FED936E0-0A6F-43DB-87B5-ABE7B0A65799}")) {
			playerName = "Me";
		} else if (guid.equals("{66B8630F-0CBE-4B4E-BFD5-192B09C489DE}")) {
			playerName = "Joe Viti";
		} else if (guid.equals("{6B60B179-6FD4-44EA-BEEB-5452F6B1180B}")) {
			playerName = "Shannon";
		} else if (guid.equals("{3963B88C-28F7-45C1-8781-FB276E18AA17}")) {
			playerName = "Joe Vaccaro";
		} else if (guid.equals("{1B97B44B-B739-4670-8518-895EB6B80B36}")) {
			playerName = "Eric";
		} else if (guid.equals("{C6F97B7E-6150-4797-9CB5-F932C1958463}")) {
			playerName = "Sean";
		} else if (guid.equals("{1892C10F-CD14-4EAF-A701-47870982F254}")) {
			playerName = "Ed";
		} else if (guid.equals("{A31A37BD-0B9F-4C8C-B6E2-5878858F9C3D}")) {
			playerName = "Little";
		}
		return playerName;
	}
}
