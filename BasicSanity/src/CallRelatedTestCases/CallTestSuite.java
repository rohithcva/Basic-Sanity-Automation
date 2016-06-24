package CallRelatedTestCases;

import java.io.*;

import BasicMethods.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 
 * @author gboyina
 *
 */

class CallTestSuite {
	public static void main(String s[]) throws Exception {
		String sCurrentLine, samplestring;
		String[] results = new String[100];
		String[] sut = new String[10];
		String[] sip = new String[10];
		LogRegister.setLogger();
		LogRegister.setPropertyPaths();
		LogRegister.setLogFile();
		LogRegister.logger.info("CallTestSuite : main()");
		int ind = 0;
		String InputFilePath = null, FilePath = null;
		FilePath f = new FilePath();
		FilePath = f.getfilepath();
		InputFilePath = FilePath + "testing.txt";
		System.out.println(InputFilePath);
		BufferedReader br = new BufferedReader(new FileReader(InputFilePath));
		while ((sCurrentLine = br.readLine()) != null) {
			samplestring = sCurrentLine;
			results[ind] = samplestring.substring(samplestring.lastIndexOf('-') + 1).trim();
			ind++;
		}
		/*
		 * System.arraycopy(results, 0, sut,0,3);
		 * 
		 * /*for(int count1=0;count1<=8;count1++){ sut[count1]= results[count1];
		 * }
		 */
		/*
		 * int sipcount=0; for(int count2=3;count2<=5;count2++) { sip[sipcount]=
		 * results[count2]; sipcount++; } // String isdn1=results[6]; String
		 * rmx=results[18]; String Calldelay=results[19]; String
		 * Contentdelay=results[20]; String TCdelay=results[21];
		 */
		sut[0] = results[0];
		sut[1] = results[1];
		sut[2] = results[2];
		sip[0] = results[3];
		sip[1] = results[4];
		sip[2] = results[5];
		String isdn1 = results[6];
		String Calldelay = results[8];
		String Contentdelay = results[9];
		String TCdelay = results[10];

		boolean callstatus = true, ppcipcontentstatus = true, vgacontentstatus = true, callspeedcheck = true,
				farendcontentcheck = true;
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

		int CallDelay = (int) (Float.parseFloat(Calldelay));
		int ContentDelay = (int) (Float.parseFloat(Contentdelay));
		int TCDelay = (int) (Float.parseFloat(TCdelay));

		String path = FilePath + "chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", path);

		/*
		 * System.setProperty("webdriver.ie.driver",
		 * "C:\\Users\\gboyina\\Downloads\\IEDriverServer_x64_2.48.0\\IEDriverServer.exe"
		 * ); WebDriver driver = new InternetExplorerDriver();
		 */
		// prerequisite(sut1,sut2,path);

		Call c = new Call();
		PPCIP p = new PPCIP();
		VGA v = new VGA();
		Output o = new Output();
		Mute m = new Mute();
		Info in = new Info();
		/*
		 * int[]
		 * cr={56,64,112,128,168,192,224,256,280,320,336,384,392,504,512,560,576
		 * ,616,
		 * 640,672,704,728,768,784,832,840,952,960,1008,1024,1064,1088,1120,1152
		 * ,1176,
		 * 1216,1232,1280,1288,1400,1408,1456,1472,1512,1536,1568,1600,1624,1664
		 * ,1680, 1728,1792,1856,1920};
		 */

		int[] cr = { 1024 };

		int len = cr.length;
		String testsut1 = results[0];
		WebDriver driver = new ChromeDriver();
		System.out.println("Opening sut in chrome browser");
		driver.manage().window().maximize();
		driver.get("https://" + testsut1 + "/");
		// Testcase execution
		WebElement element = driver.findElement(By.id("main_0"));
		element.click();
		driver.switchTo().activeElement();
		driver.switchTo().frame(driver.findElement(By.name("contentFrame")));
		int counter = 0;
		for (int sutc = 0; sutc <= 2; sutc++) {
			String sut1 = sut[sutc];
			for (int sipc = 0; sipc < 2; sipc++) {
				if (sutc == sipc) {
					sipc++;
					System.out.println("Ids matched ");
				}

				System.out.println();
				String sip1 = sip[sipc];
				System.out.println(sip1);
				for (int i = 0; i < len; i++) {
					System.out.println("In counter " + counter++);
					int c1 = cr[i];
					String str = Integer.toString(c1);
					String testname1 = "Basic Sanity: Beta Smoke-Point to Point SIP call ";
					String testinfo1 = "Call type-SIP Call rate-";
					testinfo1 = testinfo1.concat(str);
					System.out.println("\n" + testname1);
					System.out.println("--------------------------------------------------");
					// 1= SIP 2= H323 3= ISDN
					c.PlaceaCall(sut1, sip1, driver, element, 1, c1);
					Thread.sleep(10000);
					callstatus = c.hangupcheck(driver, element);
					if (callstatus) {
						System.out.println("Call Connected");
						callspeedcheck = c.CallSpeedCheck(sut1, c1);
						ppcipcontentstatus = p.ppcip(sut1, driver, element, ppcipcontentstatus, ContentDelay);
						// vgacontentstatus=v.ContentClass(sut1,driver,element,ContentDelay);
						// farendcontentcheck=v.farcontentcheck(sut2);
						vgacontentstatus = true;
						m.Mute(sut1);
						Thread.sleep(5000);
						callstatus = c.hangup(driver, element);
						System.out.println("Call Ended");
					} else {
						System.out.println("Call not made.Please Check your Settings");
						ppcipcontentstatus = false;
						vgacontentstatus = false;
						callstatus = false;
						callspeedcheck = false;
					}
					Thread.sleep(5000);
					/*
					 * if(vgacontentstatus== true && farendcontentcheck==true)
					 * vgacontentstatus=true; else vgacontentstatus=false;
					 */
					System.out.println("Test case execution completed, writing output to file");
					System.out.println();
					String Info = "Call Between " + sut1 + " & " + sip1;
					o.writecsv(Info, testname1, testinfo1, callstatus, vgacontentstatus, ppcipcontentstatus,
							callspeedcheck);
					System.out.println("------------CheckPoint-------------");
					Thread.sleep(5000);

				}
			}
		}

		driver.close();
		LogRegister.logger.info("Exiting CallTestSuite: main()");
		System.exit(0);
	}

}
