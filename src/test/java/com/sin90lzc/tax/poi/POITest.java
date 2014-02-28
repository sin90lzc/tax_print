package com.sin90lzc.tax.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;

import com.sin90lzc.util.system.SystemProperty;

public class POITest {

	private static final String WORKBOOK_NAME = "work_book_test.xsl";

	private static final String col00 = "½ð¶î";

	private static final String col01 = "ÈÕÆÚ";
	private static final String col10="10.4";
	
	private static final Date CAL=new Date();

	private void createWordBook() throws Exception{

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("20140228");
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(col00);
		cell=row.createCell(1);
		cell.setCellValue(col01);
		row=sheet.createRow(1);
		cell=row.createCell(0);
		cell.setCellValue(col10);
		cell=row.createCell(1);
		cell.setCellValue(CAL);
		
		FileOutputStream os=new FileOutputStream(getFileTempPath());
		workbook.write(os);
	}
	
	private void analysisWorkbook() throws Exception{
		InputStream is=new FileInputStream(getFileTempPath());
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheet("20140228");
		HSSFRow row = sheet.getRow(0);
		Assert.assertEquals(col00, row.getCell(0).getStringCellValue());
		Assert.assertEquals(col01, row.getCell(1).getStringCellValue());
		row=sheet.getRow(1);
		Assert.assertEquals(col10, row.getCell(0).getStringCellValue());
		Assert.assertEquals(CAL, row.getCell(1).getDateCellValue());
		is.close();
	}
	
	private String getFileTempPath() {
		return SystemProperty.getProjectRootDir()+"/target/"+WORKBOOK_NAME;
	}
	
	private void deleteWorkbook() throws Exception{
		new File(getFileTempPath()).delete();
	}
	
	@Test
	public void testPoi() throws Exception{
		createWordBook();
		analysisWorkbook();
		deleteWorkbook();
	}
}
