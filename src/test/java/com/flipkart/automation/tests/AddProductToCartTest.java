package com.flipkart.automation.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.flipkart.automation.pages.CartPage;
import com.flipkart.automation.pages.ProductDetailsPage;
import com.flipkart.automation.pages.ProductListPage;
import com.flipkart.automation.utils.JsonFileReader;

public class AddProductToCartTest extends BaseTest {

	private ProductDetailsPage productDetailsPage;
	private CartPage cartPage;
	private ProductListPage productListPage;
	private static Logger log = LogManager.getLogger();

	@Test(groups = { "CartTestCases" }, dataProvider = "jsonData")
	public void addProductToCart(HashMap<String, String> testData) {
		// Close the login popup if present
		homePage.closeLoginPopup();
		log.info("Successfully closed the login popup");

		homePage.clickOnSearch();
		log.info("Successfully clicked on the search button");

		String productName = testData.get("productName");
		log.info("Searching for product: " + productName);
		homePage.enterProductName(productName);

		productListPage = homePage.searchProductAndLandOnPLP();

		productListPage.validateSearchedProduct(productName);
		log.info("Verified the product: " + productName + " is visible on the product list page");

		String title = productListPage.getFirstProductTitle();
		productDetailsPage = productListPage.clickFirstProduct();

		String titleOnProductPage = productDetailsPage.getProductTitle();
		log.info("Product title on search results page: " + title);
		log.info("Product title on product details page: " + titleOnProductPage);

		// Compare the product titles on search and product details pages
		productDetailsPage.compareTitle(title, titleOnProductPage);
		log.info("Successfully compared the product titles on search results and product details pages");

		cartPage = productDetailsPage.addProductToCart();
		log.info("Added product to cart");

		String titleOnCartPage = cartPage.getProductTitle();

		// Compare the product titles on product details and cart pages
		cartPage.compareTitle(titleOnCartPage, titleOnProductPage);
		log.info("Successfully compared the product titles on product details and cart pages");

		cartPage.validateProductCount();
		log.info("Validated the product count in the cart");
	}

	// Retrieve test data from the product data JSON file
	@DataProvider(name = "jsonData")
	public Object[] getData() {
		List<HashMap<String, String>> data = null;
		try {
			data = JsonFileReader.getJsonData("ProductData");
		} catch (IOException e) {
			log.error("Failed to read test data from JSON file: " + e.getMessage());
			e.printStackTrace();
		}

		return new Object[] { data.get(0), data.get(1) }; // Test data for product names
	}
}
