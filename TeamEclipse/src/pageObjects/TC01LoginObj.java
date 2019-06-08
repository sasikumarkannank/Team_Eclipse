package pageObjects;
import org.openqa.selenium.By;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import pageObjectDriver.controller;
import pageObjectGenericeMethods.ProjectSpecificMethods;
import pageObjectGenericeMethods.Reader;
import pageObjectGenericeMethods.Utilities;
import pageObjectReports.Reports;

public class TC01LoginObj extends controller
{

	Reports repobj= new Reports();
	ProjectSpecificMethods psmObj = new ProjectSpecificMethods();
	Utilities utils = new Utilities();
	Reader reader = new Reader();

	String className=getClass().getSimpleName();


	public  void logintoApplication(ExtentTest rprtLogin) throws Exception
	{
		
		String date = psmObj.GetDateFromSystem("replaced");
		
		ExtentTest reportLogin = _extent.startTest("TC01LoginObj");

		fillobjLocator.storeLoctorsontoMap(className);
		fillobjData.storeDataontoMap(className);

		By signIn =  By.xpath(fillobjLocator.getLocatorMap("signIn"));	
		By emailTxtBox = By.id(fillobjLocator.getLocatorMap("emailTxtBox"));	
		By pwdTxtBox = By.id(fillobjLocator.getLocatorMap("pwdTxtBox"));	
		By signInSubmit = By.id(fillobjLocator.getLocatorMap("signInSubmit"));	
		
		try{
			Thread.sleep(5000);
			utils.toFocusClick(signIn);
			reportLogin.log(LogStatus.PASS, "Clicked on Sign In Button Successfully");
		}catch(Exception e){
			e.printStackTrace();
			reportLogin.log(LogStatus.FAIL, "Clicked on Sign In Button Successfully");
		}
		
		try{
			utils.toEnterData(emailTxtBox,fillobjData.getDatarMap("txtUsername"));
			reportLogin.log(LogStatus.PASS, "User name Entered Successfully");
		}catch(Exception e){
			e.printStackTrace();
			reportLogin.log(LogStatus.FAIL, "Failed to enter TestUsername");
		}
		
		try{
			utils.toEnterData(pwdTxtBox,fillobjData.getDatarMap("txtPassword"));
			reportLogin.log(LogStatus.PASS, "Password Entered Successfully");
		}catch(Exception e){
			e.printStackTrace();
			reportLogin.log(LogStatus.FAIL, "Failed to enter Password");
		}
		
		try{
			utils.toFocusClick(signInSubmit);
			reportLogin.log(LogStatus.PASS, "Clicked on SignIN button Successfully");
		}catch(Exception e){
			e.printStackTrace();
			reportLogin.log(LogStatus.FAIL, "Failed to Click on Sign in Button");
		}
		
		
		rprtLogin.appendChild(reportLogin);	
		_extent.endTest(reportLogin);
		String result = "Pass";
		String[] str123= {date,"Time","Time",className,"TotalSteps","PassedSteps",result};
		reader.writetoExcel(str123);
		reader.convertExcelFileToCSVFile("E:\\TeamEclipseWorkspace\\TeamEclipse\\src\\pageObjectData\\Test_File.xls","E:\\TeamEclipseWorkspace\\TeamEclipse\\src\\pageObjectData\\Test_File.csv");
	}	
	

}
