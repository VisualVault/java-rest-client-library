import java.util.regex.*;
import java.io.File;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONValue;

// EXAMPLES CLASS
class Examples {
	// method to parse a JSON response returning only a specific field value.
	// pass in the JSON response to parse, and the desired fieldName.
	// must import json-simple-1.1.1.jar to class path to use the JSON simple
	// library.
	@SuppressWarnings("unchecked")
	public static String parseResponse(String response, String fieldName) throws Exception {
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

	// method to parse a JSON response returning only a specific field value based
	// upon a dependent field value in same object
	// pass in the JSON response to parse, and the desired fieldName.
	// must import json-simple-1.1.1.jar to class path to use the JSON simple
	// library.
	@SuppressWarnings("unchecked")
	public static String parseResponse(String response, String fieldName1, String fieldName1Value, String fieldName2)
			throws Exception {
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(response);
		JSONObject jsonObject = (JSONObject) obj;
		try {
			JSONArray data = (JSONArray) jsonObject.get("data");
			Iterator<JSONObject> iterator = data.iterator();
			while (iterator.hasNext()) {
				JSONObject responseObj = (JSONObject) iterator.next();
				String fieldValue1 = (String) responseObj.get(fieldName1);
				String fieldValue = "";

				if (fieldValue1.toLowerCase().equals(fieldName1Value.toLowerCase())) {
					fieldValue = (String) responseObj.get(fieldName2);
				}

				if (fieldValue.length() > 0) {
					return fieldValue;
				}
			}
		} catch (Exception e) {
			JSONObject data2 = (JSONObject) jsonObject.get("data");
			String fieldValue = (String) data2.get(fieldName1);
			return fieldValue;
		}
		return "";
	}

	// main method
	public static void main(String[] args) throws Exception {
		// OBJECTS
		Docs d = new Docs(); // https://github.com/VisualVault/java-rest-client-library/blob/master/endpoints/Docs.java
		Filez f = new Filez(); // https://github.com/VisualVault/java-rest-client-library/blob/master/endpoints/Filez.java
		Folders folders = new Folders(); // https://github.com/VisualVault/java-rest-client-library/blob/master/endpoints/Folders.java
		Sites sites = new Sites(); // https://github.com/VisualVault/java-rest-client-library/blob/master/endpoints/Sites.java
		Users users = new Users(); // https://github.com/VisualVault/java-rest-client-library/blob/master/endpoints/Users.java
		Forms forms = new Forms(); // https://github.com/VisualVault/java-rest-client-library/blob/master/endpoints/Forms.java
		

		// Example Code
		// IMPORTANT! The examples below use the classes located at
		// https://github.com/VisualVault/java-rest-client-library/blob/master/endpoints
		// Each endpoint's .java file may contain additional functionality not shown in
		// these examples

		// IMPORTANT! The parseResponse helper function used by the examples below may
		// be use to get any field values returned by the VV server.
		// Each API call returns a data object. Each data object's list of fields
		// documented here:
		// http://developer.visualvault.com/api/v1/RestApi/Data/datatypeslist
		// If a response does not include a data object then no object exists on the VV
		// server and parseResponse will return an empty string.
		
		// GET FORM TEMPLATE BY NAME
		// VisualVault uses "Forms" to build UI and database schema related to business objects
		// To get form data records related to a business object we must first get the object's template id
		// Query syntax is documented at http://developer.visualvault.com/api/v1/RestApi/Data/datafilters
		// System field names for each API end point documented at http://developer.visualvault.com/api/v1/RestApi/Data/datatypeslist
		
		String formTemplateName = "Patient";
		String formTemplateResponse = forms.getFormTemplateByName(formTemplateName);
		
		//get the form template's latest revision id
		String formTemplateId = parseResponse(formTemplateResponse, "revisionId");
		
		//use form template id to query for data
		String query = "[last Name] eq 'Doe' and [first Name] eq 'John'";
		String fieldList = "last name,first name,gender,weight,height Inches,physical Address,physical City";
		String response = forms.getFormData(formTemplateId, query, fieldList);

		// CREATE A FOLDER OR GET A FOLDER (if path does not exist), Gets a Folder (if
		// path does exist)
		// Parameters are (folder path (string), folder description, allowRevisions
		// (boolean true|false))
		String folderResponse = folders.postFolders("javaExampleFolder", "description", true);

		// GET FOLDER ID FROM SERVER RESPOSNE response is a JSON document)
		String folderId = parseResponse(folderResponse, "id");

		// CREATE NEW DOCUMENT
		// Document record must be created first and then a file is 'attached' to the
		// Document using HTTP Post to the Files controller (example below).
		// NOTE: document revision used in this example is "0". Document revision can be
		// any unique string value (unique across all revisions of the Document)
		//
		// Parameters:
		// folderId: folderId variable set above
		// documentName: any unique string value. If value is not unique VV Server will
		// append integer value.
		// documentState: integer, 0 for unreleased, 1 for released (draft, published)
		// description: string, any description you want to use
		// revision: any unique string value (required to be unique for this document
		// only). Typically 0,1,2,3, Etc. If not unique document creation will fail.
		// fileName: name you wish the file to be saved as in VisualVault i.e.
		// "example.txt". Not important in this example because we are not attaching a
		// file yet.
		// indexFields: If the document has no index fields then pass in "{}" for the
		// index fields parameter.
		// If the document has index fields you must enter them as json serialized key
		// value pairs.
		// i.e. let indexFields = "{\"cool index field\":\"change you\",\"favorite
		// foods\":\"mountain dew\"}".
		String docResponse = d.postDoc(folderId, "javaExampleName", 1, "description", "0", "nameMeLater", "{}");

		// GET DOCUMENT ID FROM SERVER RESPONSE (response is a JSON document)
		String documentId = parseResponse(docResponse, "documentId");

		// GET DOCUMENT NAME FROM SERVER RESPONSE (response is a JSON document)
		String docName = parseResponse(docResponse, "name");

		// UPLOAD (POST) A FILE AND ATTACH TO DOCUMENT CREATED ABOVE. Requires the
		// documentId and docName variables created above.
		// Parameters:
		// documentId: documentId returned by the document data type response (see
		// above).
		// docName: docName returned by the document data type response (see above).
		// revisionNumber: Revision number that the file will be once uploaded. This can
		// be any unique string value, operation will fail if not unique.
		// checkInDocumentState: "Released" or "Unreleased" (draft or published).
		// indexFields: If the document has no index fields then pass in "{}" for the
		// index fields parameter.
		// If the document has index fields you must enter them as json serialized key
		// value pairs.
		// i.e. let indexFields = "{\"cool index field\":\"change you\",\"favorite
		// foods\":\"mountain dew\"}".
		// If the document has no index fields then pass in "{}" for the index fields
		// parameter.
		// fileName: Name you wish the file to be saved as in VisualVault i.e.
		// "example.txt".
		// filePath: Path where the file lives on your local machine.
		// mimeType: MimeType of the file being uploaded. If unsure use
		// "application/octet-stream" for binary files and "text/csv" for text files
		// or see http://www.iana.org/assignments/media-types/media-types.xhtml
		// charset: Charset i.e. "UTF-8" in most cases.

		String fileName = "upload.txt";
		String filePath = new File(".").getCanonicalPath() + "\\" + fileName;

		f.postFile(documentId, docName, "1", "java practice", "Released", "{}", fileName, filePath, "text/csv",
				"UTF-8");

		// CHECK IF USER ACCOUNT EXISTS
		// Important! All user accounts belong to a 'Site'. Most use cases can simply
		// use the Site name 'Home' which is a system generated Site.

		String newUserName = "newUser@somecompany.com";

		String userAccountResponse = users.getUsersUsId(newUserName);
		String foundUserName = parseResponse(userAccountResponse, "name");

		if (foundUserName.equals("")) {
			// User does not exist

			// CREATE NEW USER ACCOUNT

			// Get 'Home' site Id (each user belongs to a 'site', default site name is
			// 'Home')
			String sitesResponse = sites.getSites();

			// get Id for the 'Home' site
			// multiple user sites may be returned, specify site "name" must equal "home"
			String siteId = parseResponse(sitesResponse, "name", "home", "id");

			if (!siteId.equals("")) {

				String lastName = "last";
				String firstName = "first";
				String emailAddress = newUserName;
				String password = "password";

				// Create new user account using the site Id
				users.postUsers(siteId, newUserName, firstName, lastName, emailAddress,	password);

				String verifyNewUserName = parseResponse(userAccountResponse, "userid");

				if (verifyNewUserName.equals(newUserName)) {
					// user created successfully
				}
			}
		}

	}

}
