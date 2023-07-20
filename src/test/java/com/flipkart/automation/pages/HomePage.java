package com.flipkart.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.flipkart.automation.utils.PropertyFileReader;

public class HomePage extends BasePage {

	private WebDriver driver;

	@FindBy(xpath = "//button[contains(text(),'✕')]")
	private WebElement crossButton;

	@FindBy(xpath = "//input[@type = 'text']")
	private WebElement searchPlaceHolder;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement submitButton;

	@FindBy(xpath = "//img[@alt= 'Travel']")
	private WebElement flightIcon;

	@FindBy(xpath = "//img[@alt='Fashion']")
	private WebElement firstNavigationCard;

	@FindBy(xpath = "(//a[@class ='_1BJVlg'])[1]")
	private WebElement firstCategory;

	@FindBy(xpath = "//span[text() = 'Sign in']")
	private WebElement signInButton;

	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//Navigate to the home page.
	public void goTo() {
		driver.get(PropertyFileReader.getPropertyValue("websiteURL"));
	}


	//Close the cross button if present.
	public void closeLoginPopup() {
		if (isElementPresent(crossButton)) {
			crossButton.click();
		}
	}


	//Click on the search placeholder.
	public void clickOnSearch() {
		waitTillVisible(searchPlaceHolder);
		searchPlaceHolder.click();
	}

	/**
	 * Enter the product name in the search field.
	 * @param name: the product name to search
	 */
	public void enterProductName(String name) {
		searchPlaceHolder.clear();
		searchPlaceHolder.sendKeys(name);
	}

	// Click on Submit button and land on product list page
	public ProductListPage searchProductAndLandOnPLP() {
		submitButton.click();
		return new ProductListPage(driver);
	}

	/**
	 * Click on the flight icon to navigate to the BookFlightPage.
	 * @return an instance of the BookFlightPage
	 */
	public SearchFlightPage landOnBookFlightPage() {
		flightIcon.click();
		return new SearchFlightPage(driver);
	}

	public String getCategoryName() {
		// Move to the first navigation card to trigger the hover effect
		performInteractions().moveToElement(firstNavigationCard).perform();

		//Return the text of the first category
		return firstCategory.getText();
	}

	public ProductListPage clickCategoryAndLandOnPLP() {
		// Click on the first category using JavaScript
		clickUsingJavaScript(firstCategory);

		// Return a new instance of the ProductListPage
		return new ProductListPage(driver);
	}
}
