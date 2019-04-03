import java.io.*;
import java.net.*;
import java.util.*;

public class Folders extends Token{

	// searches a folder by folderpath. folderPath is the folderPath.
	// if there are spaces in the folder path name use %20 to account for spaces.
	public String getFolders(String folderPath) throws Exception{
		String baseUrl = getBaseUrl();
		String endpoint = "/folders?folderpath=";
		String request = baseUrl + endpoint + folderPath;
		URL url = new URL(request);
		String auth = getToken();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/JSON");
        conn.setRequestProperty("Authorization", "Bearer " + auth);
        conn.setDoOutput(true);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String response = "";
        for (int c = in.read(); c != -1; c = in.read())
            response += (char)c;

        System.out.println(response);
        
        return response;
	}

	// the getFoldersDocs method requests a list of documents in a folder by folderId. 
	public String getFoldersDocs(String folderId) throws Exception{
		String baseUrl = getBaseUrl();
		String endpoint = "/folders/";
		String request = baseUrl + endpoint + folderId + "/documents";
		URL url = new URL(request);
		String auth = getToken();

	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-Type", "application/JSON");
	    conn.setRequestProperty("Authorization", "Bearer " + auth);
	    conn.setDoOutput(true);

	    Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	    String response = "";
	    for (int c = in.read(); c != -1; c = in.read())
	        response += (char)c;

	    System.out.println(response);
	    
	    return response;
	}

	// the postFolders method creates a folder.
	// name is name of folder. description is description of folder.
	// allowRevisions is a boolean either true or false. 
	public String postFolders(String name, String description, boolean allowRevisions) throws Exception{
		String baseUrl = getBaseUrl();
		String endpoint = "/folders";
		String request = baseUrl + endpoint;
		URL url = new URL(request);
		String auth = getToken();

		Map<String,Object> params = new LinkedHashMap<>();
        params.put("name", name);
        params.put("description", description);
        params.put("allowRevisions", allowRevisions);

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

        System.out.println(response);
        
        return response;
	}


}