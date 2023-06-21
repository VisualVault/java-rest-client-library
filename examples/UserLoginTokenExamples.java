import java.util.Iterator;
import com.visualvault.api.security.ClientCredentials;
import com.visualvault.api.security.Token;
import com.visualvault.api.security.Users;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserLoginTokenExamples {

    public String GetUserLoginToken(String[] args) throws Exception {

        String url = "https://demo.visualvault.com";
        String clientId = "";
        String clientSecret = "";       
        String customerAlias = "";
        String databaseAlias = "";
        String userName = "";      

        String loginToken = "";

        try {

            ClientCredentials credentials = new ClientCredentials(url, clientId, clientSecret, clientId, clientSecret,
                    customerAlias, databaseAlias);

            Token token = new Token(credentials);
            Users users = new Users(token);

            // https://docs.visualvault.com/docs/data-types#user
            String userIdApiResponse = users.getUserId(userName);
            String foundUserId = parseArrayResponse(userIdApiResponse, "id");

            //https://docs.visualvault.com/docs/users-2#get-usersidwebtoken
            String userloginTokenApiResponse = users.getUserLoginToken(foundUserId);
            loginToken = parseArrayResponse(userloginTokenApiResponse, "webToken");            

        } catch (Exception e) {
            System.out.println(" Exception =  " + e);
        }

        return loginToken;
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

}
