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
private String pathToApiKeyFile="";
private String OPsystem;


public CommandLineReader() {

	
}

public int setInputArguments(String[] arguments) {
	int conta = 0;
	for (String env : arguments) {
			if (conta == 0) {
				pathToApiKeyFile=env;
				readPropertiesFile(pathToApiKeyFile);
				
			}
			
			if (conta == 1) {
				searchQuery=env;
			}
					
			conta++;

	}
	
			
	return(conta);
}



private String readPropertiesFile(String path)  {
	
	Properties prop = new Properties();
	InputStream input = null;

	try {

		input = new FileInputStream(path);

		// load a properties file
		prop.load(input);

		// get the property value and print it out
		apiKey=prop.getProperty("apiKey");
		

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


}
