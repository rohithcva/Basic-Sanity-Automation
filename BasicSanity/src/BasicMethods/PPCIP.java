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

public class PPCIP 
{
	public static Socket socket;
	public boolean ppcip(String s1,WebDriver driver,WebElement element,boolean ppcipcontentstatus,int Delay) throws Exception
	{
		LogRegister.setLogger();
		LogRegister.setPropertyPaths();
		LogRegister.setLogFile();
		LogRegister.logger.info("Entering PPCIP : ppcip()");
		String s="cd \"C:\\Program Files (x86)\\Polycom\\People+Content IP\" && ppcip -a ";
    	s=s+s1;
    	ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c",s);
    	builder.redirectErrorStream(true);
    	builder.start();
    	if(isRunning())
    		System.out.println("PPCIP Application is running");
    	Thread.sleep(Delay);
    	ppcipcontentstatus=ppcipcheck(s1,driver,element);
    	String processName="PPCIP.exe";
    	getRuntime().exec("taskkill /F /IM " + processName);
    	System.out.println("PPCIP process destroyed");
    	LogRegister.logger.info("Exiting PPCIP: ppcip()");
    	if(ppcipcontentstatus)
    		return true;
    	else//if(ppcipcontentstatus==false)
    	{
    		System.out.println("false ppcip");
    		return false;
    	}
    	 
    	//return ppcipcontentstatus;
	}
	
	private static Runtime getRuntime()
	{
		return Runtime.getRuntime();
	} 
	
	private static boolean isRunning() throws Exception
	{
		Process listTasksProcess = getRuntime().exec("tasklist");
		BufferedReader tasksListReader = new BufferedReader(
				new InputStreamReader(listTasksProcess.getInputStream()));
		String tasksLine;
		while ((tasksLine = tasksListReader.readLine()) != null){
			if (tasksLine.contains("PPCIP.exe"))
				return true;
		}
		return false;
	}
		
	public static boolean ppcipcheck(String s,WebDriver driver,WebElement element) 
			throws UnknownHostException, IOException, InterruptedException
	{
		LogRegister.logger.info("Entering PPCIP: ppcipcheck() ");
		socket = new Socket(s, 24);
        // socket.setSoTimeout(10000);
        socket.setKeepAlive(true);
        final BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        final PrintWriter w = new PrintWriter(socket.getOutputStream(), true);
        /* inserting command */
        
        w.append(GlobalDataStore.lit);
        w.append(GlobalDataStore.vcbutton_play);
        w.flush();
        Thread.sleep(5000);
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
        	if("error: ppcip is not currently attached".equals(st))
        	{            	
        		System.out.println("ppcip Content not displayed please check settings");
		    	System.out.println("false ppcipcheck");
            	return false;
            }
        }
        //   socket.close();
        r.close();
        w.close();
        LogRegister.logger.info("Exiting PPCIP: ppcipcheck()");
		return true;	
	}
}
