package org.cognitus;

/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

/**
 * Print a list of videos matching a search term.
 *
 * @author Marco Chavarria
 */
public class YoutubeCrawler {

  

   public static void main(String[] args) {
    	
    	
    	
    	CommandLineReader commmands = new CommandLineReader();
    	int result=commmands.setInputArguments(args);
    	String apiKey=commmands.getApiKey();
    	
    	if(result==2) { // OFFline version to make development !!!
    		
    		String searchKey=commmands.getSearchQuery();
    		
    		String download=commmands.getDownloadFlag();
    		String license= commmands.getLicense();
    		Long results=commmands.getNumberOfResults();
    		System.out.println("We start with the app OFFLINE ");
	   		System.out.println("Api key used = "+apiKey);
	   		System.out.println("search Key = "+searchKey);
	   		System.out.println("download active = "+download);
	   		System.out.println("Number of results to show = "+results);
	   		System.out.println("License filter = "+license);
	   		
	   		//** Lets make the first search 
	   		YoutubeApiInterface myAPI = new YoutubeApiInterface(apiKey,results,download,license);
	   		//myAPI.startSearch(searchKey);
	   		
	   		myAPI.startSearchPaginated(searchKey);
	   		
    		
    	} else {
    		System.out.println("Online activate! .Start receiving the data from RabbitMQ server !!");
    		
    		// Get the parameters of the Rabbit Worker 
    		System.out.println("We start with the app ONLINE ");
	   		String server = commmands.getDatabaseServer();
	   		String name = commmands.getDatabaseName();
	   		String user = commmands.getDataBaseUser();
	   		String password = commmands.getDataBasePassword();
	   		long port = commmands.getPort();
	   		
	   		System.out.println("Queue server  = "+server);
	   		System.out.println("Queue name of queue  = "+name);
	   		System.out.println("Queue user  = "+user);
	   		System.out.println("Queue password = "+password);
	   		System.out.println("Queue port = "+port);
	   		
	
	   		
	   		RabbitWorker myWorker = new RabbitWorker();
	   		myWorker.setRabbitServer(server);
	   		myWorker.setQueueName(name);
	   		myWorker.setRabbitPassword(password);
	   		myWorker.setPort((int) port);
	   		
	   		myWorker.setApiKey(apiKey);
	   		
	   		myWorker.startWorker();
    		
    		
    	}
    	 
    		
    	
    } 
    
    
  
}