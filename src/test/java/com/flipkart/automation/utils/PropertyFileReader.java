package com.flipkart.automation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {

	/**
	 * Get the extent report configurations
	 * @param propertyName: the name of the property to be fetched
	 * @return value of the property
	 */
	public static String getPropertyValue(String propertyName) {
		// Fetch the properties file data
		File file = new File(System.getProperty("user.dir") + "//src//test//resources//GlobalData.properties");
		FileInputStream fis = null;
		Properties prop = null;

		try {
			fis = new FileInputStream(file);
			prop = new Properties();
			prop.load(fis);
	
			// Get the value of the specified property name
			return prop.getProperty(propertyName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Close the FileInputStream
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
