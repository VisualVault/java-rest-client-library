package com.visualvault.api.library;

import com.visualvault.api.security.ClientCredentials;
import com.visualvault.api.security.Token;
import org.apache.commons.io.IOUtils;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

 
public class Files extends Token {

    public Files (ClientCredentials clientCredentials) {
        super(clientCredentials);
    }
    
    // getFile method downloads a file by id
    // also pass in the the file path you wish to download the file to and 
    public void getFile(String id, String filePath) throws Exception{
        String baseUrl = Token.getBaseUrl();
        String endpoint = "/files/";
        String request = baseUrl + endpoint + id;
        URL url = new URL(request);
        String auth = Token.getToken();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/JSON");
        conn.setRequestProperty("Authorization", "Bearer " + auth);
        conn.setDoOutput(true);

        InputStream is = conn.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }   

        buffer.flush();
        byte[] byteArray = buffer.toByteArray();

        File newFile = new File(filePath);
        FileOutputStream f = new FileOutputStream(newFile);

        f.write(byteArray);
        f.close();
        is.close();
    }


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
    // inputStream is the source fileStream
    // mimeType is the mimeType of the file being uploaded.
    // charset is "UTF-8" in most cases.
    public String uploadFile(String documentId, String revision, String changeReason, String checkInDocumentState,
                             String indexFields, String fileName, InputStream inputStream, String mimeType, String charset) throws Exception{

        String baseUrl = Token.getBaseUrl();
        String endpoint = "/files";
        String request = baseUrl + endpoint;
        String auth = Token.getToken();
        String boundary = Long.toHexString(System.currentTimeMillis());
        String CRLF = "\r\n";

        URLConnection connection = new URL(request).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Bearer " + auth);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try {
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);



            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"documentId\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(documentId).append(CRLF).flush();

            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"name\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(fileName).append(CRLF).flush();

            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"revision\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(revision).append(CRLF).flush();

            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"changeReason\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(changeReason).append(CRLF).flush();

            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"checkInDocumentState\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(checkInDocumentState).append(CRLF).flush();

            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"indexFields\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(indexFields).append(CRLF).flush();

            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"fileName\"").append(CRLF);
            writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
            writer.append(CRLF).append(fileName).append(CRLF).flush();

            writer.append("--" + boundary).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"uploadFile\"; filename=\"" + fileName + "\"").append(CRLF);
            writer.append("Content-Type: " + mimeType + "; charset=" + charset).append(CRLF);
            writer.append(CRLF).flush();

            IOUtils.copy(inputStream, output);
            
            output.flush();

            writer.append(CRLF).flush();
            writer.append("--" + boundary + "--").append(CRLF).flush();
        }catch (Exception e) {

        }

        Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String response = "";
        for (int c = in.read(); c != -1; c = in.read())
            response += (char)c;

        System.out.println(response);

        return response;
    }

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
    public String uploadFile(String documentId, String revision, String changeReason, String checkInDocumentState,
                             String indexFields, String fileName, String filePath, String mimeType, String charset) throws Exception{
            
            String baseUrl = Token.getBaseUrl();
            String endpoint = "/files";
            String request = baseUrl + endpoint;
            String auth = Token.getToken();
            File fileUpload = new File(filePath);
            String boundary = Long.toHexString(System.currentTimeMillis()); 
            String CRLF = "\r\n"; 

            URLConnection connection = new URL(request).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + auth);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try {
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"documentId\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(documentId).append(CRLF).flush();

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"name\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(fileName).append(CRLF).flush();

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"revision\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(revision).append(CRLF).flush();

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"changeReason\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(changeReason).append(CRLF).flush();

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"checkInDocumentState\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(checkInDocumentState).append(CRLF).flush();

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"indexFields\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(indexFields).append(CRLF).flush();

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"fileName\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(fileName).append(CRLF).flush();

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"uploadFile\"; filename=\"" + fileName + "\"").append(CRLF);
                writer.append("Content-Type: " + mimeType + "; charset=" + charset).append(CRLF);
                writer.append(CRLF).flush();
                java.nio.file.Files.copy(fileUpload.toPath(), output);
                output.flush();

                writer.append(CRLF).flush();
                writer.append("--" + boundary + "--").append(CRLF).flush();
            }catch (Exception e) {

            }

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String response = "";
            for (int c = in.read(); c != -1; c = in.read())
                response += (char)c;
            
            System.out.println(response);
            
            return response;            
    }

    // returns byte array of file by dhid. 
    // use getFile above to download a file. 
    public byte[] getFileByteArray(String id) throws Exception{
        
        String baseUrl = Token.getBaseUrl();
        String endpoint = "/files/";
        String request = baseUrl + endpoint + id;
        URL url = new URL(request);
        String auth = Token.getToken();

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/JSON");
        conn.setRequestProperty("Authorization", "Bearer " + auth);
        conn.setDoOutput(true);

    
        InputStream is = conn.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }   

        buffer.flush();
        byte[] byteArray = buffer.toByteArray();
        System.out.println(byteArray.length);


        return byteArray;
     
    }


}