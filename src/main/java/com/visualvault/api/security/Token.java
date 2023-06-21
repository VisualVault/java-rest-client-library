package com.visualvault.api.security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;

public class Token {
	// Set Url and API credentials here used to request an access token
	// For the Url variable, do not end Url with a "/".
	// i.e. for using https://demo.visualvault.com let Url =
	// "https://demo.visualvault.com".
	// clientId and clientSecret can be found in the central admin section on your
	// instance of visual vault under your user name.
	// The first APIKEY is the clientId and the second API SECRET is the
	// clientSecret.
	// userName can be set to clientId and password set to clientSecret for a
	// "client_credentials" OAuth grant type (service account)
	// userName and password may be to another user's credentials if your
	// application is prompting user for credentials ("password" OAuth grant type)
	// customerAlias and databaseAlias are the customer and database you would like
	// to connect to

	Calendar tokenExpirationDate;
	String accessToken;
	String refreshToken;

	ClientCredentials Credentials;

	public Token(ClientCredentials clientCredentials){
		Credentials = clientCredentials;
	}

	public String getToken() throws Exception {

		// If the token has not been fetched or is within 30 seconds of expiration need
		// to fetch token
		Calendar nowDate = Calendar.getInstance();
		nowDate.add(Calendar.SECOND, 30);

		if (accessToken!=null && accessToken.length() > 0 && null != tokenExpirationDate) {
			// if we have a non-expired access token then return it
			if (tokenExpirationDate.after(nowDate)) {
				return accessToken;
			}
		}

		if (accessToken!=null && accessToken.length() > 0 && refreshToken!=null && refreshToken.length() > 0) {
			// if we have an expired access token try to fetch a new access token using the
			// refresh token
			if (getTokenUsingRefreshToken()) {
				return accessToken;
			}
		}

		// fetch a new access token and refresh token

		URL request = new URL(Credentials.Url + "/oauth/token");

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("client_id", Credentials.ClientId);
		params.put("client_secret", Credentials.ClientSecret);
		params.put("username", Credentials.UserName);
		params.put("password", Credentials.Password);
		params.put("grant_type", "client_credentials");

		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}

		byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		HttpURLConnection conn = (HttpURLConnection) request.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/JSON");
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String response = "";
		for (int c = in.read(); c != -1; c = in.read())
			response += (char) c;

		Object obj = new JSONObject(response);
		JSONObject jo = (JSONObject) obj;

		accessToken = (String) jo.get("access_token");
		refreshToken = (String) jo.get("refresh_token");
		int expiresIn = (int) jo.get("expires_in");

		tokenExpirationDate = Calendar.getInstance();
		tokenExpirationDate.add(Calendar.SECOND, expiresIn);

		return accessToken;
	}

	// method to get base Url for requests
	public String getBaseUrl() {
		String baseUrl = Credentials.Url + "/api/v1/" + Credentials.CustomerAlias + "/" + Credentials.DatabaseAlias;
		return baseUrl;	}

	public String getUriScheme() throws URISyntaxException {
        URI uri = new URI(Credentials.Url);
		return uri.getScheme();
	}

	public String getUriAuthority() throws URISyntaxException {
		URI uri = new URI(Credentials.Url);
		return uri.getAuthority();
	}

	public String getUriPath() {
		String baseUrl = "/api/v1/" + Credentials.CustomerAlias + "/" + Credentials.DatabaseAlias;
		return baseUrl;
	}

	public Boolean getTokenUsingRefreshToken() throws Exception {

		URL request = new URL(Credentials.Url + "/oauth/token");

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("client_id", Credentials.ClientId);
		params.put("client_secret", Credentials.ClientSecret);
		params.put("grant_type", "refresh_token");
		params.put("refresh_token", refreshToken);

		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}

		byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		HttpURLConnection conn = (HttpURLConnection) request.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/JSON");
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String response = "";
		for (int c = in.read(); c != -1; c = in.read())
			response += (char) c;

		Object obj = new JSONObject(response);
		JSONObject jo = (JSONObject) obj;

		accessToken = (String) jo.get("access_token");
		refreshToken = (String) jo.get("refresh_token");
		int expiresIn = (int) jo.get("expires_in");

		tokenExpirationDate = Calendar.getInstance();
		tokenExpirationDate.add(Calendar.SECOND, expiresIn);

		return accessToken.length() > 0;
	}

}