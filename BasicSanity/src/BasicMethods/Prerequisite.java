package BasicMethods;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
/**
 * 
 *  @author gboyina
 *	@version 1.0
 *	@since
 */
public class Prerequisite 
{
	public static void prerequisite(String s1,String s2,String path)
	{
		LogRegister.setLogger();
		LogRegister.setPropertyPaths();
		LogRegister.setLogFile();
		LogRegister.logger.info("Entering Prerequisite: prerequisite()");
		System.setProperty("webdriver.chrome.driver",path);
	 	WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
	    driver.get("https://"+s1+"/");
	    WebElement element = driver.findElement(By.id("main_1"));
		element.click();
		driver.switchTo().activeElement();
		driver.switchTo().frame(driver.findElement(By.name("contentFrame")));
		Select dropdown1 = new Select(driver.findElement(By.id("answersinglecall")));
		dropdown1.selectByValue("Yes");
		element= driver.findElement(By.id("update"));
		element.click();
		driver.close();
		LogRegister.logger.info("Exiting Prerequiste : prerequisite()");
	}

		
}
