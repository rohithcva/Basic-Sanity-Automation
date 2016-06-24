package BasicMethods;

import java.io.*;
import java.util.Date;

import com.csvreader.CsvWriter;
/**
 * 
 *  @author gboyina
 *	@version 1.0
 *	@since
 */
public class Output 
{
	
	public void writecsv(String i,String t1,String in,boolean callstatus,boolean vgacontentstatus,
			boolean ppcipcontentstatus,boolean callspeedcheck)
	{
		LogRegister.setLogger();
		LogRegister.setPropertyPaths();
		LogRegister.setLogFile();
		LogRegister.logger.info("Entering Output: writecsv()");
		FilePath f=new FilePath();
		String OutputFilePath=f.getfilepath();
		OutputFilePath=OutputFilePath+"Sanity.csv";
		boolean alreadyExists = new File(OutputFilePath).exists();  
		try { 
			// use FileWriter constructor that specifies open for appending  
			CsvWriter csvOutput = new CsvWriter(new FileWriter(OutputFilePath,true),',');  	       
			// if the file didn't already exist then we need to write out the header line  
			if (!alreadyExists) 
			{ 
	             csvOutput.write("Date and Time");
	             csvOutput.write("TestCase Info");
	             csvOutput.write("Testcase Name");
	             csvOutput.write("Call info");
	             csvOutput.write("Call Status");
	             csvOutput.write("PPCIP Content Status");
	             csvOutput.write("VGA Content Status");
	             csvOutput.write("Call Speed");
	             csvOutput.write("Testcase Result");
	             csvOutput.endRecord();  
			}
			Date date = new Date();
			csvOutput.write(date.toString());
	        csvOutput.write(i);
	        csvOutput.write(t1);  
	        csvOutput.write(in);
	        if(callstatus)
	        	csvOutput.write("Call Connected");
	        else
	        	csvOutput.write("Call not Connected");
	        if(ppcipcontentstatus)
	        	csvOutput.write("PPCIP Content Shared"); 
	        else
	        	csvOutput.write("PPCIP Content Failed");
	        if(vgacontentstatus)
	        	csvOutput.write("VGA Content Shared"); 
	        else
	        	csvOutput.write("VGA Content Failed");
	        if(callspeedcheck)
	        	csvOutput.write("Call Speed Correct"); 
	        else
	        	csvOutput.write("Call Speed Wrong");
	        
	        if(callstatus && ppcipcontentstatus && vgacontentstatus && callspeedcheck)
	        {
	        	csvOutput.write("Passed");
	        }
//	        if(callstatus==true && ppcipcontentstatus==true && vgacontentstatus==true && callspeedcheck==true)
	        
	        else
	        	csvOutput.write("Failed");

	        csvOutput.endRecord();
	        csvOutput.close();  
		} 
		catch (IOException e) 
		{  
			LogRegister.logger.error("In Output:writecsv() "+e.toString());
			e.printStackTrace();  
		}
	}
}
