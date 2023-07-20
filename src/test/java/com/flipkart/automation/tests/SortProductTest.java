package com.flipkart.automation.tests;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.flipkart.automation.pages.ProductListPage;
import com.flipkart.automation.utils.ExcelFileReader;

public class SortProductTest extends BaseTest {

	private ProductListPage productListPage;
	private List<String> prices;
	private static Logger log = LogManager.getLogger();

	@Test(dataProvider = "excelData")
	public void sortProductTest(String productName) {
		// Cancel login popup if present
		homePage.closeLoginPopup();
		log.info("Closed the login popup");

		homePage.clickOnSearch();
		log.info("Clicked on the search field");

		homePage.enterProductName(productName);
		log.info("Entered product name: " + productName);

		productListPage = homePage.searchProductAndLandOnPLP();
		log.info("Landed on the product list page");

		productListPage.validateSearchedProduct(productName);
		log.info("Validated the product: " + productName + " is visible");

		productListPage.sortProductsPrice("Ascending");
		log.info("Sorted products by price in ascending order");

		prices = productListPage.getAllProductsPrice();

		productListPage.validateProductPriceSorting("Ascending", prices);
		log.info("Validated product price sorting in ascending order");

		productListPage.sortProductsPrice("Descending");
		log.info("Sorted products by price in descending order");

		prices = productListPage.getAllProductsPrice();

		productListPage.validateProductPriceSorting("Descending", prices);
		log.info("Validated product price sorting in descending order");
	}

	@DataProvider(name = "excelData")
	public Object[][] getData() {
		return ExcelFileReader.getExcelData("TestData.xlsx");
	}
}
