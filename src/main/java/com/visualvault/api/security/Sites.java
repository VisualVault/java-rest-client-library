package com.visualvault.api.security;

import java.io.*;
import java.net.*;

import com.visualvault.api.common.BaseApi;

// SITES CLASS
public class Sites extends BaseApi {
	
    public Sites(Token token) {
    	super(token);
    }

        // getSites() returns a list of all sites.
	public String getSites() throws Exception{        
        String baseUrl = token.getBaseUrl();
        String endpoint = "/sites";
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

        System.out.println(response);
        
        return response;        
	}

	// getSitesId() returns a site by siteId.
	public String getSitesId(String id) throws Exception{        
        String baseUrl = token.getBaseUrl();
        String endpoint = "/sites/";
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

        System.out.println(response);
        
        return response;        
	}

}