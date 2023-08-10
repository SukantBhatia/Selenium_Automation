package com.flipkart.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.flipkart.automation.pages.BasePage;

public class ExcelFileReader {

	private static DataFormatter dataFormatter = new DataFormatter();

	/**
	 * Reads data from an Excel file and returns it as a two-dimensional array of objects.
	 * @param fileName: the name of the Excel file
	 * @return a two-dimensional array of objects representing the data from the Excel file
	 */
	public static Object[][] getExcelData(String fileName) {
		FileInputStream fis = null;
		XSSFWorkbook wb = null;

		try {
			fis = new FileInputStream(BasePage.userDir + "//src//test//resources//" + fileName);
			wb = new XSSFWorkbook(fis);

			// Get the sheet
			XSSFSheet sheet = wb.getSheetAt(0);
			int rowCount = sheet.getPhysicalNumberOfRows();
			XSSFRow row = sheet.getRow(0);

			// Get the column count based on rows
			int columnCount = row.getLastCellNum();

			// Retrieve the test data
			Object Data[][] = new Object[rowCount - 1][columnCount];
			
			for (int i = 0; i < rowCount - 1; i++) {
				row = sheet.getRow(i + 1);
				for (int j = 0; j < columnCount; j++) {
					// Read the cell value as a formatted string
					Data[i][j] = dataFormatter.formatCellValue(row.getCell(j));
				}
			}
			return Data;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the FileInputStream and workbook
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
