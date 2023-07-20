package com.flipkart.automation.pages;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.flipkart.automation.utils.PropertyFileReader;

public class BasePage {

	public WebDriver driver;
	public static String userDir = System.getProperty("user.dir");

	// Default wait time for element visibility
	public static int defaultWaitTime = Integer.parseInt(PropertyFileReader.getPropertyValue("defaultWaitTime"));

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Waits until the element is visible using the default wait time.
	 * @param element: The element to wait for visibility
	 */
	public void waitTillVisible(WebElement element) {
		waitTillVisible(element, defaultWaitTime);
	}

	/**
	 * Waits until the element is visible using a specified wait time.
	 * @param element: The element to wait for visibility
	 * @param waitTime: The wait time in seconds
	 */
	public void waitTillVisible(WebElement element, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Waits until the element is clickable.
	 * @param element The element to wait for clickability
	 */
	public void waitTillClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(defaultWaitTime));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Checks if the element is present on the page.
	 * @param element The element to check for presence
	 * @return true if the element is present, false otherwise
	 */
	public boolean isElementPresent(WebElement element) {
		try {
			waitTillVisible(element, 5);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verifies that the element is displayed on the page.
	 * @param element The element to verify for display
	 */
	public void verifyElementIsDisplayed(WebElement element) {
		waitTillVisible(element);
		Assert.assertTrue(element.isDisplayed(),""+element+"is not displayed");
	}

	/**
	 * Switches the driver's focus to the specified window.
	 * @param windowHandle The window handle of the target window
	 */
	public void switchToWindow(String windowHandle) {
		driver.switchTo().window(windowHandle);
	}

	/**
	 * Switches the driver's focus to the last opened window.
	 */
	public void switchToLastWindow() {
		Set<String> windowHandles = driver.getWindowHandles();
		for (String windowHandle : windowHandles) {
			switchToWindow(windowHandle);
		}
	}

	/**
	 * Compares if the expected string contains the actual string.
	 * @param actual: The actual string
	 * @param expected: The expected string
	 */
	public static void compareActualContainsExpected(String actual, String expected) {
		Assert.assertTrue(expected.contains(actual), "" + expected + " text not equal/contains" + actual);
	}

	/**
	 * Compares if the expected string is equal to the actual string.
	 * @param actual: The actual string
	 * @param expected: The expected string
	 */
	public static void compareActualEqualsExpected(String actual, String expected) {
		Assert.assertEquals(actual, expected, "" + actual + "text not equals" + expected);
	}

	/**
	 * Creates an instance of Actions class to perform interactions on the web page.
	 * @return an instance of Actions class
	 */
	public Actions performInteractions() {
		return new Actions(driver);	
	}

	/**
	 * Performs a click on the given WebElement using JavaScript.
	 * @param element: the WebElement to click
	 */
	public void clickUsingJavaScript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;	
		js.executeScript("arguments[0].click();", element);  
	}
}
