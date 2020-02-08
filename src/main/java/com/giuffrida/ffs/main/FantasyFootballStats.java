package com.giuffrida.ffs.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.talksoft.dao.PartnerDataInterface;
import com.talksoft.server.partner.PartnerDataInterfaceException;
import com.talksoft.server.partner.RestfulPartnerInterface.NoOpHostnameVerifier;
import com.talksoft.server.partner.intergy.ServiceCenterList;
import com.talksoft.server.partner.intergy.ServiceCenterListGetRequest;
import com.talksoft.server.partner.intergy.ServiceCenterListGetResponse;

public class FantasyFootballStats {

	public static void main(String[] args) {
		Map<String, String> headers = new HashMap<>();
		URL url;

		try {
			url = new URL(
					"https://fantasy.espn.com/[insert base url here]/[league id here]?view=kona_history_standings");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}

		URLConnection conn = url.openConnection();

		// Set the cookie value to send
		conn.setRequestProperty("Cookie", "ccokie1");
		conn.setRequestProperty("Cookie", "ccokie2");
		conn.connect();
		
		InputStream response = conn.getInputStream();
		response.
		
		try {
			executeGet(statsUrl, headers);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	private List<String> getData() throws Exception {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = null;
		CloseableHttpResponse httpResponse = null;
		ServiceCenterListGetResponse serviceCenterListGetResponse = null;
		List<ServiceCenterList> centers = new ArrayList<ServiceCenterList>();

		ServiceCenterListGetRequest serviceCenterListGetRequest = new ServiceCenterListGetRequest();
		serviceCenterListGetRequest.setCredential(credentials);
		serviceCenterListGetRequest.setOnlyAppointmentLocations(true);

		httpClient = this.getHttpClient();
		httpPost = new HttpPost(SERVICE_CENTER_LIST_GET_URI);
		httpPost.addHeader("Content-Type", "application/json");

		// Build json request from appointmentRequest object
		Gson gson = new GsonBuilder().create();
		String jsonRequest = gson.toJson(serviceCenterListGetRequest);
		try {
			httpPost.setEntity(new StringEntity(jsonRequest));

			httpResponse = this.executeWebServiceCall(httpClient, httpPost);

			String responseString = eventLog.getResponseString();

			serviceCenterListGetResponse = gson.fromJson(responseString,
					ServiceCenterListGetResponse.class);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				logger.info("Successfully retrieved service center list of "
						+ serviceCenterListGetResponse.getServiceCenterList().size()
						+ " service center(s).");
				centers = serviceCenterListGetResponse.getServiceCenterList();
			} else {
				eventLog.setEventCode("PDI-Intergy-304");
				throw new PartnerDataInterfaceException(this.throwableEmailSubject,
						"failed getting service center list for practice "
								+ pdi.getDestinationSiteId(),
						eventLog);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			eventLog.setOutcome("UnsupportedEncodingException: " + e.getMessage());
			eventLog.setEventCode("PDI-Intergy-305");
			throw new PartnerDataInterfaceException(this.throwableEmailSubject,
					"failed getting service center list for practice " + pdi.getDestinationSiteId(),
					eventLog);
		} finally {
			try {
				if (httpResponse != null)
					httpResponse.close();
				if (httpPost != null)
					httpPost.releaseConnection();
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				logger.warning("Error encountered while closing httpResponse : " + e.getMessage());
				e.printStackTrace();
			}
		}

		return centers;
	}

	public CloseableHttpClient getHttpClient()
			throws Exception {
		CloseableHttpClient httpClient = null;

		try {
			SSLContext sslContext = SSLContexts.custom()
					.loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
			HostnameVerifier hnv = HttpsURLConnection.getDefaultHostnameVerifier();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
					new String[]{"TLSv1.2"},
					null, hnv);
			RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(300000).build();
			httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config)
					.setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return httpClient;
	}
}
