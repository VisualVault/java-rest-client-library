import java.io.*;
import java.net.*;
import java.util.*;

// Emails Class
public class Emails extends Token{
    // the postEmails method sends an email to users.
    // recipients, ccRecipients, subject, and body are mandatory fields. 
    // you may leave ccRecipients and subject as an empty string as needed.
    // for sending emails to multiple people use commas between the names
    // of the recipients and ccRecipients. 
    public String postEmails(String recipients, String ccRecipients, String subject, String body) throws Exception{
		String baseUrl = getBaseUrl();
		String endpoint = "/emails";
		String request = baseUrl + endpoint;
		URL url = new URL(request);
		String auth = getToken();

		Map<String,Object> params = new LinkedHashMap<>();
        params.put("recipients", recipients);
        params.put("ccRecipients", ccRecipients);
        params.put("subject", subject);
        params.put("body", body);


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