package com.flipkart.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CartPage extends BasePage {

	@FindBy(xpath = "(//*[starts-with(text(),'Flipkart')])[1]")
	private WebElement productCount;

	@FindBy(xpath = "//button[normalize-space()='Add to cart']")
	private WebElement cartButton;

	@FindBy(xpath = "//a[@class='_2Kn22P gBNbID']")
	private WebElement productTitle;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Get the product title displayed on the cart page.
	 * @return the text of the product title
	 */
	public String getProductTitle() {
		waitTillClickable(productTitle);
		return productTitle.getText();
	}

	/**
	 * Compares the title on the cart page with the title on the product page.
	 * @param titleOnCartPage: the title displayed on the cart page
	 * @param titleOnProductPage: the title displayed on the product page
	 */
	public void compareTitle(String titleOnCartPage, String titleOnProductPage) {
		compareActualContainsExpected(titleOnCartPage, titleOnProductPage);
	}

	/**
	 * Adds the product to the cart.
	 */
	public void addProductToCart() {
		cartButton.click();
	}

	/**
	 * Verifies the product count. The count should be greater than or equal to 1.
	 */
	public void validateProductCount() {
		String[] productCounts = productCount.getText().split("\\s");
		int count = Integer.parseInt(productCounts[1].replaceAll("[()]", ""));
		Assert.assertTrue(count >= 1, "Count should be greater/equal than/to 1 but found as " + count);
	}
}
