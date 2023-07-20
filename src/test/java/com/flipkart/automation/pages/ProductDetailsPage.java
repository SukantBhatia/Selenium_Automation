package com.flipkart.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailsPage extends BasePage {

	@FindBy(xpath = "//button[normalize-space()='Add to cart']")
	private WebElement cartButton;

	@FindBy(xpath = "//span[@class = \"B_NuCI\"]")
	private WebElement productTitle;


	public ProductDetailsPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Get the product title on the product details page.
	 * @return the product title
	 */
	public String getProductTitle() {
		return productTitle.getText();
	}

	/**
	 * Compare the product title on the product details page with the expected title.
	 * @param title: the expected product title
	 * @param titleOnProductPage: the actual product title on the page
	 */
	public void compareTitle(String title, String titleOnProductPage) {
		compareActualContainsExpected(title, titleOnProductPage);	
	}

	/**
	 * Click on the "Add to cart" button to add the product to the cart.
	 * @return an instance of the CartPage
	 */
	public CartPage addProductToCart() {
		cartButton.click();
		return new CartPage(driver);
	}
}
