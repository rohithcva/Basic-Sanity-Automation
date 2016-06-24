package BasicMethods;

import java.io.File;
import java.io.IOException;

/**
 * The Filepath class contains method to dynamically get the path of required input files
 * 
 * 	@author gboyina
 *	@version 1.0
 *	@since
 */

public class FilePath 
{	
	 public String getfilepath()
	 {	
		 	LogRegister.setLogger();
			LogRegister.setPropertyPaths();
			LogRegister.setLogFile();
			LogRegister.logger.info("Entering FilePath: getfilepath()");
		  String before = null;
		  try
		  { 	
			  File temp = File.createTempFile("i-am-a-temp-file", ".tmp" ); // Stores a temp file and fethces the file Location
			  String absolutePath = temp.getAbsolutePath();
			  String filePath = absolutePath.
					  substring(0,absolutePath.lastIndexOf(File.separator));
			  String substr = "AppData";
			  String[] parts = filePath.split(substr);
			  before = parts[0];
			  before=before.replace("\\", "\\\\");  				// Trimming the path till User of the Computer
			  before=before+"desktop"+"\\\\"+"BasicSanityAutomation"+"\\\\";  // Appending Desktop folder path 
			  return before;
		  	}
		  	catch(IOException e)
		  {
		  	  LogRegister.logger.error("In FilePath: getfilepath()" + e.toString());	
			  e.printStackTrace();
	    	}
		  LogRegister.logger.info("Exiting FilePath: getfilepath()");
		  return before;	
	    }
}
