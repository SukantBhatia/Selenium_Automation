package com.flipkart.automation.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import com.flipkart.automation.pages.BasePage;
import com.flipkart.automation.pages.HomePage;
import com.flipkart.automation.utils.PropertyFileReader;

public class BaseTest {

	public WebDriver driver;
	public HomePage homePage;
	private static Logger log = LogManager.getLogger();

	/**
	 * Initializes the WebDriver based on the browser specified in the properties file
	 * @return The WebDriver instance
	 */
	public WebDriver initializeDriver() {
		String browserName = PropertyFileReader.getPropertyValue("browser");

		switch (browserName) {
		case "Chrome":
			driver = new ChromeDriver();
			break;
		case "Firefox":
			driver = new FirefoxDriver();
			break;
		case "Safari":
			driver = new SafariDriver();
			break;
		}
		log.info("Executing test on the brower : "+browserName+"");
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(BasePage.defaultWaitTime));
		return driver;
	}

	/**
	 * Launches the application by initializing the WebDriver and navigating to the home page.
	 * @return The instance of the HomePage
	 */
	@BeforeMethod(alwaysRun = true)
	public HomePage launchApplication() {
		log.info("Test case started");
		driver = initializeDriver();
		homePage = new HomePage(driver);
		homePage.goTo();
		log.info("Successfully navigated to the Home Page");
		return homePage;
	}

	/**
	 * Closes the browser after each test method.
	 */
	@AfterMethod(alwaysRun = true)
	public void closeBrowser() {
		driver.close();
		log.info("Successfully closed the browser");
		log.info("Test case completed");
	}

	/**
	 * Closes the browser after each test method.
	 */
	@AfterTest(alwaysRun = true)
	public void closeBrowsers() {
		if (driver!=null && isSessionValid(driver)) {
			driver.close();
			log.info("Successfully closed the browser");
			log.info("Test case completed");
		}
	}
	
	/**
	 * Quits the browser after test suite
	 */
	@AfterSuite(alwaysRun = true)
	public void quitBrowser() {
		if (driver!=null && isSessionValid(driver)) {
			driver.quit();
			log.info("Successfully quits the browser");
		}
	}
	
	/**
	 * Captures a screenshot of the current page.
	 * @param filePath: Path of the file
	 * @param screenshotName: Name of the screenshot to be added
	 * @param driver: executable driver having the running test config
	 * @throws IOException if there is an error in accessing the file
	 */
	public void takeScreenshot(String filePath, String screenshotNameAndFormat, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File destination = new File(filePath, screenshotNameAndFormat);
		FileUtils.copyFile(source, destination);
	}
	
	private static boolean isSessionValid(WebDriver driver) {
        try {
            // Check if the WebDriver session is still valid by accessing the session ID
            driver.getWindowHandle();
            return true;
        } catch (NoSuchSessionException e) {
            return false;
        }
    }
}
