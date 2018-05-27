package com.jani.selenium;

import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.jani.framework.ReadPropertiesFile;
//import com.thoughtworks.selenium.webdriven.commands.WindowFocus;

public class SeleniumWebDriver {

	public static WebElement webelement;
	public static List<WebElement> webelements = null;
	public static WebDriver driver = null;
	public static int defaultBrowserTimeOut = 30;
	public static List<String> windowHandlers;
	
	
	public static WebDriver startBrowser(String browserName, boolean FirefoxBrowserProxy)
			throws UnknownHostException {
	
		deleteTempFile();

		if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "\\geckodriver.exe");
		/*	DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("marionette", true);
			WebDriver driver = new FirefoxDriver(capabilities);*/
			if(FirefoxBrowserProxy)
			{
		
				
			}
			else
			driver = new FirefoxDriver();
		} 
		
		else if (browserName.equalsIgnoreCase("iexplorer")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\IEDriverServer.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
			capabilities.setCapability("ignoreZoomSetting", true);
			driver = new InternetExplorerDriver(capabilities);
		} 
		
		else if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		}

		driver.manage().timeouts().implicitlyWait(defaultBrowserTimeOut, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		
		if (browserName.equalsIgnoreCase("iexplorer"))
			SeleniumWebDriver.SwitchAlert();

		if (windowHandlers == null)
			windowHandlers = new LinkedList<String>();
		else
			windowHandlers.clear();

		windowHandlers.add(driver.getWindowHandle());
		driver.manage().window().maximize();
		return driver;
	
	}
	
		
	public static void deleteTempFile() throws UnknownHostException {
		String property = "java.io.tmpdir";
		String temp = System.getProperty(property);
		File directory = new File(temp);
		try {
			delete(directory);
		} catch (IOException j) {
			j.printStackTrace();
			System.exit(0);
		}
	}

	public static void delete(File file) throws IOException {
		if (file.isDirectory()) { // directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : "
							+ file.getAbsolutePath());
				}
			}
		} else {
			// if file, then delete it
			file.delete();
		}
	}

	public static boolean SwitchAlert() {
		boolean Flag = false;

		try {
			if (driver.switchTo().alert() != null) {
				driver.switchTo().alert().accept();
				Flag = true;
			}

		}

		catch (NoAlertPresentException e) {
			
		}
		return Flag;

	}


	
}
