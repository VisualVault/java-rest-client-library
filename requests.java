// REQUESTS CLASS
class Requests{	 
	// main method   
	public static void main(String[] args) throws Exception{	    
	    // OBJECTS
	    Docs d = new Docs();
	    Emails e = new Emails();
	    Filez f = new Filez();
	    Folders folders = new Folders();
	    Sites sites = new Sites();
	    Users users = new Users();
	    
	    // DOCS

	    // System.out.print(d.deleteDoc("0dd6500c-de8e-e511-bf05-9c4e36b08790"));
	    // System.out.print(d.getDocsFolder("testfolder",4,6));
	    // System.out.print(d.getDocsName("javaDoc"));
	    //System.out.print(d.getDocId("8b9ca507-c288-e511-bf05-9c4e36b08790"));
	    // System.out.print(d.postDoc("EA4833D3-E487-E511-BF05-9C4E36B08790","java99",1,"descrip",
	    // 	"1","nameMeLater","{}"));

	    // EMAILS

	    // System.out.print(e.postEmails("example@gmail.com,example2@gmail.com","","java email subject",
	    // 	"this was sent from java. this is the body."));

	    // FILES

	    // f.getFile("071A75FA-C27D-E511-BF02-008CFA482110","/home/jr/Desktop/test.csv");
	    // System.out.print(f.postFile("09eb4157-15f1-e511-a6a2-e094676f83f7","sendToS3.txt","2","test sending to s3",
	    // 	"Released","{}","sendToS3.txt","/home/jr/Desktop/sendToS3.txt","application/octet-stream", "UTF-8"));
	    // System.out.println(f.getFileByteArray("9326124F-E592-E511-BF07-008CFA482110"));
		
		// FOLDERS

		//ystem.out.print(folders.getFolders("testfolder"));
		// System.out.print(folders.getFoldersDocs("271058a3-9b72-e511-befe-98991b71acc0"));
	    // System.out.print(folders.postFolders("javaFolderDos3","el description", true));

	    // SITES

		// System.out.print(sites.getSites());
		// System.out.print(sites.getSitesId("8be2a9c6-2c83-e511-bf05-9c4e36b08790"));		    

	    // USERS

		// System.out.print(users.getUsersUsId("vault.config"));
		// System.out.print(users.getUsers());
		// System.out.print(users.getUsersId("5eefec33-ca71-e511-befe-98991b71acc0"));
  // 		System.out.print(users.postUsers("4d35d7ea-e383-e511-bf05-9c4e36b08790","javaMan4","J1J",
  // 			"LL1","j5@aol.com","password"));
    }
}