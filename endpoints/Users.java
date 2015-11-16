import java.io.*;
import java.net.*;
import java.util.*;

public class Users extends Token{

	// the getUsersUsId method requests a user by userId. userId is the userId of the user.
	public static String getUsersUsId(String userId) throws Exception{
		Token token = new Token();
		String baseUrl = token.getBaseUrl();
		String endpoint = "/users?q=userId%20eq%20%27";
		String request = baseUrl + endpoint + userId + "%27";
		URL url = new URL(request);
		String auth = token.getToken();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/JSON");
        conn.setRequestProperty("Authorization", "Bearer " + auth);
        conn.setDoOutput(true);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String response = "";
        for (int c = in.read(); c != -1; c = in.read())
            response += (char)c;

        return response;		
	}

    // the getUsers method returns all users. 
    public static String getUsers() throws Exception{
        Token token = new Token();
        String baseUrl = token.getBaseUrl();
        String endpoint = "/users";
        String request = baseUrl + endpoint;
        URL url = new URL(request);
        String auth = token.getToken();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/JSON");
        conn.setRequestProperty("Authorization", "Bearer " + auth);
        conn.setDoOutput(true);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String response = "";
        for (int c = in.read(); c != -1; c = in.read())
            response += (char)c;

        return response;        
    }

    // getUsersId method requests a user by id. id is the id of the user. 
    public static String getUsersId(String id) throws Exception{
        Token token = new Token();
        String baseUrl = token.getBaseUrl();
        String endpoint = "/users/";
        String request = baseUrl + endpoint + id;
        URL url = new URL(request);
        String auth = token.getToken();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/JSON");
        conn.setRequestProperty("Authorization", "Bearer " + auth);
        conn.setDoOutput(true);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String response = "";
        for (int c = in.read(); c != -1; c = in.read())
            response += (char)c;

        return response;        
    }

	// the postUsers method creates a user for a site.
    // siteId is the siteId. userId is the userId name you wish to call the user. 
    // firstName, lastName are first name and last name. 
    // emailAddress is the email address, and password is the password
	public static String postUsers(String siteId, String userId, String firstName,
        String lastName, String emailAddress, String password) throws Exception{
    		
            Token token = new Token();
    		String baseUrl = token.getBaseUrl();
    		String endpoint = "/users?siteId=";
    		String request = baseUrl + endpoint + siteId;
    		URL url = new URL(request);
    		String auth = token.getToken();

    		Map<String,Object> params = new LinkedHashMap<>();
            params.put("userId", userId);
            params.put("firstName", firstName);
            params.put("lastName", lastName);
            params.put("emailAddress", emailAddress);
            params.put("password", password);

            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }

            byte[] postDataBytes = postData.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + auth);
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String response = "";
            for (int c = in.read(); c != -1; c = in.read())
                response += (char)c;

            return response;
	}

}