# VisualVault java-rest-client-library
A JAVA Client Library for accessing the VisualVault REST API

REQUIREMENTS:

java 7 or greater.

WELCOME TO THE JAVA VISUALVAULT REST API CLIENT LIBRARY

* This application simplifies HTTPS calls to the VisualVault REST API when using JAVA.

* Clone this repository and modify Token.java with your VisualVault instance credentials

* Example code for interacting with the VisualVault REST API is located in src/examples.java

* Each VisualVault endpoint has a java helper class which constructs the http GET,POST,PUT,DELETE calls

* Not all VisualVault API endpoints are demonstrated in the examples.  See http://developer.visualvault.com/ for additonal endpoint functionality.  

* Please contact support@visualvault.com if you need addtional examples

NOTE:

* If using Eclipse you can import the project after cloning the repository.

* Once you enter your credentials in Token.java, place Token.class and javaVisualVaultRest.jar in your classpath 
to make requests with the library. 

* For using the parseResponse() method on examples.java, download json-simple-1.1.1.jar 
@ https://code.google.com/p/json-simple/downloads/list and place the file in your classpath.

* For more information on any of the endpoints, datatypes, or anything referring to the REST API please refer to the 
HTTP API section @ http://developer.visualvault.com/
