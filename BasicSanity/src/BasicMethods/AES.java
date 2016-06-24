package BasicMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
/**
 * 
 * The AES Class Contains method for AES Encrypiton ON and OFF
 * 
 * 
 * @author gboyina
 *	@version 1.0
 *	@since
 */
public class AES {
	
	/**
	 * aes method enables/disables encryption for given SUT
	 * 
	 * 
	 * @param aes - AES Option  aes = 1 -> Required All aes = 2 -> OFF
	 * @param sut - IP of SUT
	 * @throws InterruptedException
	 */
	
	public void aes(int aes,String sut) throws InterruptedException
	{
		LogRegister.setLogger();
		LogRegister.setPropertyPaths();
		LogRegister.setLogFile();
		LogRegister.logger.info(" Entering AES:aes()  ");
		FilePath f = new FilePath();
		String FilePath=f.getfilepath();
		String  path=FilePath+"chromedriver.exe";
		System.out.println(path);
		System.setProperty("webdriver.chrome.driver",path);
		sut = "https://10.221.50.52/";
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(sut);
		
		Thread.sleep(3000);
  
		WebElement element ;
		element = driver.findElement(By.id("main_1"));
		element.click();
	
		element = driver.findElement(By.id("admin_general_securitymain"));
		element.click();
		Thread.sleep(3000);
	
		element = driver.findElement(By.id("admin_general_securitymain_security"));
		element.click();
		Thread.sleep(3000);
		driver.switchTo().activeElement();
		driver.switchTo().frame(driver.findElement(By.id("contentFrame")));
		if(aes==1){
			Select dropdown = new Select(driver.findElement(By.id("encryptionenable")));
			dropdown.selectByValue("Required_All");
		}
		else if(aes==2){
			Select dropdown = new Select(driver.findElement(By.id("encryptionenable")));
			dropdown.selectByValue("False");
		}
		driver.switchTo().defaultContent();
		element = driver.findElement(By.xpath("//input[@value='Update']"));
		element.click();
		driver.close();
		LogRegister.logger.info("Exiting AES : aes()");
	}
}
