package BasicMethods;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogRegister 
{
public static Logger logger;
 	
	public static void setLogger()
	{
		logger=Logger.getLogger("LogDemo");
	}
	public static void setPropertyPaths()
	{
		String myPath=System.getProperty("user.home").toString();
		System.setProperty("my.log",myPath+"//Desktop//BasicSanityAutomation//Logs//testlog.log");
		System.setProperty("my.log1",myPath+"//Desktop//BasicSanityAutomation//Logs//testlog1.log");
		System.setProperty("my.log2",myPath+"//Desktop//BasicSanityAutomation//Logs//testlog2.html");
	}
	public static void setLogFile()
	{
		PropertyConfigurator.configure("Log4j.properties");
	}
}
