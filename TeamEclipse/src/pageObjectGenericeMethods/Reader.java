package pageObjectGenericeMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Reader {

	// Set the excel sheet path
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static MultiMap<String, ?> multiMapForLocators = new MultiValueMap();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static MultiMap<String, ?> multiMapForData = new MultiValueMap();
	// public static String filename = System.getProperty("user.dir") +
	// "\\src\\Marlabs\\Data\\";

	public String path;
	public FileInputStream fis = null;
	// public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	public Reader() {

	}

	public Reader(String filepath) {
		path = filepath;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			fis.close();
		} catch (IOException e) {
		}
	}

	// Sheet path
	// Get the row count from sheet
	public int getRowCount(String sheetName) {

		System.out.println("Sheet Name ----" + sheetName);
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getPhysicalNumberOfRows();

			System.out.println("number " + number);
			return number;
		}
	}

	public int getColCount(String sheetName) {

		int noofColumns = sheet.getRow(0).getPhysicalNumberOfCells();
		// to specify the data points in excel (Except url's and other information)
		System.out.println("Number of columns: " + noofColumns);
		if (noofColumns == -1)
			return -1;
		else
			return noofColumns;
	}

	// Returns the data from sheet based on the
	// "Sheet name,Column name, Row count"
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);

			if (cell == null)
				return "";
			// New Change
			cell.setCellType(Cell.CELL_TYPE_STRING);
			// System.out.println(cell.getCellType());

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();
					// Get the time/date
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				// It will return the cell data
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {

			if (rowNum <= 0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);

			if (row == null)
				return "";

			cell = row.getCell(colNum);

			if (cell == null)
				return "";

			cell.setCellType(Cell.CELL_TYPE_STRING);

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	public void writeToExcel(String path, String sheetName, int rowNum, int colNum, String desc) {
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			Sheet s = wb.getSheet(sheetName);
			Row r = s.getRow(rowNum);
			Cell c = r.createCell(colNum);
			c.setCellValue(desc);
			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getSheetCount() {
		int count = workbook.getNumberOfSheets();
		if (count == -1)
			return 0;
		else {
			return count;
		}
	}

	public String GetSheetName(int sheetID) {
		String sheetName = workbook.getSheetName(sheetID);
		return sheetName;
	}

	public String getcelldata(String filename, String sheetname, String key, String colname) throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(filename);
		String strQuery = "Select " + colname + " from " + sheetname + " where Keywords='" + key + "'";
		// String strQuery= "Select "+colname+" from "+sheetname+" where
		// Keywords='"+key+"' and "+colname+"!=''";

		Recordset recordset = connection.executeQuery(strQuery);

		while (recordset.next()) {

		}
		return (recordset.getField(colname));
	}

	public String getDatafrompropfile(String Key) throws IOException {
		String value = null;
		File file = new File(System.getProperty("user.dir") + "\\src\\pageObjectData\\contantData.properties");

		try {
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			value = properties.getProperty(Key);

			if (Key.equalsIgnoreCase("url")) {
				value = properties.getProperty(Key);
			} else {
				value = System.getProperty("user.dir") + "\\src\\pageObjectData\\" + properties.getProperty(Key);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		return value;

	}

	// to fetch and print 19122017
	public HashMap<String, String> readDataFromExcel(String excelPath, String sheetName, int i) throws IOException, EncryptedDocumentException, InvalidFormatException {
		HashMap<String, String> hashMap = new HashMap<>();
		try {

			Fillo fillo = new Fillo();
			Connection connection = fillo.getConnection(excelPath);
			String strQuery = "Select * from " + sheetName;

			Recordset recordset = connection.executeQuery(strQuery);

			while (recordset.next()) {
				String key = recordset.getField("Keywords");
				String value = recordset.getField(getColname(excelPath, sheetName, i));
				hashMap.put(key, value);
			}
			recordset.close();
			connection.close();
		} catch (FilloException e) {
			e.printStackTrace();
		}
		return hashMap;
	}

	public int getColcount(String file, String sheetname) throws IOException {
		FileInputStream filein = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(filein);
		XSSFSheet sh = wb.getSheet(sheetname);
		XSSFRow row = sh.getRow(0);
		int colcount = row.getLastCellNum();

		return colcount;

	}

	@SuppressWarnings("unused")
	public static String getColname(String file, String sheetname, int colno) throws IOException, EncryptedDocumentException, InvalidFormatException {
		FileInputStream filein = new FileInputStream(file);
		org.apache.poi.ss.usermodel.Workbook wb = WorkbookFactory.create(filein);
		Sheet sh = wb.getSheet(sheetname);
		Row row = sh.getRow(0);
		Cell cell = sh.getRow(0).getCell(colno);

		return cell.getStringCellValue();

	}

	public void storeLoctorsontoMap(String sheetname) throws Exception {

		try {
			String keyfromexcel = null;
			int rowcount = getRowCount(sheetname);
			int Columncount = getColCount(sheetname);
			System.out.println(Columncount);

			ArrayList<String> al = new ArrayList<String>();
			for (int k = 1; k <= Columncount; k++) {

				String col_data = getCellData(sheetname, k, 1);
				al.add(col_data);
			}
			int column_size = al.size();
			for (int i = 1; i <= rowcount; i++) {
				// System.out.println(rowcount);
				keyfromexcel = getCellData(sheetname, "Keywords", i);
				// Constants.multiMapForOnlineEducation.put(key,key);
				for (int j = 0; j < column_size - 1; j++) {

					String colName = al.get(j).toString();
					String Columname = getCellData(sheetname, colName, i);
					// System.out.println(keyfromexcel+"-->"+Columname);
					multiMapForLocators.put(keyfromexcel, Columname);
				}
			}
			System.out.println("Adding loactors to the Map ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void storeDataontoMap(String sheetname) throws FilloException {

		String keyfromexcel = null;
		int rowcount = getRowCount(sheetname);
		int Columncount = getColCount(sheetname);
		System.out.println(Columncount);

		ArrayList<String> al = new ArrayList<String>();
		for (int k = 1; k <= Columncount; k++) {

			String col_data = getCellData(sheetname, k, 1);
			al.add(col_data);
		}
		int column_size = al.size();
		for (int i = 1; i <= rowcount; i++) {

			keyfromexcel = getCellData(sheetname, "Keywords", i);
			// Constants.multiMapForOnlineEducation.put(key,key);
			for (int j = 0; j < column_size - 1; j++) {

				String colName = al.get(j).toString();
				String Columname = getCellData(sheetname, colName, i);
				// System.out.println(keyfromexcel+"-->"+Columname);
				multiMapForData.put(keyfromexcel, Columname);
			}
		}
		System.out.println("Adding Data's to the Map ");

	}

	@SuppressWarnings("unchecked")
	public static ArrayList<String> objectReferLocator(String ORefer) {

		ArrayList<String> object = null;
		try {
			object = (ArrayList<String>) multiMapForLocators.get(ORefer);
		} catch (Exception e) {
			System.out.println("There is no object by the name: " + ORefer);
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<String> objectReferData(String ORefer) {

		ArrayList<String> object = null;
		try {
			object = (ArrayList<String>) multiMapForData.get(ORefer);
		} catch (Exception e) {
			System.out.println("There is no object by the name: " + ORefer);
		}
		return object;
	}

	public String getLocatorMap(String key) {
		ArrayList<String> value = objectReferLocator(key);
		String custval = value.get(0);
		return custval;
	}

	public String getDatarMap(String key) {
		ArrayList<String> value = objectReferData(key);
		String custval = value.get(0);
		return custval;
	}

	public ArrayList<String> getcellEOBData(String filename, String colname) throws FilloException {
		ArrayList<String> XlEOBvaluesList = new ArrayList<>();

		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(filename);

		String strQuery = "Select EOBs from MultipleEOBs where " + colname + "=1";

		Recordset recordset = connection.executeQuery(strQuery);

		while (recordset.next()) {
			XlEOBvaluesList.add(recordset.getField("EOBs"));
		}
		return (XlEOBvaluesList);
	}

	
	public void convertExcelFileToCSVFile(String excelFilePath, String csvFilePath) throws IOException {

		// First we read the Excel file in binary format into FileInputStream
		FileInputStream input_document = new FileInputStream(new File(excelFilePath));
		// Read workbook into HSSFWorkbook
		HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document);
		// Read worksheet into HSSFSheet
		HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
		// To iterate over the rows
		Iterator<Row> rowIterator = my_worksheet.iterator();
		// OpenCSV writer object to create CSV file
		FileWriter my_csv = new FileWriter(csvFilePath);
		CSVWriter my_csv_output = new CSVWriter(my_csv);
		// Loop through rows.
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			int i = 0;// String array
			// change this depending on the length of your sheet
			String[] csvdata = new String[2];
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				
				if(i!=3) {
					break;
				}
				
				Cell cell = cellIterator.next(); // Fetch CELL
				switch (cell.getCellType()) { // Identify CELL type
				// you need to add more code here based on
				// your requirement / transformations
				case Cell.CELL_TYPE_STRING:
					
					csvdata[i] = cell.getStringCellValue();
					break;
				}
				i = i + 1;
			}
			my_csv_output.writeNext(csvdata);
		}
		my_csv_output.close(); // close the CSV file
		// we created our file..!!
		input_document.close(); // close xls

	}
	
	
	public static void writeDataToExcelBasedOnColumn(String excelPath,String cellValue,String sheetName,String excelColumn) throws IOException, EncryptedDocumentException, InvalidFormatException {
		
	    FileOutputStream fos=null;
	   //create an object of Workbook and pass the FileInputStream object into it to create a pipeline between the sheet and eclipse.
	   FileInputStream fis = new FileInputStream(new File(excelPath));
	 //  XSSFWorkbook workbook = new XSSFWorkbook(fis);
	   org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(fis);
	   //call the getSheet() method of Workbook and pass the Sheet Name here. 
	    //or if you use the method getSheetAt(), you can pass sheet number starting from 0. Index starts with 0.
	   Sheet sheet = workbook.getSheet(sheetName);
	   int rowCount=sheet.getPhysicalNumberOfRows();

	    System.out.println("Row Count : "+rowCount);

	   Row row = sheet.createRow(rowCount);
	   int colCount=sheet.getRow(0).getPhysicalNumberOfCells(); 
	    System.out.println("colCount: "+colCount);
	   for(int i=0;i<=colCount;i++) {
		 // String columnName = row.getCell(i).getStringCellValue();
		   String columnName = getColname(excelPath, sheetName, i);
		  System.out.println("Column Name ="+columnName);
	        if(columnName.equalsIgnoreCase(excelColumn)) {
	        	 Cell cell = row.createCell(rowCount+1);
	      	
	      	   cell.setCellType(cell.CELL_TYPE_STRING);
	      	   cell.setCellValue(cellValue);
	      	   fos = new FileOutputStream(excelPath);
	      	   workbook.write(fos);	
	        	//break;
	        }
	    
	   
	   }
	   fos.close();
	  // System.out.println("END OF WRITING DATA IN EXCEL");
	   }
	public static void writetoExcel(String[] args) {
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
				  { cell = row.createCell(columnValue++); 
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
	
	
	
	
	
	
	
	
	


