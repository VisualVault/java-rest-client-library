# java-rest-client-library
A JAVA Client Library for accessing the VisualVault REST API

REQUIREMENTS:

java 7 or greater.

WELCOME TO THE JAVA VISUALVAULT REST API CLIENT LIBRARY

* This application simplifies HTTPS calls to the VisualVault REST API when using JAVA.

* Start with the Token.java page and declare your constants on that page (your credentials).

* By doing so, a token will be automatically generated for you each time you make a request with a method.

* Each endpoint has a dedicated class assigned to it. i.e. to see the methods for the files enpoint see Filez.java.

* Inside each of the specified classes are helper functions to access the VisualVault REST API end points.

* Refer to the function parameters within each class for guidance on the data type to pass in.

* See examples.java to get started.

NOTE:

* Once you fill in your credentials on Token.java, place Token.class and javaVisualVaultRest.jar in your classpath 
to make requests with the library. 

* For using the parseResponse() method on examples.java, download json-simple-1.1.1.jar 
@ https://code.google.com/p/json-simple/downloads/list and place the file in your classpath.

* For more information on any of the endpoints, datatypes, or anything referring to the REST API please refer to the 
HTTP API section @ http://developer.visualvault.com/
