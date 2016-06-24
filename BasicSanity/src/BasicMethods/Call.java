package BasicMethods;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import CallRelatedTestCases.GlobalDataStore;


/**
 * 
 * 
 * The Call Class contains Methods for :
 *  ->Placing a P2P Call (PlaceaCall)
 *  ->Placing a MP Call	 (mpPlaceaCall)	
 * 	->Placing a Mixed MP Call (mixmpPlaceaCall)
 * 	->Hanging Up the P2P Call (hangup)
 * 	->Hanging Up the MP Call  (mphangup)
 * 	->Checking P2P Call Connection Status (hangupcheck)
 * 	->Checking MP Call Connection Status  (mphangupcheck)	
 * 	->Checking Call Speed (CallSpeedCheck)
 * 	
 * 
 * 
 * @author gboyina
 *	@version 1.0
 *	@since
 */

public class Call {
	public static Socket socket;
	
	/**
	 * PlaceaCall Method is used to make a P2P Call
	 * 
	 * @param s1 - IP of Endpoint 1
	 * @param s2 - IP of Endpoint 2
	 * @param driver  - Web Driver
	 * @param element - Web Element
	 * @param ct - Call Type  {1= SIP, 2= H323, 3= ISDN}
	 * @param cs - Call Speed
	 */
	 
	public  void PlaceaCall(String s1,String s2,WebDriver driver,WebElement element,
			int ct,int cs) throws InterruptedException, UnknownHostException, IOException
	{	
		LogRegister.setLogger();
		LogRegister.setPropertyPaths();
		LogRegister.setLogFile();
		LogRegister.logger.info("Entering Call:PlaceaCall()"); 
		System.out.println(s1+"----------"+s2);
		Thread.sleep(4000);	
		if(ct==1)
		{
			Select dropdown = new Select(driver.findElement(By.id("macconnectiontype")));
			dropdown.selectByValue("sip");
			System.out.println("SIP Call Selected");
		}
		if(ct==2)
		{
			Select dropdown = new Select(driver.findElement(By.id("macconnectiontype")));
			dropdown.selectByValue("h323");
			System.out.println("H323 Call Selected...");
		}
		if(ct==3){
			Select dropdown = new Select(driver.findElement(By.id("macconnectiontype")));
			dropdown.selectByValue("isdn");
			System.out.println("ISDN Call Selected...");
		}

		Select callspeed = new Select(driver.findElement(By.id("speeds")));
		String str = Integer.toString(cs);
		try{
			callspeed.selectByValue(str);
			System.out.println("Call rate is "+str);
		}
		catch(Exception e)
		{
			LogRegister.logger.error("In Call:PlaceaCall()"+e.toString());
			callspeed.selectByValue("Auto");
			System.out.println(str+"Call rate is not available, Call rate is set to Auto");
		}
		Thread.sleep(4000);
		element = driver.findElement(By.cssSelector("#dataTableBody > tr > td:nth-child(2) > table > tbody >"
				+ " tr:nth-child(1) > td > table > tbody > tr:nth-child(2) > td:nth-child(1) > input"));
		element.clear();
		element.clear();
		element.sendKeys(s2.toString());
		element= driver.findElement(By.id("diallink"));
		element.click();
		System.out.println("Dialing to Endpoint");
		Thread.sleep(5000);
		LogRegister.logger.info("Exiting Call: PlaceaCall()");
		}
	
	/* hangupcheck method is used to check P2P Call Connection Status	 */
	
	public static boolean hangupcheck(WebDriver driver,WebElement element)
	{
		LogRegister.logger.info("Entering Call:hangupcheck()");
		try
		{
			element= driver.findElement(By.id("hanguplink"));
			//element.click();
			//System.out.println("+++++++++++ Call Disconnected +++++++++");
		}			
		catch(Exception e)
		{
			LogRegister.logger.info("In Call:hangupcheck() "+e.toString());
			System.out.println("Call not made.Please Check your Settings");
			return false;
		}
		LogRegister.logger.info("Exiting Call:hangupcheck()");
		return true;
	}	
	
	/* hangup method is used to hangup a P2P Call */
	
	public boolean hangup(WebDriver driver,WebElement element)
	{
		LogRegister.logger.info("Entering Call: hangup()");
		try
		{
			element= driver.findElement(By.id("hanguplink"));
			element.click();
			System.out.println("++++++++++++hangup clicked++++++++++");
			LogRegister.logger.info(" Call Disconnected: hangup()");
	    }			
		catch(Exception e)
		{
			LogRegister.logger.error("In Call:hangup()"+e.toString());
			System.out.println("Call not made or Call Disconnected ");
			return false;
		}
		LogRegister.logger.info("Exiting Call:hangup()");
		return true;
	}
    /**
     * mpPlaceaCall method is used to make MP clls of same all type
     * 
     * @param s1 - IP of Endpoint1
     * @param s2 - IP of Endpoint2
     * @param s3 - IP of Endpoint3
     * @param driver  - Web Driver
     * @param element - Web Element
     * @param ct - Call Type  {1= SIP, 2= H323,  3= ISDN}
     * 
     */
	public static void mpPlaceaCall(String s1,String s2,String s3,WebDriver driver,
		WebElement element,int ct) throws InterruptedException
	{
		LogRegister.logger.info("Entering Call:mpPlaceCall()");
		Thread.sleep(4000);	
		if(ct==1)
		{
			Select dropdown = new Select(driver.findElement(By.id("macconnectiontype")));
			dropdown.selectByValue("sip");
			System.out.println("SIP Call Selected...");
		}
		if(ct==2)
		{
			Select dropdown = new Select(driver.findElement(By.id("macconnectiontype")));
			System.out.println("H323 call Selected...");
			dropdown.selectByValue("h323");
		}
		if(ct==3)
		{
			Select dropdown = new Select(driver.findElement(By.id("macconnectiontype")));
			dropdown.selectByValue("isdn");
			System.out.println("ISDN Call Selected...");
		}
		Thread.sleep(4000);
		element = driver.findElement(By.cssSelector("#dataTableBody > tr > td:nth-child(2) > table > tbody > tr:nth-child(1) >"
				+ " td > table > tbody > tr:nth-child(2) > td:nth-child(1) > input"));
		element.clear();
		element.sendKeys(s2);
		element= driver.findElement(By.id("diallink"));
		element.click();
		System.out.println("Making first call");
		Thread.sleep(20000);
		boolean callc=hangupcheck(driver,element);
		if(callc)
		{
			System.out.println("First call Succesfull,Making second call ");
			element = driver.findElement(By.cssSelector("#dataTableBody > tr > td:nth-child(2) > table > tbody > tr:nth-child(1) >"
					+ " td > table > tbody > tr:nth-child(2) > td:nth-child(1) > input"));
			element.clear();
			element.sendKeys(s3);
			element= driver.findElement(By.id("diallink"));
			element.click();
		}
		else
		{
			System.out.println("First call not made please check settings");			
		}
		LogRegister.logger.info("Exiting Call:mpPlaceaCall()");
	}

    /**
     * mixmpPlaceaCall method is used to make a mp call of different call types
     * 
     * @param s1 - IP of Endpoint1
     * @param s2 - IP of Endpoint2
     * @param s3 - IP of Endpoint3
     * @param driver  - Web Driver
     * @param element - Web Element
     * @param ct - Call Type  {1= SIP & ISDN, 2=SIP & H323, 3=H323 & ISDN}
     * 
     */
	public static void mixmpPlaceaCall(String s1,String s2,String s3,WebDriver driver,
			WebElement element,int ct) throws InterruptedException
	{	
		LogRegister.logger.info("Entering Call:mixmpPlaceaCall()");
		System.out.println(s1+" "+s2+" "+s3);
		Thread.sleep(4000);	
		if(ct==1 || ct==2)
		{
			Select dropdown = new Select(driver.findElement(By.id("macconnectiontype")));
			dropdown.selectByValue("sip");
			System.out.println("SIP Call Selected...");
		}
		if(ct==3)
		{
			Select dropdown = new Select(driver.findElement(By.id("macconnectiontype")));
			System.out.println("H323 call Selected...");
			dropdown.selectByValue("h323");
		}
		
		Thread.sleep(4000);
		element = driver.findElement(By.cssSelector("#dataTableBody > tr > td:nth-child(2) > table > tbody > tr:nth-child(1) >"
				+ " td > table > tbody > tr:nth-child(2) > td:nth-child(1) > input"));
		element.clear();
		element.sendKeys(s2);
		element= driver.findElement(By.id("diallink"));
		element.click();
		System.out.println("Making first call");
		Thread.sleep(20000);
		boolean mcallc=hangupcheck(driver,element);
		if(mcallc)
		{
			System.out.println("First call Succesfull,Making second call ");
			Thread.sleep(20000);
			element = driver.findElement(By.cssSelector("#dataTableBody > tr > td:nth-child(2) > table > tbody > tr:nth-child(1) >"
					+ " td > table > tbody > tr:nth-child(2) > td:nth-child(1) > input"));
			element.clear();
			element.sendKeys(s3);
			if(ct==3 || ct==1)
			{
				Select dropdown = new Select(driver.findElement(By.id("macconnectiontype")));
				dropdown.selectByValue("isdn");
				System.out.println("ISDN Call Selected...");
			}
			if(ct==2)
			{
				Select dropdown = new Select(driver.findElement(By.id("macconnectiontype")));
				System.out.println("H323 call Selected...");
				dropdown.selectByValue("h323");
			}
			element= driver.findElement(By.id("diallink"));
			element.click();
		}
		else
		{
			System.out.println("First call not made please check settings");
		}				
		LogRegister.logger.info("Exiting Call:mixMpPlaceaCall()");
	}

	/* mphangupcheck method is used to check MP Call Connection Status	 */
	
	public boolean mphangupcheck(WebDriver driver,WebElement element) throws InterruptedException, Exception
	{
		LogRegister.logger.info("Entering Call:mphangupcheck()");
		try
		{
 			element= driver.findElement(By.id("hanguplink"));
			element.click();
			System.out.println("+++++++++++"+driver);
		}
		catch(Exception e)
		{
			LogRegister.logger.error("In Call:mphangupCheck() "+e.toString());
			System.out.println("Call not made.Please Check your Settings");
		}
		Thread.sleep(2000);
		//element= driver.findElement(By.id("Submit3"));
		LogRegister.logger.info("Exiting Call:mphangupcheck()");
		return true;
	}
	
	/* mphangup method is used to hangup MP Call */
	
	public void mphangup(WebDriver driver,WebElement element) throws InterruptedException
	{
		LogRegister.logger.info("Entering Call:mphangup()");
		try
		{
			element= driver.findElement(By.id("Submit3"));
			element.click();
		}
		catch(Exception e)
		{
			LogRegister.logger.error("In Call:mphangup() "+e.toString());
			System.out.println("MP call not made.Please Check your Settings"); 
		}
		LogRegister.logger.info("Exiting :Call:mphangup()");
	}

	/* CallSpeedCheck method is used to check Callrate */
	
	public static boolean CallSpeedCheck(String s,int i) throws UnknownHostException, IOException, InterruptedException
	{
		
		LogRegister.logger.info("Entering Call:CallSpeedCheck()");
		socket = new Socket(s,24);
		// socket.setSoTimeout(10000);
		socket.setKeepAlive(true);
		final BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		final PrintWriter w = new PrintWriter(socket.getOutputStream(), true);
		/*CharSequence lit="\r";
		CharSequence xstat="xmlnetstats \r";
		CharSequence Xit="exit \r";
		/* inserting command */
		Thread.sleep(3000);
		System.out.println(GlobalDataStore.xstat);
		w.append(GlobalDataStore.lit);
		w.append(GlobalDataStore.xstat);
        Thread.sleep(2000);
        w.append(GlobalDataStore.lit);
        w.append(GlobalDataStore.Xit);
        String userInput;
        String s3="";
        while ((userInput = r.readLine()) != null) 
        {
        	w.println(userInput);
        	//String st= r.readLine();
        	String st=userInput;
        	if(st.contains("<NETWORK>"))
        		s3=st;    
        }
        //  System.out.println(s3);
        String s4="<CALLSPEED>";
        //K</CALLSPEED>
        s4=s4+Integer.toString(i)+" K</CALLSPEED>";
        System.out.println("s4:  "+s4);
        boolean c1;
        c1=s3.contains(s4);
        // System.out.println("c1: "+c1);
        r.close();
        w.close();
        LogRegister.logger.info("Exiting Call : CallSpeedupCheck()");
        return c1;
        
	}

}
