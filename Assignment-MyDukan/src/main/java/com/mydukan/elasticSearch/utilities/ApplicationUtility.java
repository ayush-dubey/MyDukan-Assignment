package com.mydukan.elasticSearch.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Component;

@Component
public class ApplicationUtility {

	/**
	 * Utility for reading any excel file using the apache poi
	 * The key of return map is the name of spread sheet
	 * The value of return map is the list of rows inside the spread sheet which inturn contains the list of cells inside it 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public Map<String,List<List<String>>> extractDataFromexcel(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Map<String,List<List<String>>> toReturn = new HashMap<>();
		
		//XSSFRow row = null;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			XSSFSheet spreadsheet= workbook.getSheetAt(i);
			String spreadSheetName =workbook.getSheetName(i);
			List<List<String>> rowList = new ArrayList<>();
			//int rowNum=spreadsheet.getPhysicalNumberOfRows()-spreadsheet.getFirstRowNum();
			for(Row row : spreadsheet) {
				//row = spreadsheet.getRow(j);
				if(isRowEmpty(row)) {
					continue;
				}
				List<String> cellList = new ArrayList<>(); 
				for(int k =0;k<row.getLastCellNum();k++) {
					Cell cell = row.getCell(k);
					if(Cell.CELL_TYPE_NUMERIC==cell.getCellType())
						cell.setCellType(Cell.CELL_TYPE_STRING);
					cellList.add(cell.getStringCellValue());
					/*
					 * switch (cell.getCellType()) { case Cell.CELL_TYPE_NUMERIC:
					 * cellList.add(String.valueOf(cell.getNumericCellValue())); break;
					 * 
					 * case Cell.CELL_TYPE_STRING:
					 * 
					 * break; }
					 */
				}
				rowList.add(cellList);
			}
			toReturn.put(spreadSheetName, rowList);
		}
		fis.close();
		return toReturn;

	}
	
	private boolean isRowEmpty(Row row) {
		if (row == null) {
	        return true;
	    }
	    if (row.getLastCellNum() <= 0) {
	        return true;
	    }
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}
}