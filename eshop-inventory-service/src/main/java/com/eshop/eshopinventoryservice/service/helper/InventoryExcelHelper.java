package com.eshop.eshopinventoryservice.service.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.eshop.eshopinventoryservice.model.inventory.InventoryProductDTO;

/**
 * Class to parse list of inventory products form .xlsx (Excel Workbook/Worksheet) file
 */

@Service
public class InventoryExcelHelper {

	@Autowired
	private ProductCategoryConverter productCatgeoryConverterObject;

	private static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private static String[] HEADERS = { "inventoryProductID", "inventoryProductName", "inventoryProductQuantity", "inventoryProductPrice", "inventoryProductCategory" };
	private static String specificSheetName = "SHEET";
	
	private Workbook workbookObject = null;
	private Sheet sheetObject = null;
	
	/**
	 * Check if file sent is excel format or not
	 * @param file
	 * @return if required format
	 */
	public static boolean isExcelFormat(MultipartFile file) {
	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }
	    return true;
	  }
	
	/**
	 * Parse list of inventoryProductDTOs from an excel file
	 * @param inputStream
	 * @return list of inventoryProductDTOs
	 */
	public List<InventoryProductDTO> excelToInventoryProductDTOs(InputStream inputStream) throws IOException {

		List<InventoryProductDTO> inventoryProductDTOs = new ArrayList<InventoryProductDTO>();

		try {
			int rowNumber = 0;
			workbookObject = new XSSFWorkbook(inputStream);
			sheetObject = workbookObject.getSheet(specificSheetName);

			Iterator<Row> rows = sheetObject.iterator();

			while (rows.hasNext()) {
				  Row currentRow = rows.next();
				  Iterator<Cell> rowColumns = currentRow.iterator();
				  if (rowNumber == 0) {
					  if(!checkFileHeaders(currentRow, rowColumns)) {
						  throw new IOException("Excel File Columns not correct or not correctly Aligned");
					  }
			          rowNumber++;
			          continue;
			        }
				  
				  InventoryProductDTO inventoryProductDTOObject = new InventoryProductDTO(); 
				  int cellNumber = 0;
				  Iterator<Cell> cellsInRow = currentRow.iterator();

				  while (cellsInRow.hasNext() && cellNumber<HEADERS.length) {
					  Cell currentCell = cellsInRow.next();

					  switch (cellNumber) {
			          case 0:	inventoryProductDTOObject.setInventoryProductID((long) currentCell.getNumericCellValue());
			          			break;
			          case 1:	inventoryProductDTOObject.setInventoryProductName(currentCell.getStringCellValue());
			            		break;
			          case 2:	inventoryProductDTOObject.setInventoryProductQuantity((long) currentCell.getNumericCellValue());
			          			break;
			          case 3:	inventoryProductDTOObject.setInventoryProductPrice((long) currentCell.getNumericCellValue());
			            		break;
			          case 4:	inventoryProductDTOObject.setInventoryProductCategory(
			        		  	productCatgeoryConverterObject.convertToEntityAttribute(currentCell.getStringCellValue()));
	            				break;
			          default:	break;
			          }

			          cellNumber++;
			        }

				  inventoryProductDTOs.add(inventoryProductDTOObject);
				  }

			workbookObject.close();
		}
		catch(IOException ioe) {
			throw new IOException("Failed to parse Excel file: " + ioe.getMessage());
		}
		return inventoryProductDTOs;
	}
	
	
	/**
	 * Check if file has cell columns correctly aligned as mentioned in HEADERS array
	 * @param currentRow - the first row containing column names
	 * @param rowColumns - Iterator for the row
	 * @return true, if columns correctly aligned else false
	 */
	public boolean checkFileHeaders(Row currentRow, Iterator<Cell> rowColumns) {
		int index = 0;
		while(rowColumns.hasNext() && index < HEADERS.length) {
			if(!HEADERS[index].equalsIgnoreCase(rowColumns.next().getStringCellValue())) {
				return false;
			}
			index++;
		}
		return true;
	}

}
