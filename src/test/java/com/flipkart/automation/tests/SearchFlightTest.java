package com.flipkart.automation.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.flipkart.automation.pages.SearchFlightPage;
import com.flipkart.automation.utils.JsonFileReader;

public class SearchFlightTest extends BaseTest {

	private SearchFlightPage bookFlightPage;
	private static Logger log = LogManager.getLogger();

	@Test(dataProvider = "jsonData")
	public void searchFlightTest(HashMap<String, String> testData) {
		// Cancel login popup if present
		homePage.closeLoginPopup();
		log.info("Clicked on the close button");

		bookFlightPage = homePage.landOnBookFlightPage();
		log.info("Landed on the book flight page");

		bookFlightPage.enterFromAndToCity(testData.get("fromLocation"), testData.get("toLocation"));
		log.info("Entered from location: " + testData.get("fromLocation"));
		log.info("Entered to location: " + testData.get("toLocation"));

		bookFlightPage.clickOnSearchFlight();
		log.info("Clicked on search flight button");

		bookFlightPage.validateSearchedFlight(testData.get("fromLocation"), testData.get("toLocation"));
		log.info("Succesfully Validated the searched flight details for from location:" + testData.get("fromLocation")
				+ " and to location: " + testData.get("toLocation"));
	}

	// Retrieve test data from the Flight data JSON file
	@DataProvider(name = "jsonData")
	public Object[] getData() {
		List<HashMap<String, String>> data = null;
		try {
			data = JsonFileReader.getJsonData("FlightData");
		} catch (IOException e) {
			log.error("Failed to read test data from JSON file: " + e.getMessage());
			e.printStackTrace();
		}

		return new Object[] { data.get(0), data.get(1) }; // Test data for flight names
	}
}
