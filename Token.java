import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class Token {    
// Declare your constants as strings here for authoriation to request your access token.
// This token will automatically be requested and hard coded into the methods throughout the application. 
// For the url variable, do not end url with a "/".
// i.e. for using https://demo.visualvault.com let url = "https://demo.visualvault.com".
// clientId and clientSecret can be found in the central admin section on your instance of visual vault under your user name. 
// The first APIKEY is the clientId and the second APIKEY is the clientSecret. 
// userName is your user name and password is your password.
// ALSO FILL OUT VARIABLES IN THE NEXT METHOD BELOW
    public static String getToken() throws Exception {
        String url = "";
        String clientId = "";
        String clientSecret = "";
        String userName = "";
        String password = "";
        URL request = new URL(url + "/oauth/token");

        Map<String,Object> params = new LinkedHashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("username", userName);
        params.put("password", password);
        params.put("grant_type", "password");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }

        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)request.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/JSON");
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String response = "";
        for (int c = in.read(); c != -1; c = in.read())
            response += (char)c;

        List<String> strings = Arrays.asList( response.replaceAll(".*?access_token", "").split(",.*?(access_token|$)"));
        String a = strings.get(0);
        String b = a.replace("\"","");
        String token = b.replace(":","");
        return token;
    }

    // method to get base url for requests.
    // url is going to be the baseUrl for your requests. For the url variable, do not end url with a "/".
    // i.e. for using https://demo.visualvault.com let url = "https://demo.visualvault.com".
    // customerAlias and databaseAlias are the customer and database you would like to connect to.
    public static String getBaseUrl(){
        String url = "";
        String customerAlias = "";
        String databaseAlias = "";
        String baseUrl = url + "/api/v1/" + customerAlias + "/" + databaseAlias;
        return baseUrl;
    }

}