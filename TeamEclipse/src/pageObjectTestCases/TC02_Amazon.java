package pageObjectTestCases;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;

import pageObjectDriver.controller;
import pageObjects.TC02_AmazonObj;

public class TC02_Amazon extends controller{
  @Test
  public void amazonSearch() throws Exception {
	  
	  ExtentTest test = _extent.startTest("TC02_Amazon");

	  TC02_AmazonObj obj = new TC02_AmazonObj();
	  
	  obj.serachForAnItem(test);
	  
	  _extent.endTest(test);
	  
	  
	  
  }
}
