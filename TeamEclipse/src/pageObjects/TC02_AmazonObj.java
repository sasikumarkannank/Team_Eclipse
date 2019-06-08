package pageObjects;

import org.openqa.selenium.By;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pageObjectDriver.controller;
import pageObjectGenericeMethods.ProjectSpecificMethods;
import pageObjectGenericeMethods.Reader;
import pageObjectGenericeMethods.Utilities;

public class TC02_AmazonObj extends controller{

	String className=getClass().getSimpleName();
	Utilities utils = new Utilities();
	ProjectSpecificMethods psmObj = new ProjectSpecificMethods();
	Reader reader = new Reader();
	
	public void serachForAnItem(ExtentTest test) throws Exception {
		
		String date = psmObj.GetDateFromSystem("replaced");
		ExtentTest srchTest = _extent.startTest("TC01LoginObj");

		fillobjLocator.storeLoctorsontoMap(className);
		fillobjData.storeDataontoMap(className);

		By srchTxtBox =  By.id(fillobjLocator.getLocatorMap("srchTxtBox"));
		By srchIcon =  By.id(fillobjLocator.getLocatorMap("srchIcon"));
		
		_driver.navigate().back();
		Thread.sleep(2000);
		_driver.navigate().back();
		//entering text in search textbox
		try{
			utils.toEnterData(srchTxtBox,fillobjData.getDatarMap("srchTxt"));
			srchTest.log(LogStatus.PASS, "Search Text entered Successfully");
		}catch(Exception e){
			e.printStackTrace();
			srchTest.log(LogStatus.FAIL, "Failed to enter search Text");
		}
		
		//Clicking on search icon
		try{
			Thread.sleep(5000);
			utils.toFocusClick(srchIcon);
			srchTest.log(LogStatus.PASS, "Clicked on search icon Successfully");
		}catch(Exception e){
			e.printStackTrace();
			srchTest.log(LogStatus.FAIL, "Failed to click on Search icon Successfully");
		}
		
		
		test.appendChild(srchTest);	
		_extent.endTest(srchTest);
		
		String result = "Pass";
		String[] str123= {date,"Time","Time",className,"TotalSteps","PassedSteps",result};
		reader.writetoExcel(str123);
		reader.convertExcelFileToCSVFile("E:\\TeamEclipseWorkspace\\TeamEclipse\\src\\pageObjectData\\Test_File.xls","E:\\TeamEclipseWorkspace\\TeamEclipse\\src\\pageObjectData\\Test_File.csv");
	}
	
	
	
	
	
}
