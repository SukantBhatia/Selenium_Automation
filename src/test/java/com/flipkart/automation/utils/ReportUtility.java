package com.flipkart.automation.utils;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.flipkart.automation.pages.BasePage;
import com.flipkart.automation.tests.BaseTest;

public class ReportUtility extends BaseTest implements ITestListener {

	private ExtentTest logger;
	private ExtentReports reports;
	private ThreadLocal<ExtentTest> threadLocalReport = new ThreadLocal<>();
	public static String currentTestResultsPath = BasePage.userDir + "//Current test results//"
			+ StringFunctions.getTimeStamp() + "//";

	@Override
	public void onStart(ITestContext context) {
		// Executed on start, intialize the extent reports with customization
		// Move existing test results to archived directory
		StringFunctions.moveResultsToArchivedDirectory();
		reports = ExtentReporterConfig.getReportConfig(currentTestResultsPath);
	}

	@Override
	public void onTestStart(ITestResult result) {
		// Executed before the start of each test method
	    logger = reports.createTest(result.getMethod().getMethodName()+ " : [ " + StringFunctions.getTestParameterValue(result)+ " ]");
		threadLocalReport.set(logger);
		threadLocalReport.get().log(Status.INFO, result.getMethod().getMethodName() + "Test is started ");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// Executed when a test method passes successfully
		threadLocalReport.get().log(Status.PASS, result.getMethod().getMethodName() + "Test is passed ");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// Executed when a test method fails, attach screenshot
		threadLocalReport.get().log(Status.FAIL, result.getMethod().getMethodName() + "Test is failed");

		String methodName = result.getMethod().getMethodName();

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

		String errorDescription = StringFunctions.trimString(result.getThrowable().getMessage());
		String screenshotNameAndFormat = methodName + errorDescription + ".png";
		try {
			// take screenshot and add to the report folder
			takeScreenshot(currentTestResultsPath, screenshotNameAndFormat, driver);
			// addd screenshot to the extent report
			threadLocalReport.get().addScreenCaptureFromPath(currentTestResultsPath + "//" + screenshotNameAndFormat,
					methodName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		threadLocalReport.get().fail(result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// Executed when a test method is skipped
		threadLocalReport.get().log(Status.SKIP, "Test Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		// Executed after the completion of the test run
		reports.flush();
	}
}
