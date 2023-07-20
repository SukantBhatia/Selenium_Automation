package com.flipkart.automation.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.flipkart.automation.pages.ProductListPage;

public class SearchProductTest extends BaseTest {

    private ProductListPage productListPage;
    private static Logger log = LogManager.getLogger();

    @Test
    public void searchProductTest() {
        // Cancel login popup if present
        homePage.closeLoginPopup();
        log.info("Closed the login popup");

        homePage.clickOnSearch();
        log.info("Clicked on the search field");

        String productName = "AC";
        homePage.enterProductName(productName);
        log.info("Entered product name: " + productName);

        productListPage = homePage.searchProductAndLandOnPLP();
        log.info("Landed on the product list page");

        // Verify that the searched product is displayed
        productListPage.validateSearchedProduct(productName);
        log.info("Validated the product: " + productName+ "is visible");
    }
}
