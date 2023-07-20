package com.flipkart.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchFlightPage extends BasePage {

	@FindBy(xpath = "//input[@tabindex='01']")
	private WebElement fromInputElement;

	@FindBy(xpath = "//input[@tabindex='02']")
	private WebElement toInputElement;;

	@FindBy(xpath = "//a[@class='_2Kn22P gBNbID']")
	private WebElement productTitle;

	@FindBy(xpath = "//span[text()='SEARCH']")
	private WebElement searchButton;

	@FindBy(xpath = "(//div[@class='_3Jcym_'])[1]")
	private WebElement searchFlightResult;

	@FindBy(xpath = "(//button[text()='23'])[1]")
	private WebElement defaultDate;

	public SearchFlightPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public String getProductTitle() {
		waitTillClickable(productTitle);
		return productTitle.getText();
	}

	/**
	 * Enters the from and to city in the input fields.
	 * @param fromCity: the source city
	 * @param toCity: the destination city
	 */
	public void enterFromAndToCity(String fromCity, String toCity) {
		waitTillClickable(fromInputElement);
		fromInputElement.sendKeys(fromCity);
		WebElement selectFromCity = driver.findElement(By.xpath("(//span[text()='" + fromCity + "'])[1]"));
		waitTillClickable(selectFromCity);
		performInteractions().moveToElement(selectFromCity).perform();
		performInteractions().click(selectFromCity).build().perform();

		waitTillClickable(toInputElement);
		toInputElement.sendKeys(toCity);
		WebElement selectToCity = driver.findElement(By.xpath("(//span[text()='" + toCity + "'])[2]"));
		waitTillClickable(selectToCity);
		performInteractions().moveToElement(selectToCity).perform();
		performInteractions().click(selectToCity).build().perform();
	}

	/**
	 * Clicks on the search flight button.
	 */
	public void clickOnSearchFlight() {
		// This is used to select the date temporarily
		WebElement alternateDate = driver.findElement(By.xpath("(//button[text()='23'])[2]"));
		if (defaultDate.isEnabled()) {
			defaultDate.click();
		} else {
			alternateDate.click();
		}

		waitTillClickable(searchButton);
		searchButton.click();
	}

	/**
	 * Verifies the searched flight details.
	 * @param fromCity: the expected source city
	 * @param toCity: the expected destination city
	 */
	public void validateSearchedFlight(String fromCity, String toCity) {
		String result = searchFlightResult.getText();
		String[] results = result.split("\n");
		compareActualEqualsExpected(results[0], fromCity);
		compareActualEqualsExpected(results[1], toCity);
	}
}
