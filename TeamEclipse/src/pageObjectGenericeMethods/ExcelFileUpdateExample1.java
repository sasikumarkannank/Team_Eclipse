package pageObjectGenericeMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
 
/**
 * This program illustrates how to update an existing Microsoft Excel document.
 * Append new rows to an existing sheet.
 *
 * @author www.codejava.net
 *
 */
public class ExcelFileUpdateExample1 {
 
 
    public void writetoExcel(String[] args) {
        String excelFilePath = "E:\\TeamEclipseWorkspace\\TeamEclipse\\src\\pageObjectData\\Test_File.xls";
         
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
 
            Sheet sheet = workbook.getSheetAt(0);
 
           // Object[] bookData = {"The Passionate Programmer", "Chad Fowler", 16};
 
            int rowCount = sheet.getLastRowNum();
 
                Row row = sheet.createRow(++rowCount);
                int columnValue=0;
                Cell cell = row.createCell(columnValue);
               // cell.setCellValue("The Passionate Programmer");
                 
				  for (Object field : args) 
				  { cell = row.createCell(++columnValue); 
				  if (field
				  instanceof String) { cell.setCellValue((String) field); } else if (field
				  instanceof Integer) { cell.setCellValue((Integer) field); 
				  } 
				  }
				  
            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream("E:\\TeamEclipseWorkspace\\TeamEclipse\\src\\pageObjectData\\Test_File.xls");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
             
        } catch (IOException | EncryptedDocumentException
                | InvalidFormatException ex) {
            ex.printStackTrace();
        }
    }
 
}