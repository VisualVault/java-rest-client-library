package com.visualvault.api.security;

public class ClientCredentials {
    public String Url = "";
    public String ClientId = "";
    public String ClientSecret = "";
    public String UserName = "";
    public String Password = "";
    public String CustomerAlias = "";
    public String DatabaseAlias = "";

    public ClientCredentials(){

    }

    /**+
     *
     * @param url VisualVault Instance URL e.g. https://demo2.visualvault.com
     * @param clientId API Client Id can be found in the VisualVault user properties for authorized accounts
     * @param clientSecret API Client Secret can be found in the VisualVault user properties for authorized accounts
     * @param userName userName you wish to authenticate
     * @param password password for the user account you wish to authenticate
     * @param customerAlias VisualVault Customer Alias
     * @param databaseAlias VisualVault Database Alias
     */
    public ClientCredentials(String url, String clientId, String clientSecret, String userName, String password, String customerAlias, String databaseAlias){
        Url = url;
        ClientId = clientId;
        ClientSecret = clientSecret;
        UserName = userName;
        Password = password;
        CustomerAlias = customerAlias;
        DatabaseAlias = databaseAlias;
    }
}
