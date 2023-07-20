package com.flipkart.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterConfig {

	/**
	 * Get the exxtent report configurations
	 * @param filePath: the path where report will be stored
	 * @return extent report instance
	 */
    public static ExtentReports getReportConfig(String filePath) {
        // Define the path for the HTML report file
        String path = filePath + "//extentreports.html";
        
        // Create an instance of ExtentSparkReporter with the specified path
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);

        // Configure the report name and document title from properties file
        reporter.config().setReportName(PropertyFileReader.getPropertyValue("reportName"));
        reporter.config().setDocumentTitle(PropertyFileReader.getPropertyValue("reportTitle"));

        // Create an instance of ExtentReports
        ExtentReports reports = new ExtentReports();
        
        // Attach the reporter to the ExtentReports instance
        reports.attachReporter(reporter);
        return reports;
    }
}
