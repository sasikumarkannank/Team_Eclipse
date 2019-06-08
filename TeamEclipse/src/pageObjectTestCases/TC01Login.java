package pageObjectTestCases;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import pageObjectDriver.controller;
import pageObjectGenericeMethods.Reader;
import pageObjects.TC01LoginObj;


public class TC01Login extends controller
{
	@Test
	public void MCOLogin() throws Exception 
	{
		try {
			//starting test
			//test
			ExtentTest rprtLogin = _extent.startTest("TC01Login");

			TC01LoginObj obj = new TC01LoginObj();

			obj.logintoApplication(rprtLogin);

			_extent.endTest(rprtLogin);
			
			Reader.multiMapForLocators.clear();
			
			Reader.multiMapForData.clear();
			

			//Ramya commented
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Reader.multiMapForLocators.clear();
			Reader.multiMapForData.clear();
		}
	}


}
