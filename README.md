A JAVA Client Library for accessing the VisualVault REST API

WELCOME TO THE JAVA VISUALVAULT REST API CLIENT LIBRARY

REQUIREMENTS:

java 7 or greater.

* This application serves as a method for accessing the Visual Vault REST API while using JAVA.

* Start with the Token.java page and declare your constants on that page (your credentials).

* By doing so, a token will be automatically generated for you each time you make a request with a method.

* Each endpoint has a dedicated class assigned to it. i.e. to see the methods for the files enpoint see Filez.java.

* Inside each of the specified classes, there lies the methods to access the specified enpoints.

* Some methods may take parameters, where some may not. All the methods lie in the endpoints folder.

* Refer to the parameters inside the method of the desired class for the correct data type to pass in.

* Read the description on each method to understand how to call the method appropriately.

NOTE:

* Once you fill in your credentials on Token.java, place Token.class and javaVisualVaultRest.jar in your classpath 
to make requests with the library. 

* Refer to the example code section (examples.java) for how to use the library as well. 

* For using the parseResponse() method on examples.java, download json-simple-1.1.1.jar 
@ https://code.google.com/p/json-simple/downloads/list and place the file in your classpath.

* For more information on any of the endpoints, datatypes, or anything referring to the REST API please refer to the 
HTTP API section @ http://developer.visualvault.com/ where each endpoint and there parameters are covered in great detail.
