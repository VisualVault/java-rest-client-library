
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import com.visualvault.api.forms.Forms;
import com.visualvault.api.library.*;
import com.visualvault.api.security.ClientCredentials;
import com.visualvault.api.security.Sites;
import com.visualvault.api.security.Token;
import com.visualvault.api.security.Users;
import org.json.JSONArray;
import org.json.JSONObject;


// EXAMPLES CLASS
class Examples {

    // main method
    public static void main(String[] args) throws Exception {

        try {
            //String url, 
            //String clientId, 
            //String clientSecret, 
            //String userName, 
            //String password, 
            //String customerAlias, 
            //String databaseAlias
                                  
            ClientCredentials credentials = new ClientCredentials("https://demo.visualvault.com"
                    , ""
                    , ""
                    , ""
                    , ""
                    , ""
                    , "");
            
            Token token = new Token(credentials);

            // OBJECTS
            Documents docs = new Documents(token); // https://github.com/VisualVault/java-rest-client-library/blob/master/src/main/java/com/visualvault/api/library/Documents.java
            Files files = new Files(token); // https://github.com/VisualVault/java-rest-client-library/blob/master/src/main/java/com/visualvault/api/library/Files.java
            Folders folders = new Folders(token); // https://github.com/VisualVault/java-rest-client-library/blob/master/src/main/java/com/visualvault/api/library/Folders.java
            Sites sites = new Sites(token); // https://github.com/VisualVault/java-rest-client-library/blob/master/src/main/java/com/visualvault/api/security/Sites.java
            Users users = new Users(token); // https://github.com/VisualVault/java-rest-client-library/blob/master/src/main/java/com/visualvault/api/security/Users.java
            Forms forms = new Forms(token); // https://github.com/VisualVault/java-rest-client-library/blob/master/src/main/java/com/visualvault/api/forms/Forms.java
            //CustomQuery customQuery = new CustomQuery(); //

            // Example Code
            // IMPORTANT! The examples below use the classes located at
            // https://github.com/VisualVault/java-rest-client-library
            // Each endpoint's .java file may contain additional functionality not shown in here.

            // IMPORTANT! Each API call returns a data object. Each data object's list of fields
            // documented here:
            // http://developer.visualvault.com/api/v1/RestApi/Data/datatypeslist
            // If a response does not include a data object then no object exists on the VV
            // server and parseResponse will return an empty string.

            // GET CUSTOM QUERY RESULTS USING QUERY NAME
            //		String queryName = "MyQueryName";
            //		String queryFilter = "([Last Name] = 'Smith' and [First Name] = 'Jim' and [DOB] = '1992-08-08') OR SID = '12345'";
            //		String queryResults = customQuery.getCustomQueryResultByName(queryName,queryFilter);

            // GET FORM TEMPLATE BY NAME
            // VisualVault uses "com.visualvault.api.forms.Forms" to build UI and database schema related to business
            // objects
            // To get form data records related to a business object we must first get the
            // object's template id
            String formTemplateName = "Patient Registration Application"; //use a Form Template Name that exists in your instance of VisualVault
            String formTemplateResponse = forms.getFormTemplateByName(formTemplateName);

            // get the form template's latest revision id
            String formTemplateId = parseArrayResponse(formTemplateResponse, "revisionId");

            // use form template id to query for data
            // Query syntax is documented at
            // http://developer.visualvault.com/api/v1/RestApi/Data/datafilters

            String query = "[last Name] eq 'Doe' and [first Name] eq 'John'";

            //enter a list of fields to be returned in the response.  Use field names which exist in the Form Template
            // System field names are always returned.  System field names documented at
            // http://developer.visualvault.com/api/v1/RestApi/Data/datatypeslist
            String fieldList = "last name,first name,gender,weight,height Inches,physical Address,physical City";

            //make http request for matching forms
            String response = forms.getFormData(formTemplateId, query, fieldList);

            // CREATE A FOLDER OR GET A FOLDER (if path does not exist), Gets a Folder (if
            // path does exist)
            // Parameters are (folder path (string), folder description, allowRevisions
            // (boolean true|false))
            String folderResponse = folders.createFolder("javaExampleFolder", "description", true);

            // GET FOLDER ID FROM SERVER RESPOSNE response is a JSON document)
            String folderId = parseArrayResponse(folderResponse, "id");

            // CREATE NEW DOCUMENT
            // Document record must be created first and then a file is 'attached' to the
            // Document using Files.uploadFile - HTTP Post to the Files controller (example below).
            // NOTE: document revision used in this example is "0". Document revision can be
            // any unique string value (unique across all revisions of the Document)
            //
            // Parameters:
            // folderId: folderId variable set above
            // documentName: any unique string value. If value is not unique VV Server will append integer value.
            // documentState: integer, 0 for unreleased, 1 for released (draft, published)
            // description: string, any description you want to use
            // revision: any unique string value (required to be unique for this document
            // only). Typically 0,1,2,3, Etc. If not unique document creation will fail.
            // fileName: name you wish the file to be saved as in VisualVault i.e.
            // "example.txt". Note - this API does not attach a file yet so the fileName can be an empty string.
            // indexFields: If the document has no index fields then pass in "{}" for the
            // index fields parameter.
            // If the document has index fields you must enter them as json serialized key
            // value pairs.
            // i.e. let indexFields = "{\"cool index field\":\"change you\",\"favorite
            // foods\":\"mountain dew\"}".
            String documentResponse = docs.createDocument(folderId, "javaExampleName", 1, "description", "0", "nameMeLater", "{}");

            // GET DOCUMENT ID FROM SERVER RESPONSE (response is a JSON document)
            String documentId = parseArrayResponse(documentResponse, "documentId");

            // GET DOCUMENT NAME FROM SERVER RESPONSE (response is a JSON document)
            String docName = parseArrayResponse(documentResponse, "name");

            // uploadFile uploads a file and attaches it to an existing Document as a new file revision
            // OR replaces a file revision depending upon the CheckInState parameter value.
            //
            // documentId is the GUID/UUID (unique identifier) value for an existing Document.  Use the Documents endPoint to get a documentId using the Document name)
            // revisionNumber is the revision number you wish to assign to the file i.e. current document at revision 1 so I will upload the file at revision 2.
            // checkInDocumentState is either "Released" or "Unreleased" or "Replace" (replace the existing revision's file).
            // if you want to update document index fields values you must provide a json string representing key value pairs.
            // i.e. let indexFields = "{\"Customer Name\":\"ACME\",\"Favorite Drink\":\"mountain dew\"}".
            // if the document has no index fields then pass an empty json document "{}" for the index fields parameter.
            // fileName is the name you wish the file to be saved as i.e. "example.txt".
            // filePath is the path where the file will be uploaded from.
            // mimeType is the mimeType of the file being uploaded.
            // charset is "UTF-8" in most cases.

            String fileName = "upload.txt";
            String filePath = new File(".").getCanonicalPath() + "\\" + fileName;

            files.uploadFile(documentId,"1", "java practice", "Released", "{}", fileName, filePath, "text/csv",
                    "UTF-8");
            
            // GET FOLDER DOCUMENTS
            // To get document(s) from a folder, you can first make a call to the Documents.getFolderDocuments method, specifying the parameters below:
            //
            // Parameters:
            // folderPath: The path of a folder in VisualVault. In this case we are using the javaExampleFolder
            // startDoc: The starting document to obtain
            // endDoc: The ending document to obtain
            // In this case, startDoc and endDoc are set to just get one document from the method.
            String documentGetResponse = docs.getFolderDocuments("javaExampleFolder", 1, 1);
                
            // GET FILE
            // To get a file, we first need to get the ID of the document/file we want to download from VisualVault.
            // In the code above, we have already obtained the record of a document in the javaExampleFolder folder that we can download.
            // All we need to do is get the ID and then pass it along with a path in the local file system to the Files.getFile method to
            // download the file.
            //
            // Parameters:
            // id: ID of the document to download, obtained below from the response of Documents.getFolderDocuments
            // filePath: Path in local file system where the file is to be saved
            String downloadDocumentId = parseArrayResponse(documentGetResponse, "id"); //Get document's ID from the response obtained in the code above.
            
            String downloadFilePath = new File(".").getCanonicalPath() + "\\download.txt"; //Build a string with a local path where the file should be saved.
            
            files.getFile(downloadDocumentId, downloadFilePath); //Make call to download file from VisualVault and save in the specified path.

            // GET FILE BYTE ARRAY
            // Instead of directly saving the file to the file system, you can also get it as a byte array, which can be written
            // to the file system later. To do so, make a call to Files.getFileByteArray, passing in the document's ID.
            //
            // Parameters:
            // id: ID of the document to download, obtained previously from the response of Documents.getFolderDocuments
            
            byte[] downloadedFileByteArray = files.getFileByteArray(downloadDocumentId); //Get file's byte array
            
            String byteArrayFilePath = new File(".").getCanonicalPath() + "\\downloadBytes.txt"; //Path to write byte array to
            
            // Write byte array to file system as a file
            try (FileOutputStream fos = new FileOutputStream(byteArrayFilePath)) {
            	   fos.write(downloadedFileByteArray);
            }
            
            // CHECK IF USER ACCOUNT EXISTS
            // Important! All user accounts belong to a 'Site'. Most use cases can simply
            // use the Site name 'Home' which is a system generated Site.

            String newUserName = "newUser@somecompany.com";

            String userAccountResponse = users.getUsersUsId(newUserName);
            String foundUserName = parseArrayResponse(userAccountResponse, "name");

            if (foundUserName.equals("")) {
                // User does not exist

                // CREATE NEW USER ACCOUNT

                // Get 'Home' site Id (each user belongs to a 'site', default site name is
                // 'Home')
                String sitesResponse = sites.getSites();

                // get Id for the 'Home' site
                // multiple user sites may be returned, specify site "name" must equal "home"
                String siteId = parseArrayResponse(sitesResponse, "name", "home", "id");

                if (!siteId.equals("")) {

                    String lastName = "last";
                    String firstName = "first";
                    String emailAddress = newUserName;
                    String password = "password";

                    // Create new user account using the site Id
                    users.postUsers(siteId, newUserName, firstName, lastName, emailAddress, password);

                    String verifyNewUserName = parseArrayResponse(userAccountResponse, "userid");

                    if (verifyNewUserName.equals(newUserName)) {
                        // user created successfully
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(" Exception =  " + e);
        }

    }

    // method to parse a JSON response that returns an array
    // Returns a specific field value from the first item in the array.
    // pass in the JSON response to parse, and the desired fieldName.
    public static String parseResponse(String response, String fieldName) throws Exception {
        Object obj = new JSONObject(response);
        JSONObject jsonObject = (JSONObject) obj;
        try {
            JSONObject responseObj = (JSONObject)  jsonObject.get("data");
            String fieldValue = responseObj.get(fieldName).toString();
            return fieldValue;
        } catch (Exception e) {
            return "";
        }
    }

    // method to parse a JSON response that returns an array
    // Returns a specific field value from the first item in the array.
    // pass in the JSON response to parse, and the desired fieldName.
    public static String parseArrayResponse(String response, String fieldName) throws Exception {
        Object obj = new JSONObject(response);
        JSONObject jsonObject = (JSONObject) obj;
        try {
            JSONArray data = (JSONArray) jsonObject.get("data");
            Iterator<Object> iterator = data.iterator();
            while (iterator.hasNext()) {
                JSONObject responseObj = (JSONObject) iterator.next();
                String fieldValue = responseObj.get(fieldName).toString();
                return fieldValue;
            }
        } catch (Exception e) {
            JSONObject data2 = (JSONObject) jsonObject.get("data");
            String fieldValue = data2.get(fieldName).toString();
            return fieldValue;
        }
        return "";
    }

    // method to parse a JSON response that returns an array
    // Returns a specific field value from the first item in the array.
    // pass in the JSON response to parse, and the desired fieldName.
    // fieldName2 is a child object's field name
    public static String parseArrayResponse(String response, String fieldName1, String fieldName1Value, String fieldName2)
            throws Exception {
        Object obj = new JSONObject(response);
        JSONObject jsonObject = (JSONObject) obj;
        try {
            JSONArray data = (JSONArray) jsonObject.get("data");
            Iterator<Object> iterator = data.iterator();
            while (iterator.hasNext()) {
                JSONObject responseObj = (JSONObject) iterator.next();
                String fieldValue1 = responseObj.get(fieldName1).toString();
                String fieldValue = "";

                if (fieldValue1.toLowerCase().equals(fieldName1Value.toLowerCase())) {
                    fieldValue = responseObj.get(fieldName2).toString();
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

}
