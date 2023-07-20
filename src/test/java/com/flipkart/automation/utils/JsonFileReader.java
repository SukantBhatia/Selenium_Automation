package com.flipkart.automation.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.automation.pages.BasePage;

public class JsonFileReader {

	/**
	 * Get the exxtent report configurations
	 * @param fileName: the name of the json file
	 * @return data in list of hashmap 
	 */
	public static List<HashMap<String, String>> getJsonData(String fileName) throws IOException {

		// read json to string
		File file = new File(BasePage.userDir + "//src//test//resources//"+fileName+".json");
		String jsonContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        // Convert JSON string to List of HashMaps
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}
}