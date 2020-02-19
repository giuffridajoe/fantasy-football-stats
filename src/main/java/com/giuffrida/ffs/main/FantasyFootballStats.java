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
		
		headers.put("espn_s2",
				"");
		headers.put("swid", "");
		return HttpUtils.buildHttpRequest(this.getUrl(), headers);
	}


}
