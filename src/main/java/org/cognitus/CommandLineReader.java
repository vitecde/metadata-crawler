package org.cognitus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Get all command line input parameters of the tool.
 *
 * 
 */
public class CommandLineReader {

private String searchQuery="";

private String apiKey="";
private String downloadFlag="";
private String pathToApiKeyFile="";
private String OPsystem;
private String license="any";

private long numberOfResults=1;


// Database parameters 

private String databaseServer="";
private String databaseName="postgres";
private String dataBaseUser="postgres";
private String dataBasePassword="";
private long port=5432;



public CommandLineReader() {

	
}

public int setInputArguments(String[] arguments) {
	int number = 0;
	for (String env : arguments) {
			if (number == 0) {
				pathToApiKeyFile=env;
				readPropertiesFile(pathToApiKeyFile);
				
			}
			
			if (number == 1) {
				searchQuery=env;
			}
					
			number++;

	}
	
			
	return(number);
}



private String readPropertiesFile(String path)  {
	
	Properties prop = new Properties();
	InputStream input = null;

	try {

		input = new FileInputStream(path);

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		if (prop.getProperty("apiKey") != null) {
			apiKey=prop.getProperty("apiKey");
		}
				
		if (prop.getProperty("download") != null) {
			downloadFlag=prop.getProperty("download");
			}
		
		
		if (prop.getProperty("results") != null) {
			
			long number =1;
			try {
				number=new Long(prop.getProperty("results")).longValue();	
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR \""+prop.getProperty("results")+"\"  must be a number!!!!");
	            }
					
			numberOfResults=number;
		}
		
		if (prop.getProperty("license") != null) {
			license=prop.getProperty("license");
		}
		
		if (prop.getProperty("server") != null) {
			databaseServer=prop.getProperty("server");
		}
		if (prop.getProperty("port") != null) {
		
			long number =port;
			try {
				number=new Long(prop.getProperty("port")).longValue();	
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR \""+prop.getProperty("port")+"\"  must be a number!!!!");
	            }
					
			port=number;
		}
		
		if (prop.getProperty("queuename") != null) {
			databaseName=prop.getProperty("queuename");
		}
		
		if (prop.getProperty("user") != null) {
			dataBaseUser=prop.getProperty("user");
		}
		
		if (prop.getProperty("password") != null) {
			dataBasePassword=prop.getProperty("password");
		}
		
		

	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	return(apiKey);
	
}



public String getDownloadFlag() {
	return downloadFlag;
}

public void setDownloadFlag(String downloadFlag) {
	this.downloadFlag = downloadFlag;
}

public String getSearchQuery() {
	return searchQuery;
}

public void setSearchQuery(String searchQuery) {
	this.searchQuery = searchQuery;
}

public String getApiKey() {
	return apiKey;
}

public void setApiKey(String apiKey) {
	this.apiKey = apiKey;
}

public String getPathToApiKeyFile() {
	return pathToApiKeyFile;
}

public void setPathToApiKeyFile(String pathToApiKeyFile) {
	this.pathToApiKeyFile = pathToApiKeyFile;
}

public long getNumberOfResults() {
	return numberOfResults;
}

public void setNumberOfResults(long numberOfResults) {
	this.numberOfResults = numberOfResults;
}

public String getLicense() {
	return license;
}

public void setLicense(String license) {
	this.license = license;
}

public String getDatabaseServer() {
	return databaseServer;
}

public void setDatabaseServer(String databaseServer) {
	this.databaseServer = databaseServer;
}

public String getDatabaseName() {
	return databaseName;
}

public void setDatabaseName(String databaseName) {
	this.databaseName = databaseName;
}

public String getDataBaseUser() {
	return dataBaseUser;
}

public void setDataBaseUser(String dataBaseUser) {
	this.dataBaseUser = dataBaseUser;
}

public String getDataBasePassword() {
	return dataBasePassword;
}

public void setDataBasePassword(String dataBasePassword) {
	this.dataBasePassword = dataBasePassword;
}

public long getPort() {
	return port;
}

public void setPort(long port) {
	this.port = port;
}


}
