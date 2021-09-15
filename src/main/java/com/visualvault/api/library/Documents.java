package com.visualvault.api.library;

import com.visualvault.api.common.BaseApi;
import com.visualvault.api.security.ClientCredentials;
import com.visualvault.api.security.Token;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

public class Documents extends BaseApi {

	public Documents(Token token) {
		super(token);
	}

	// the deleteDoc method deletes a document by documentId.
	public String deleteDocument(String id) throws Exception{
		String baseUrl = token.getBaseUrl();
		String endpoint = "/documents/";
		String request = baseUrl + endpoint + id;
		URL url = new URL(request);
		String auth = token.getToken();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Content-Type", "application/JSON");
        conn.setRequestProperty("Authorization", "Bearer " + auth);
        conn.setDoOutput(true);

		String response = IOUtils.toString(conn.getInputStream(), "UTF-8");
		response = response.replaceAll("\\r\\n|\\r|\\n", " ");

        return response;
	}
	// the getFolderDocuments method will return all documents of a folder and all documents in the subfolders
	// 0f the parent folder. folderPath is the full path of the parent folder.
	// if there are spaces in the folder path name use %20 to account for spaces.
	// startDoc and endDoc is the range of documents you would like returned.
	// startDoc is the number in the list you would like to start from, and
	// endDoc is the last number in the range of the document list you would like returned. 
	// startDoc of 10 and endDoc of 20 will respond with a list of documents from 10 through 20.
	public String getFolderDocuments(String folderPath, int startDoc, int endDoc) throws Exception{
		int start = (startDoc - 1);
		int end = (endDoc - start);
		String offset = Integer.toString(start);
		String limit = Integer.toString(end);
		String baseUrl = token.getBaseUrl();
		String endpoint = "/documents?q=folderpath%20like%20%27/";
		String request = baseUrl + endpoint + folderPath + "%%27&offset=" + offset + "&limit=" + limit;
		URL url = new URL(request);
		String auth = token.getToken();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/JSON");
        conn.setRequestProperty("Authorization", "Bearer " + auth);
        conn.setDoOutput(true);

		String response = IOUtils.toString(conn.getInputStream(), "UTF-8");
		response = response.replaceAll("\\r\\n|\\r|\\n", " ");

        return response;
	}

	// the getDocsName method will return a document by name.
	// if you are in the UI, document Id is equivalent to name.  
	public String getDocumentByName(String name) throws Exception{
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

		String response = IOUtils.toString(conn.getInputStream(), "UTF-8");
		response = response.replaceAll("\\r\\n|\\r|\\n", " ");

        return response;
	}

	// the getDocId method requests a document by documentId.
	public String getDocumentById(String id) throws Exception{
		String baseUrl = token.getBaseUrl();
		String endpoint = "/documents/";
		String request = baseUrl + endpoint + id;
		URL url = new URL(request);
		String auth = token.getToken();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/JSON");
        conn.setRequestProperty("Authorization", "Bearer " + auth);
        conn.setDoOutput(true);

		String response = IOUtils.toString(conn.getInputStream(), "UTF-8");
		response = response.replaceAll("\\r\\n|\\r|\\n", " ");

		//        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		//        String response = "";
		//        for (int c = in.read(); c != -1; c = in.read())
		//            response += (char)c;

        return response;
	}

	// the getDocId method requests a document by revisionId (aka dhId).
	public String getDocumentRevision(String revisionId) throws Exception{
		String baseUrl = token.getBaseUrl();
		String endpoint = "/Documents/Revisions/";
		String request = baseUrl + endpoint + revisionId;
		URL url = new URL(request);
		String auth = token.getToken();

		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/JSON");
		conn.setRequestProperty("Authorization", "Bearer " + auth);
		conn.setDoOutput(true);

		String response = IOUtils.toString(conn.getInputStream(), "UTF-8");
		response = response.replaceAll("\\r\\n|\\r|\\n", " ");

		//		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		//		String response = "";
		//		for (int c = in.read(); c != -1; c = in.read())
		//			response += (char)c;

		return response;
	}



	// the getDocId method requests a document by document revision id
	public String getDocumentRevisionOcrProperties(String id) throws Exception{
		String baseUrl = token.getBaseUrl();
		String endpoint = "/documents/" + id + "/ocr";
		String request = baseUrl + endpoint;
		URL url = new URL(request);
		String auth = token.getToken();

		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/JSON");
		conn.setRequestProperty("Authorization", "Bearer " + auth);
		conn.setDoOutput(true);

		String response = IOUtils.toString(conn.getInputStream(), "UTF-8");
		response = response.replaceAll("\\r\\n|\\r|\\n", " ");

		//		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		//		String response = "";
		//		for (int c = in.read(); c != -1; c = in.read())
		//			response += (char)c;

		return response;
	}
      	
	// the createDocument method creates a document using http post. fill out variables below.
	// this will create revision 1. use docId from this creation to upload a file.
	// for indexfields use this form, indexFields = "{\"cool index field\":\"change you\",\"favorite foods\":\"mountain dew\"}".
	// for no indexFields let indexFields = "{}".
	// folderId is the id of the folder for the document to be place.
	// documentState is an int. 0 for unreleased, 1 for released, or 2 for pending.
	// name is name of document, description is description of document. 
	// fileName refers to the name you wish to name the file.
	public String createDocument(String folderId, String name, int documentState,
		String description, String revision, String fileName, String indexFields) throws Exception{
	
			boolean allowNoFile = true;
			int fileLength = 0;			
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

			String response = IOUtils.toString(conn.getInputStream(), "UTF-8");
			response = response.replaceAll("\\r\\n|\\r|\\n", " ");

			//	        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			//	        String response = "";
			//	        for (int c = in.read(); c != -1; c = in.read())
			//	            response += (char)c;

	        return response;

	}


	public String updateDocumentOcrStatus(String id,int ocrOutcomeType,int ocrErrorCode ) throws Exception{
		String baseUrl = token.getBaseUrl();
		String endpoint = String.format("/documents/%s/ocr/status",id);

		String request = baseUrl + endpoint;
		URL url = new URL(request);
		String auth = token.getToken();

		JSONObject params = new JSONObject();
		params.put("ocrStatus", ocrOutcomeType);
		params.put("ocrErrorCode", ocrErrorCode);

		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Authorization", "Bearer " + auth);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(params.toString());
		wr.flush();
		wr.close();
		String response = IOUtils.toString(conn.getInputStream(), "UTF-8");
		response = response.replaceAll("\\r\\n|\\r|\\n", " ");
		return response;

	}

}
