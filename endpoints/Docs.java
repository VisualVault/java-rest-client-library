import java.io.*;
import java.net.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Docs extends Token{
	// the getDocsFolder method will return all documents of a folder and all documents in the subfolders
	// 0f the parent folder. folderName is the name of the parent folder. 
	// if there are spaces in the folder path name use %20 to account for spaces.
	// startDoc and endDoc is the range of docments you would like returned. 
	// startDoc is the number in the list you would like to start from, and
	// endDoc is the last number in the range of the document list you would like returned. 
	// so startDoc of 10 and endDoc of 20 will respond with a list of documents from 10 through 20. 
	public static String getDocsFolder(String folderName, int startDoc, int endDoc) throws Exception{
		Token token = new Token();
		int start = (startDoc - 1);
		int end = (endDoc - start);
		String offset = Integer.toString(start);
		String limit = Integer.toString(end);
		String baseUrl = token.getBaseUrl();
		String endpoint = "/documents?q=folderpath%20like%20%27/";
		String request = baseUrl + endpoint + folderName + "%%27&offset=" + offset + "&limit=" + limit;
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

	// the getDocsName method will return a document by name.
	// if you are in the UI, document Id is equivalent to name.  
	public static String getDocsName(String name) throws Exception{
		Token token = new Token();
		String baseUrl = token.getBaseUrl();
		String endpoint = "/documents?q=name%20eq%20%27";
		String request = baseUrl + endpoint + name + "%27";
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
      	
	// the postDoc method creates a document. fill out variables below.
	// this will create revision 1. use docId from this creation to upload a file.
	// for indexfields use this form, indexFields = "{\"cool index field\":\"change you\",\"favorite foods\":\"mountain dew\"}".
	// for no indexFields let indexFields = "{}".
	// folderId is the id of the folder for the document to be place.
	// documentState is an int. 0 for unreleased, 1 for released, or 2 for pending.
	// name is name of document, description is description of document. 
	// fileName refers to the name you wish to name the file.
	public static String postDoc(String folderId, String name, int documentState,
		String description, String revision, String fileName, String indexFields) throws Exception{
	
			boolean allowNoFile = true;
			int fileLength = 0;
			Token token = new Token();
			String baseUrl = token.getBaseUrl();
			String endpoint = "/documents";
			String request = baseUrl + endpoint;
			URL url = new URL(request);
			String auth = token.getToken();

			Map<String,Object> params = new LinkedHashMap<>();
	        params.put("folderId", folderId);
	        params.put("name", name);
	        params.put("documentState", documentState);
	        params.put("description", description);
	        params.put("revision", revision);
	        params.put("allowNoFile", allowNoFile);
	        params.put("fileLength", fileLength);
	        params.put("fileName", fileName);
	        params.put("indexFields", indexFields);

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
