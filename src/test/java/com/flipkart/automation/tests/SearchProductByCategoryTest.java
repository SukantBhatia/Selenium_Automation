package com.flipkart.automation.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.flipkart.automation.pages.ProductListPage;

public class SearchProductByCategoryTest extends BaseTest {

    private ProductListPage productListPage;
    private static Logger log = LogManager.getLogger();

    @Test
    public void searchProductByCategoryTest() {
        // Cancel login popup if present
        homePage.closeLoginPopup();
        log.info("Closed the login popup");

        String categoryNameOnHomePage = homePage.getCategoryName();
        log.info("Category name on home page: " + categoryNameOnHomePage);
        
        productListPage = homePage.clickCategoryAndLandOnPLP();
        log.info("Clicked on the first category navigation card and land on plp");

        String categoryNameOnPLP = productListPage.getCategoryName();
        log.info("Category name on plp: " + categoryNameOnPLP);

        productListPage.validateCategory(categoryNameOnHomePage, categoryNameOnPLP);
        log.info("Validated successfully "+categoryNameOnHomePage+" with " +categoryNameOnPLP);
    }
}
