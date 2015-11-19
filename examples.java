import java.util.regex.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONValue;

// EXAMPLES CLASS
class Examples{
	// method to parse a JSON response returning only a specefic field value.
	// pass in the JSON response to parse, and the desired fieldName.
	// must import json-simple-1.1.1.jar to classpath to use the json simple library. 
	@SuppressWarnings("unchecked") 
	public static String parseResponse(String response, String fieldName) throws Exception{
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
			} 
			catch (Exception e){
			JSONObject data2 = (JSONObject)jsonObject.get("data");
			String fieldValue = (String) data2.get(fieldName);
			return fieldValue;		
		}
		return "";
	}

	// main method	 
	public static void main(String[] args) throws Exception{	    
	    // OBJECTS
	    Docs d = new Docs();
	    Filez f = new Filez();
	    Folders folders = new Folders();
	    Sites sites = new Sites();
	    Users users = new Users();

	    // Example Code
    	
    	// create a folder.
	    String folderResponse = folders.postFolders("javaExampleFolder","el description", true);
	    // request the folderId.
	    String folderId = parseResponse(folderResponse,"id");
	    // create a document while passing in folderId from previous request.
	    // NOTE: document creation at revision 0.
	    String docResponse = d.postDoc(folderId,"javaExampleName",1,"descrip",
	    	"0","nameMeLater","{}");
	    // request documentId
	    String documentId = parseResponse(docResponse,"documentId");
	    // request document name.
		String docName = parseResponse(docResponse,"name");
		// upload a file to the stored document name, documentId, and folderId as revision 1. 
	    System.out.print(f.postFile(documentId,docName,"1","java practice",
	    	"Released","{}","upload.csv","upload.csv","text/csv", "UTF-8"));

            
	}

}

