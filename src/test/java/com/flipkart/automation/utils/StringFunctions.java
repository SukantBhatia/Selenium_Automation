package com.flipkart.automation.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;

public class StringFunctions {

	private static String timeStamp;

	/**
	 * Trims a string by removing special characters and spaces, and truncates it to a maximum length.
	 * @param input the input string to be trimmed
	 * @return the trimmed string
	 */
	public static String trimString(String input) {
		String output = input.replaceAll("[^a-zA-Z0-9\\s]", "").replaceAll("\\s+", "_");

		// Truncate the string to a maximum length
		int maxLength = 50;
		if (output.length() > maxLength) {
			output = output.substring(0, maxLength);
		}

		return output;
	}

	/**
	 * Retrieves the current timestamp in the format "yyyy-MM-dd-HH-mm". 
	 * @return the current timestamp
	 */
	public static String getTimeStamp() {
		if (timeStamp == null) {
			timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm").format(new Date());
		}
		return timeStamp;
	}

	/**
	 * Moves test results directories to Archive directory from current results.
	 */
	public static void moveResultsToArchivedDirectory() {
		String archivedDirectoryPath = "Archived test results";
		String currentDirectoryPath = "Current test results";
		
        // Create File instances for the current and archived directories
		File currentDirectory = new File(currentDirectoryPath);
		File archivedDirectory = new File(archivedDirectoryPath);

        // List all subdirectories within the current directory
		File[] directories = currentDirectory.listFiles(File::isDirectory);

		if (directories != null && directories.length > 0) {
			for (File directory : directories) {
				try {
                    // Move the directory to the archived directory
					FileUtils.moveDirectoryToDirectory(directory, archivedDirectory, true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getTestParameterValue(ITestResult result) {
		Object[] parametersArray = result.getParameters();
	    StringBuilder parameters = new StringBuilder();

	    if (parametersArray != null && parametersArray.length > 0) {
	        parameters.append(" [");
	        for (int i = 0; i < parametersArray.length; i++) {
	            String parameterName = result.getMethod().getConstructorOrMethod().getMethod().getParameters()[i].getName();
	            parameters.append(parameterName).append(": ").append(parametersArray[i]);
	            
	            if (i < parametersArray.length - 1) {
	                parameters.append(", ");
	            }
	        }
			return parameters.append("]").toString().replaceAll("arg0:", "").replaceAll("[\\[\\]{}]", "");
	    }
		return "";
	}
}
