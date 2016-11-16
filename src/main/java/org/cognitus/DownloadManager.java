package org.cognitus;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

public class DownloadManager {
	
public DownloadManager() {
	
}


public void start(String url) {
	
	 
	 String OPsyst= System.getProperty("os.name");
		if (OPsyst.contains("Windows") ) {
			RunCommand.run(".\\target\\youtube-dl --no-check-certificate -o ./target/videos/%(title)s_%(id)s.%(ext)s "+url, true); // for windows 
		} else {
			// RunCommand.run("youtube-dl --no-check-certificate -o '/crawler/videos/%(title)s_%(id)s.%(ext)s' "+url, true); // for linux
			
			 //--no-check-certificate
			RunCommand.runExec("youtube-dl --no-check-certificate -o '/crawler/videos/%(title)s_%(id)s.%(ext)s' "+url);

			
		}
	
	
}


public static boolean fileExist(String path)
{


  // test to see if a file exists
  File file = new File(path);
  boolean  exists = file.exists();
  if (file.exists() && file.isFile()) {
    System.out.println(path+"file exists, and it is a file");
    
  } else {
	  System.out.println(path+"NO file exists, and it is a file");
  }
  
  return(exists);
}


}
