package io.antmedia.periscope;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import io.antmedia.periscope.response.UserResponse;
import io.antmedia.periscope.type.User;

public class UserEndpoints extends BaseEndpoints {

	
	public UserEndpoints(String tokenType, String accessToken) {
		super(tokenType, accessToken);
	}

	/**
	 * Get the current user object.
	 * @return
	 * @throws Exception
	 */
	public UserResponse get() throws Exception {

		String url = ROOT_URL + "/me";

		HttpClient client = HttpClients.custom()
				.setRedirectStrategy(new LaxRedirectStrategy())
				.build();

		HttpUriRequest post = RequestBuilder.get()
				.setUri(url)
				.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.setHeader(HttpHeaders.USER_AGENT, USER_AGENT)
				.setHeader(HttpHeaders.AUTHORIZATION, getTokenType() + " " + getAccessToken())
				.build();

		HttpResponse response = client.execute(post);

		StringBuffer result = readResponse(response);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new Exception(result.toString());
		}
		
		return gson.fromJson(result.toString(), UserResponse.class);
	}
}
