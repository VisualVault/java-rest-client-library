
import java.util.Iterator;

import com.visualvault.api.library.*;
import com.visualvault.api.security.ClientCredentials;
import com.visualvault.api.security.Token;

import org.json.JSONArray;
import org.json.JSONObject;


// EXAMPLES CLASS
class OcrExamples {

    // main method
    public static void main(String[] args) throws Exception {

        ClientCredentials credentials = new ClientCredentials("https://demo2.visualvault.com"
                , "0fd0553c-2a9f-439c-bf11-de7bbf5412d9"
                , "9QY4FKVCIlbUoGRFdNbUnsLkYZuHXFVo3TdmWHYhPd8="
                , "0fd0553c-2a9f-439c-bf11-de7bbf5412d9"
                , "9QY4FKVCIlbUoGRFdNbUnsLkYZuHXFVo3TdmWHYhPd8="
                , "FLDCF"
                , "HR");

        processCompletedOcrDocument(credentials);
    }

    public static void processCompletedOcrDocument(ClientCredentials credentials) {

        try {

        	Token token = new Token(credentials);
            // Initialize API classes
            Documents docs = new Documents(token); // https://github.com/VisualVault/java-rest-client-library/blob/master/endpoints/Docs.java
            Files files = new Files(token); // https://github.com/VisualVault/java-rest-client-library/blob/master/endpoints/Filez.java
            Folders folders = new Folders(token); // https://github.com/VisualVault/java-rest-client-library/blob/master/endpoints/Folders.java

            String dhId = "cfc35f5c-9710-eb11-a9bc-ac23c951c6ed"; //doc revision Id that OCR was completed for

            //get the document revision that OCR was completed for and assign variable values needed for updating the document's OCR status
            String docRevisionResponse = docs.getDocumentRevision(dhId); //dhId is the revision specific Id
            String folderId = parseResponse(docRevisionResponse, "folderId");
            String currentRevision = parseResponse(docRevisionResponse, "revision");
            String docName = parseResponse(docRevisionResponse, "name");  //aka dhDocId
            String documentId = parseResponse(docRevisionResponse, "documentId"); //the revision neutral Document Id aka DlId
            String fileName = parseResponse(docRevisionResponse, "fileName");
            String contentType = parseResponse(docRevisionResponse, "contentType");
            Boolean uploadOcrFile = true;

            //get the document revision's current OCR status from VisualVault
            String ocrResponse = docs.getDocumentRevisionOcrProperties("cfc35f5c-9710-eb11-a9bc-ac23c951c6ed");
            OcrStatus ocrStatus = OcrStatus.fromString(parseResponse(ocrResponse, "ocrStatus"));
            OcrType ocrType = OcrType.fromString(parseResponse(ocrResponse, "ocrType"));
            DocumentCheckInState checkInState = DocumentCheckInState.Released;

            String newRevision = currentRevision;
            if (ocrType == OcrType.OcrCheckInNewRev) {

                try {
                    int i = Integer.parseInt(currentRevision.trim());
                    i = i + 1;
                    newRevision = Integer.toString(i);
                } catch (NumberFormatException nfe) {
                    newRevision = currentRevision + "(OCR)";
                }
            } else {
                if (ocrType == OcrType.OcrCheckInReplace) {
                    checkInState = DocumentCheckInState.Replace;
                } else {
                    uploadOcrFile = false;
                }
            }

            //set the new OCR status value
            OcrOutcomeType ocrOutcomeType = OcrOutcomeType.Success;

            String sourceFilePath = "";

            if (uploadOcrFile) {
                //upload new file with PDF text layer if option to create new revision enabled
                files.uploadFile(documentId,newRevision,"OCR has been completed for this document.",checkInState.toString(),"{}",fileName,sourceFilePath,contentType,"UTF-8");
            }

        } catch (Exception e) {
            System.out.println("OcrExamples Exception =  " + e);

        }
    }

    // method to parse a JSON response that returns an array
    // Returns a specific field value from the first item in the array.
    // pass in the JSON response to parse, and the desired fieldName.
    public static String parseResponse(String response, String fieldName) throws Exception {
        Object obj = new JSONObject(response);
        JSONObject jsonObject = (JSONObject) obj;
        try {
            JSONObject responseObj = (JSONObject) jsonObject.get("data");
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
    public static String parseArrayResponse(String response, String fieldName1, String fieldName1Value, String fieldName2) throws Exception {
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
