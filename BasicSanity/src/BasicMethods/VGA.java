package BasicMethods;

import java.io.*;
import java.net.*;

import org.openqa.selenium.*;

import CallRelatedTestCases.GlobalDataStore;
/**
 * 
 *  @author gboyina
 *	@version 1.0
 *	@since
 */
public class VGA {
	public static Socket socket;
	public static Socket socket1;
	
	public boolean ContentClass(String s,WebDriver driver,WebElement element,int Delay)
			throws UnknownHostException, IOException, InterruptedException
	{
		LogRegister.setLogger();
		LogRegister.setPropertyPaths();
		LogRegister.setLogFile();
		LogRegister.logger.info("Entering VGA: ContentClass()");
		socket = new Socket(s, 24);
        // socket.setSoTimeout(10000);
        socket.setKeepAlive(true);
        final BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        final PrintWriter w = new PrintWriter(socket.getOutputStream(), true);
        /* inserting command */
        w.append(GlobalDataStore.lit);
        w.append(GlobalDataStore.vcbutton_play);
        w.flush();
        Thread.sleep(Delay);
        w.append(GlobalDataStore.lit);
        w.append(GlobalDataStore.xstat);
        w.append(GlobalDataStore.lit);
        w.append(GlobalDataStore.vcbutton_off);
        w.flush();
        w.append(GlobalDataStore.lit);
        w.append(GlobalDataStore.Xit);
        String userInput;
        while ((userInput = r.readLine()) != null) 
        {
        	w.println(userInput);
        	String st= r.readLine();
            //System.out.println(st);
           if("error: invalid value! (valid range 2..3)".equals(st))
           {
        	   System.out.println("'vcbutton play' failed");
        	   System.out.println("vcbutton play 2");
        	   socket1 = new Socket(s, 24);
        	   socket1.setKeepAlive(true);
        	   final BufferedReader r1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
        	   final PrintWriter w1 = new PrintWriter(socket1.getOutputStream(), true);
        	   w1.append(GlobalDataStore.lit);
               w1.append("vcbutton play 2\r");
               w1.flush();
               Thread.sleep(5000);
               w1.append(GlobalDataStore.lit);
               w1.append(GlobalDataStore.xstat);
               w1.append(GlobalDataStore.lit);
               w1.append(GlobalDataStore.vcbutton_off);
               w1.flush();
               w1.append(GlobalDataStore.lit);
               w1.append("exit \r");
               while ((userInput = r.readLine()) != null) {
            	   w1.println(userInput);
            	   st= r1.readLine();
            	   //System.out.println(st);
               }
           	}
           if("info:already stopped".equals(st)||"error: ppcip is not currently attached".equals(st)){ 
        	   System.out.println("VGA Content not displayed please check settings");
        	   return false;
           }    
        }        
        //   socket.close();
        r.close();
        w.close();
        LogRegister.logger.info("Exiting VGA: ContentClass()");
		return true;			
	}
	
	public static  boolean   farcontentcheck(String s3) throws UnknownHostException, IOException, InterruptedException
	{
		LogRegister.logger.info("Entering VGA:farcontentcheck()");
		socket = new Socket(s3, 24);
	    final BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    final PrintWriter w = new PrintWriter(socket.getOutputStream(), true);
	    String u;
	    Thread.sleep(2000);
	    w.append(GlobalDataStore.xstat);
	    w.flush();
	    Thread.sleep(2000);
	    w.append(GlobalDataStore.lit);
	    w.append(GlobalDataStore.Xit);
	    while ((u = r.readLine()) != null) 
	    {
	    	w.println(u);
	    	System.out.println(u);
	    	//System.out.println(u.contains("<CONTENTRATE>---</CONTENTRATE>"));
	    	if(u.contains("<CONTENTRATE>1792</CONTENTRATE>") ){
	            return true;	
	    	}   	
	    }    
	    r.close();
	    w.close();
	    LogRegister.logger.info("Exiting VGA: farcontentcheck()");
	    return false;  
	}
}
