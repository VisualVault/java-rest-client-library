import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import static java.lang.Math.toIntExact;

public class Token {
	// Set url and API credentials here used to request an access token
	// For the url variable, do not end url with a "/".
	// i.e. for using https://demo.visualvault.com let url =
	// "https://demo.visualvault.com".
	// clientId and clientSecret can be found in the central admin section on your
	// instance of visual vault under your user name.
	// The first APIKEY is the clientId and the second API SECRETis the
	// clientSecret.
	// userName can be set to clientId and password set to clientSecret for a
	// "client_credentials" OAuth grant type (service account)
	// userName and password may be to another user's credentials if your
	// application is prompting user for credentials ("password" OAuth grant type)
	// customerAlias and databaseAlias are the customer and database you would like
	// to connect to

	static String uriScheme = "https";
	static String uriAuthority = "demo2.visualvault.com";
	static String url = "https://demo2.visualvault.com";
	static String clientId = "";
	static String clientSecret = "";
	static String userName = "";
	static String password = "";
	static String customerAlias = "";
	static String databaseAlias = "";

	static Calendar tokenExpirationDate;
	static String accessToken;
	static String refreshToken;

	public static String getToken() throws Exception {

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

		URL request = new URL(url + "/oauth/token");

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("client_id", clientId);
		params.put("client_secret", clientSecret);
		params.put("username", userName);
		params.put("password", password);
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

		Object obj = new JSONParser().parse(response);
		JSONObject jo = (JSONObject) obj;

		accessToken = (String) jo.get("access_token");
		refreshToken = (String) jo.get("refresh_token");
		Long expiresIn = (Long) jo.get("expires_in");

		tokenExpirationDate = Calendar.getInstance();
		tokenExpirationDate.add(Calendar.SECOND, toIntExact(expiresIn));

		return accessToken;
	}

	// method to get base url for requests
	public static String getBaseUrl() {

		String baseUrl = url + "/api/v1/" + customerAlias + "/" + databaseAlias;
		return baseUrl;
	}

	public static String getUriScheme() {

		return uriScheme;
	}

	public static String getUriAuthority() {

		return uriAuthority;
	}

	public static String getUriPath() {

		String baseUrl = "/api/v1/" + customerAlias + "/" + databaseAlias;
		return baseUrl;
	}

	public static Boolean getTokenUsingRefreshToken() throws Exception {

		URL request = new URL(url + "/oauth/token");

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("client_id", clientId);
		params.put("client_secret", clientSecret);
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

		Object obj = new JSONParser().parse(response);
		JSONObject jo = (JSONObject) obj;

		accessToken = (String) jo.get("access_token");
		refreshToken = (String) jo.get("refresh_token");
		Long expiresIn = (Long) jo.get("expires_in");

		tokenExpirationDate = Calendar.getInstance();
		tokenExpirationDate.add(Calendar.SECOND, toIntExact(expiresIn));

		return accessToken.length() > 0;
	}

}