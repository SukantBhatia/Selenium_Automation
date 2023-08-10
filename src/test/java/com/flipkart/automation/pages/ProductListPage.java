package com.flipkart.automation.pages;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ProductListPage extends BasePage {

	@FindBy(xpath = "//div[@class='_30jeq3 _1_WHN1']")
	private List<WebElement> productsPriceElementList;

	@FindBy(xpath = "(//a[@class='_1fQZEK'])[1]")
	private WebElement firstProductElement;

	@FindBy(xpath = "(//div[@class='_4rR01T'])[1]")
	private WebElement productTitleElement;

	@FindBy(xpath = "//div[@class='_10UF8M' and text()='Price -- Low to High']")
	private WebElement sortPriceLowToHighElement;

	@FindBy(xpath = "//div[@class='_10UF8M _3LsR0e' and text()='Price -- Low to High']")
	private WebElement sortPriceLowToHighSelectedElement;

	@FindBy(xpath = "//div[@class='_10UF8M' and text()='Price -- High to Low']")
	private WebElement sortPriceHighToLowElement;

	@FindBy(xpath = "//div[@class='_10UF8M _3LsR0e' and text()='Price -- High to Low']")
	private WebElement sortPriceHighToLowSelectedElement;
	
	@FindBy(xpath = "//h1[@class='_10Ermr']")
	private WebElement categoryTitle;

	public ProductListPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * Verify that the searched product is displayed.
	 * @param name: the product name to verify
	 */
	public void validateSearchedProduct(String name) {
		verifyElementIsDisplayed(driver.findElement(By.xpath("//span[normalize-space()='" + name + "']")));
	}

	/**
	 * Get the product title of the first product.
	 * @return the product title
	 */
	public String getFirstProductTitle() {
		return productTitleElement.getText();
	}

	/**
	 * Click on the first product displayed.
	 * @return an instance of the ProductDetailsPage
	 */
	public ProductDetailsPage clickFirstProduct() {
		waitTillVisible(firstProductElement);
		firstProductElement.click();
		switchToLastWindow();
		return new ProductDetailsPage(driver);
	}

	/**
	 * Sort products based on price and wait until it's selected
	 * @param input: to identify the type of sorting
	 */
	public void sortProductsPrice(String sortType) {
		if (sortType.equals("Ascending")) {
			waitTillClickable(sortPriceLowToHighElement);
			sortPriceLowToHighElement.click();	
			waitTillVisible(sortPriceLowToHighSelectedElement);
		} else if (sortType.equals("Descending")) {
			waitTillClickable(sortPriceHighToLowElement);
			sortPriceHighToLowElement.click();	
			waitTillVisible(sortPriceHighToLowSelectedElement);
		}
	}

	/**
	 * Get all the visisble products prices using java stream
	 * @return a List containing all the prices
	 */
	public List<String> getAllProductsPrice() {
		List<String> priceStrings = productsPriceElementList.stream()
				.map(WebElement::getText)
				.collect(Collectors.toList());
		return priceStrings;
	}

	/**
	 * Sort the orignal price list and validate after performing sorting using javastream
	 * @param input: to identify the type of sorting
	 * @param prices: list of prices to be sorted
	 */
	public void validateProductPriceSorting(String sortType, List<String> orignalPriceStrings) {
		List<String> sortedPriceStrings = orignalPriceStrings.stream()
				.sorted(sortType.equals("Descending") ? Comparator.reverseOrder() : Comparator.naturalOrder())
				.collect(Collectors.toList());
		Assert.assertTrue(orignalPriceStrings.equals(sortedPriceStrings));
	}
	
	//Return the category title
    public String getCategoryName() {
    	return categoryTitle.getText();
    }
    
    /**
	 * Validate category name on homepage to plp 
	 * @param categoryNameOnHomePage
	 * @param categoryNameOnPLP
	 */
    public void validateCategory(String categoryNameOnHomePage, String categoryNameOnPLP) {
    	compareActualContainsExpected(categoryNameOnPLP.toLowerCase(),categoryNameOnHomePage.toLowerCase().trim().replaceAll(" ", ""));
    }
}
