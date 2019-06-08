package pageObjectGenericeMethods;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.codoid.products.exception.FilloException;
import com.relevantcodes.extentreports.ExtentTest;



import pageObjectDriver.controller;



public class ProjectSpecificMethods extends controller
{
	

	
	public boolean compareDates(String dos,String doi) throws ParseException, FilloException, IOException, InterruptedException
	{
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

		Date ddos = format.parse(dos);
		System.out.println("DDOS--->"+ddos);
		Date ddoi = format.parse(doi);
		System.out.println("DDOI--->"+ddoi);

		return ddos.compareTo(ddoi) < 0;

	}

	
	// To get the system date
	public String GetDateFromSystem(String replaced) {
		Date myDate = new Date();
		String convertLocDate = new SimpleDateFormat("MM-dd-yyyy").format(myDate);
		replaced = convertLocDate.replace("-", "/");
		System.out.println(replaced);
		return replaced;
	}

	public String systemDate(String replaced)
	{
		Date myDate = new Date();
		String convertLocDate = new SimpleDateFormat("M-d-yyyy").format(myDate);
		replaced = convertLocDate.replace("-", "/");
		System.out.println(replaced);
		return replaced;
	}

	public String getnextDayDate()
	{
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		String prevDate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
		String currDate = dateFormat.format(date.getTime());
		String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);
		System.out.println("Previous date: " + prevDate);
		System.out.println("Currnent date: " + currDate);
		System.out.println("Next date: " + nextDate);
		return nextDate;
	}

	public static String getDatafrompropfile(String Key)  
	{

		String value = null;
		File file = new File(System.getProperty("user.dir") + "\\src\\pageObjectData\\contantData.properties");

		try 
		{
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			value = properties.getProperty(Key);

			if(Key.equalsIgnoreCase("url"))
			{
				value = properties.getProperty(Key);
			}
			else				
			{
				value = System.getProperty("user.dir") + "\\src\\pageObjectData\\" + properties.getProperty(Key);
			}

		} 
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return value;
	}


	public Double removeCommafromString(String Value)
	{	
		String Number = Value.replace(",", "");
		Double optVal = Double.parseDouble(Number);
		System.out.println(optVal);
		return optVal;
	}

	//Method to take the screenshot of particular table and print table in extentreports
	public void getScreenShotOfTable(By locator , ExtentTest reports) throws IOException
	{

		WebElement ele = _driver.findElement(locator);
		File screenshot = ((TakesScreenshot) _driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(screenshot);

		Point point = ele.getLocation();// Get the location of element on the page


		int eleWidth = ele.getSize().getWidth();// Get width and height of the element

		int eleHeight = ele.getSize().getHeight();

		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight); //Crop the entire element to capture
		ImageIO.write(eleScreenshot, "png", screenshot);

		// Copy the element screenshot to disk
		File screenshotLocation = new File(".//MarlabsMCORedesignFramework//Screenshots");
		FileUtils.copyFile(screenshot, screenshotLocation); 

		//reports.addScreenCapture(screenshotLocation);

	}
	public void switchToNewWindow(String Pwindow){
		Set<String> windows=_driver.getWindowHandles();
		System.out.println("Window Size "+windows.size());
		for(String window :windows){
			System.out.println("window "+window);
			if(!window.contains(Pwindow)){
			_driver.switchTo().window(window).close();
			}
		}
		
	}
	
}

