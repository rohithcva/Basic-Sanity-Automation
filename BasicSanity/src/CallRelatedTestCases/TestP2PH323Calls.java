package CallRelatedTestCases;

import java.io.BufferedReader;
import BasicMethods.*;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestP2PH323Calls 
{
public static void main(String s[]) throws Exception 
{
		
	String sCurrentLine,samplestring;
	String[] results =new String[100];
	int ind=0;
	String InputFilePath=null,FilePath=null;
	FilePath f = new FilePath();
	FilePath=f.getfilepath();
	InputFilePath=FilePath+"testing.txt";
	System.out.println(InputFilePath);
	BufferedReader br = new BufferedReader(new FileReader(InputFilePath));
	while ((sCurrentLine = br.readLine()) != null) 
	{
		samplestring=sCurrentLine;
		results[ind] = samplestring.substring(samplestring.lastIndexOf('-') + 1).trim();
		ind++;
	}
   
	LogRegister.setLogger();
	LogRegister.setPropertyPaths();
	LogRegister.setLogFile();
	LogRegister.logger.info("TestP2PH323Calls : main()");
    String  sut1=results[0];
    String  sut2=results[1];
    String  sut3=results[2];
    
    String  sip1=results[3];
    String  sip2=results[4];
    String  sip3=results[5];
    String  isdn1=results[6];
    String  Calldelay=results[8];
    String  Contentdelay=results[9];
    String  TCdelay=results[10];
    
 
	boolean callstatus = true,ppcipcontentstatus=true,vgacontentstatus=true,callspeedcheck=true,farendcontentcheck=true;
	BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
	int CallDelay= (int) (Float.parseFloat(Calldelay));
	int ContentDelay= (int) (Float.parseFloat(Contentdelay));
	int TCDelay= (int) (Float.parseFloat(TCdelay));
	//System.out.println("C D : "+CallDelay);
	//System.out.println("Cn D : "+ContentDelay);
	//System.out.println("TC D : "+TCDelay);
	String  path=FilePath+"chromedriver.exe";
	System.out.println(path);
	System.setProperty("webdriver.chrome.driver",path);
 		
 	 /*	System.setProperty("webdriver.ie.driver", "C:\\Users\\gboyina\\Downloads\\IEDriverServer_x64_2.48.0\\IEDriverServer.exe");
		WebDriver driver = new InternetExplorerDriver();*/
 	//prerequisite(sut1,sut2,path);
	
    Call c= new Call();PPCIP p =new PPCIP();VGA v=new VGA();Output o=new Output();
    Mute m=new Mute();Info in=new Info();
    
    //System.out.println("Model :"+Mname);
  /*  if(Mname.contains("6000")){
    	int[] cr={56,64,112,128,168,192,224,256,280,320,336,384,392,504,512,560,576,616,
        		640,672,704,728,768,784,832,840,952,960,1008,1024,1064,1088,1120,1152,1176,
        		1216,1232,1280,1288,1400,1408,1456,1472,1512,1536,1568,1600,1624,1664,1680,
        		1728,1792,1856,1920};
    }
    else{
    	int[] cr={56,64,112,128,168,192,224,256,280,320,336,384,392,504,512,560,576,616,
        		640,672,704,728,768,784,832,840,952,960,1008,1024,1064,1088,1120,1152,1176,
        		1216,1232,1280,1288,1400,1408,1456,1472,1512,1536,1568,1600,1624,1664,1680,
        		1728,1792,1856,1920,1960,1984,2016,2048,2304,2560,2816,3072,3328,3584,3840,
        		4096};
    }*/
   /* int[] cr={56,64,112,128,168,192,224,256,280,320,336,384,392,504,512,560,576,616,
    		640,672,704,728,768,784,832,840,952,960,1008,1024,1064,1088,1120,1152,1176,
    		1216,1232,1280,1288,1400,1408,1456,1472,1512,1536,1568,1600,1624,1664,1680,
    		1728,1792,1856,1920,1960,1984,2016,2048,2304,2560,2816,3072,3328,3584,3840,
    		4096,4352,4608,4864, 5120,5376,5632,5888,6144};
    */
    
    
    
    int[] cr={1024};
    int len=cr.length;
    //////////////////////////
        		
    	String testsut1=results[0];
    	  WebDriver driver = new ChromeDriver();
    	 	System.out.println("Opening sut in chrome browser");
    	driver.manage().window().maximize();
        driver.get("https://"+testsut1+"/");       
        //Testcase execution
        WebElement element = driver.findElement(By.id("main_0"));
    	element.click();
    	driver.switchTo().activeElement();
    	driver.switchTo().frame(driver.findElement(By.name("contentFrame")));
        
   for(int i=0;i<len;i++)
   {
	   int c1=cr[i];
	   String str = Integer.toString(c1);
	   String testname1="Basic Sanity: Beta Smoke-Point to Point H.323 call ";
	   String testinfo1="Call type-h323 Call rate-";
	   // testname1 = testname1.concat(str);
	   testinfo1 = testinfo1.concat(str);
	   System.out.println("\n"+testname1);
	   System.out.println("--------------------------------------------------");
	   // 1= SIP   2= H323     3= ISDN
	   System.out.println("Sut1 is "+sut1+" Sut2 is  "+sut3);
	   c.PlaceaCall(sut1,sut3,driver,element,1,c1);
	   Thread.sleep (10000);
	   callstatus=c.hangupcheck(driver,element);
	   if(callstatus)
	   {
		   System.out.println("Call Connected");
		   callspeedcheck=c.CallSpeedCheck(sut1,c1);
		   ppcipcontentstatus=p.ppcip(sut1,driver,element,ppcipcontentstatus,ContentDelay);
		   vgacontentstatus=v.ContentClass(sut1,driver,element,ContentDelay);
		   m.Mute(sut1);
		   Thread.sleep(5000);
	   	   //callstatus=c.hangup(driver,element);
	   	   System.out.println("Call Ended");
	   }
	   else
	   {
		   System.out.println("Call not made.Please Check your Settings");
		   ppcipcontentstatus=false;
		   vgacontentstatus=false;
		   callstatus=false;
		   callspeedcheck=false;
	   }
	   Thread.sleep(5000);
	   System.out.println("Test case execution completed, writing output to file");
	   String Info= "Call Between " + sut1 + " & " + sut2;
	   o.writecsv(Info,testname1,testinfo1,callstatus,vgacontentstatus,ppcipcontentstatus,callspeedcheck);
   }
   LogRegister.logger.info("Exit :TestP2PH323 : main() ");  
   driver.close();
   	System.exit(0);
   } 
}
