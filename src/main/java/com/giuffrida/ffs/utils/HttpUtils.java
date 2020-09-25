package com.giuffrida.ffs.utils;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtils {

	public static HttpResponse executeGet(String url, Map<String, String> headers)
			throws Exception {
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		headers.forEach((k, v) -> {
			request.addHeader(k, v);
		});
		response = client.execute(request);
		System.out.println("Sending GET request to: " + url + " Response: " + response);
		System.out.println("Response code: " + response.getStatusLine().getStatusCode());
		return response;
	}
}
