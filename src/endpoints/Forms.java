import java.io.*;
import java.net.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Forms extends Token {

	private String parseResponse(String response, String fieldName) throws Exception {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(response);
		JSONObject jsonObject = (JSONObject) obj;
		try {
			JSONArray data = (JSONArray) jsonObject.get("data");
			Iterator<JSONObject> iterator = data.iterator();
			while (iterator.hasNext()) {
				JSONObject responseObj = (JSONObject) iterator.next();
				String fieldValue = (String) responseObj.get(fieldName);
				return fieldValue;
			}
		} catch (Exception e) {
			JSONObject data2 = (JSONObject) jsonObject.get("data");
			String fieldValue = (String) data2.get(fieldName);
			return fieldValue;
		}
		return "";
	}

	public String getFormTemplateByName(String formTemplateName) throws Exception {
		Token token = new Token();
		String baseUrl = token.getBaseUrl();

		String query = "[name] eq '" + formTemplateName + "'";
		
		String endpoint = "/formtemplates?q=" + URLEncoder.encode(query, "UTF-8");
		String request = baseUrl + endpoint;
		URL url = new URL(request);
		String auth = token.getToken();

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/JSON");
		conn.setRequestProperty("Authorization", "Bearer " + auth);
		conn.setDoOutput(true);

		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String response = "";
		for (int c = in.read(); c != -1; c = in.read())
			response += (char) c;

		System.out.println(response);

		String templateId = parseResponse(response, "id");

		if (templateId.length() > 0) {
			// Get latest revision of the template
			return getFormTemplateById(templateId);
		}else {
			return response;
		}

	}

	public String getFormTemplateById(String formTemplateId) throws Exception {
		Token token = new Token();
		String baseUrl = token.getBaseUrl();
		String endpoint = "/formtemplates/" + formTemplateId;
		String request = baseUrl + endpoint;
		URL url = new URL(request);
		String auth = token.getToken();

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/JSON");
		conn.setRequestProperty("Authorization", "Bearer " + auth);
		conn.setDoOutput(true);

		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String response = "";
		for (int c = in.read(); c != -1; c = in.read())
			response += (char) c;

		System.out.println(response);

		return response;
	}

	public String getFormTemplates(String queryString) throws Exception {
		Token token = new Token();
		String baseUrl = token.getBaseUrl();
		String endpoint = "/formtemplates?q=";
		String request = baseUrl + endpoint;
		URL url = new URL(request);
		String auth = token.getToken();

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/JSON");
		conn.setRequestProperty("Authorization", "Bearer " + auth);
		conn.setDoOutput(true);

		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String response = "";
		for (int c = in.read(); c != -1; c = in.read())
			response += (char) c;

		System.out.println(response);

		return response;
	}

	public String getFormData(String formTemplateId, String queryString, String fieldList) throws Exception {
		Token token = new Token();
		String baseUrl = token.getBaseUrl();
		String endpoint = "/formtemplates/";
		String request = baseUrl + endpoint + formTemplateId + "/forms?q=" + queryString + "&fields=" + fieldList;
		URL url = new URL(URLEncoder.encode(request, "UTF-8"));
		String auth = token.getToken();

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/JSON");
		conn.setRequestProperty("Authorization", "Bearer " + auth);
		conn.setDoOutput(true);

		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String response = "";
		for (int c = in.read(); c != -1; c = in.read())
			response += (char) c;

		System.out.println(response);

		return response;
	}

	// the postFolders method creates a folder.
	// name is name of folder. description is description of folder.
	// allowRevisions is a boolean either true or false.
	public String postFolders(String name, String description, boolean allowRevisions) throws Exception {
		Token token = new Token();
		String baseUrl = token.getBaseUrl();
		String endpoint = "/folders";
		String request = baseUrl + endpoint;
		URL url = new URL(request);
		String auth = token.getToken();

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("name", name);
		params.put("description", description);
		params.put("allowRevisions", allowRevisions);

		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0)
				postData.append('&');
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}

		byte[] postDataBytes = postData.toString().getBytes("UTF-8");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Bearer " + auth);
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);

		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String response = "";
		for (int c = in.read(); c != -1; c = in.read())
			response += (char) c;

		System.out.println(response);

		return response;
	}

}