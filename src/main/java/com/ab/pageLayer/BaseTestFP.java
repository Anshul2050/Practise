package com.ab.pageLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Hello world!
 *
 */
public class BaseTestFP {

	Properties pro;
	public WebDriver driver;

	@Test
	public WebDriver initialiseBrowser() {

		String configPath = System.getProperty("user.dir") + "\\src\\test\\resources\\configFiles\\config.properties";
		pro = new Properties();
		try {
			FileInputStream fis = new FileInputStream(configPath);
			pro.load(fis);
		} catch (Exception e) {

			e.printStackTrace();

		}

		String browserName = pro.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {

			driver = new ChromeDriver();
		}
		if (browserName.equalsIgnoreCase("firefox")) {

			driver = new FirefoxDriver();
		}
		if (browserName.equalsIgnoreCase("edge")) {

			driver = new EdgeDriver();

		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		return driver;

	}
	
	
	public String getScreenshot(WebDriver driver,String testcase) {
		String shotSaved = System.getProperty("user.dir")+"\\Resources\\"+testcase+".png";
		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File des = new File(shotSaved);
		try {
			FileHandler.copy(src, des);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return  shotSaved;
		
	}

	@BeforeMethod
	public void launchApplication() {
		    driver = initialiseBrowser();
		    driver.get(pro.getProperty("url"));
	}
	
	public void tearDown() {
		
		driver.close();
		driver.quit();
		
	}

}
