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
 * @author Jeremy Walker
 */
public class YoutubeCrawler {

  

   public static void main(String[] args) {
    	
    	System.out.println("We start with the app ");
    	
    	CommandLineReader commmands = new CommandLineReader();
    	
    	int result=commmands.setInputArguments(args);
    	if(result==2) {
    		
    		String searchKey=commmands.getSearchQuery();
    		String apiKey=commmands.getApiKey();
    		String download=commmands.getDownloadFlag();
    		String license= commmands.getLicense();
    		Long results=commmands.getNumberOfResults();
    		
	   		System.out.println("Api key used = "+apiKey);
	   		System.out.println("search Key = "+searchKey);
	   		System.out.println("download active = "+download);
	   		System.out.println("Number of results to show = "+results);
	   		System.out.println("License filter = "+license);
	   		
	   		//** Lets make the first search 
	   		YoutubeApiInterface myAPI = new YoutubeApiInterface(apiKey,results,download,license);
	   		
	   		myAPI.startSearch(searchKey);
	   		
	       
	   		
    		
    	} else {
    		System.out.println("Invalid input parameters. Count: "+result);
    	}
    	 
    		
    	
    } 
    
    
  
}