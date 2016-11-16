package org.cognitus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;


public class RunCommand {
	
	static public void run(String command, boolean waitForExitValue) {
		String s = null;
		try {
			 System.out.println("EXECUTING: "+command);
	        Process p = Runtime.getRuntime().exec(command);
	        
	        if(waitForExitValue) {
	        	BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        	BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	    
	        // read the output from the command   
	        	while ((s = stdInput.readLine()) != null) {
	        		 System.out.println("OUTPUT:"+s);
	        	
	        	}
	                
	        // read any errors from the attempted command
	        	while ((s = stdError.readLine()) != null) {
	        		 System.out.println("ERROR: "+s);
	        		
	        	}
	        
	        
	        	int exitValue=p.waitFor();
	        	 System.out.println("exit value:"+exitValue);
	        }
	        
	    } catch (Exception e) {
	    	 System.out.println("exception happened: "+e.getMessage());        
	    }//try		
	}//run
	
	
 static public void runExec(String cc) {
		
	 	System.out.println("EXECUTING: "+cc);
	 
		CommandLine command = new CommandLine("/bin/sh");
	    command.addArguments(new String[] {
	    		"-c ",
	    		cc
	    },false);
	    try {
			new DefaultExecutor().execute(command);
		} catch (ExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 }
	
}//RunCommand
