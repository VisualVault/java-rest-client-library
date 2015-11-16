import java.io.BufferedReader; 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.nio.file.Files;

 
public class Filez extends Token{

    // the postFile method uploads a revision of a file.
    // docId is the documentId you are uploading the file for. 
    // for name use the name returned by the document data type.
    // revisionNumber is the revision number that the file will be once uploaded. 
    // i.e. I have a current document at revision 1 so I will upload the file at revision 2.
    // checkInDocumentState is either "Released" or "Unreleased".
    // if the document has index fields you must enter them as json serialized keyvalue pairs.
    // i.e. let indexFields = "{\"cool index field\":\"change you\",\"favorite foods\":\"mountain dew\"}".
    // if the document has no index fields then pass in "{}" for the index fields parameter.
    // fileName is the name you wish the file to be saved as i.e. "example.txt".
    // filePath is the path where the file lives on your local machine. 
    // mimeType is the mimeType of the file being uploaded. charset is the charset i.e. "UTF-8" in most cases. 
    public String postFile(String documentId, String name, String revision, String changeReason, String checkInDocumentState, 
        String indexFields, String fileName, String filePath, String mimeType, String charset) throws Exception{

            Token token = new Token();
            String baseUrl = token.getBaseUrl();
            String endpoint = "/files";
            String request = baseUrl + endpoint;
            String auth = token.getToken();
            File fileUpload = new File(filePath);
            String boundary = Long.toHexString(System.currentTimeMillis()); 
            String CRLF = "\r\n"; 

            URLConnection connection = new URL(request).openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Bearer " + auth);
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (
                OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
            ) 
                {

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"documentId\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(documentId).append(CRLF).flush();

                writer.append("--" + boundary).append(CRLF);
                writer.append("Content-Disposition: form-data; name=\"name\"").append(CRLF);
                writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                writer.append(CRLF).append(name).append(CRLF).flush();

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
                Files.copy(fileUpload.toPath(), output);
                output.flush();

                writer.append(CRLF).flush();
                writer.append("--" + boundary + "--").append(CRLF).flush();
            }

            Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String response = "";
            for (int c = in.read(); c != -1; c = in.read())
                response += (char)c;

            return response;
            
    }


}