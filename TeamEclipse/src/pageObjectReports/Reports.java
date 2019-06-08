package pageObjectReports;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reports 
{
	//********************************************************************************************************************
	// Method to fetch the currentime from the system and to create unique report with pre-fix current time on the report folder
	//********************************************************************************************************************
	@SuppressWarnings("unused")
	public static String currenttime() 
	{
		String currtime;
		String reportpath = System.getProperty("user.dir") + "\\Reports\\";
		String timenow = null;
		String timenow2 = null;
		try 
		{
			Calendar c1 = new GregorianCalendar();
			currtime = c1.getTime().toString();
			String curtime = currtime.replace(" ", "_");
			timenow = "Test_Results_" + curtime.replace(":", ".");

			timenow2 = currtime.replace(":", "");
			String[] testdata = timenow.split(" ");
			File dir = new File(reportpath + "\\" + timenow);
			dir.mkdir();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return timenow;
	}

	static String reportLocation = System.getProperty("user.dir") + "\\Reports\\";
	static String imageLocation =  "images\\";

	public static String reportFolder = Reports.currenttime();

	//********************************************************************************************************************
	// Method for Extent report creation 
	//********************************************************************************************************************
	@SuppressWarnings("deprecation")
	public static ExtentReports startReport() 
	{
		ExtentReports extent = new ExtentReports(reportLocation + reportFolder + "\\" + "Automation Report.html", false);

		extent.config().documentTitle("Automation test execution report");

		extent.config().reportHeadline("<b>Automation test execution report</b>");

		extent.config().reportName(" Report");

		/*extent.config().insertCustomStyles("<style> table, th, td { "
				+ "border: 1px solid black;"
				+ "width:150%;" + 
				"} </style>");*/


		return extent;
	}
	//********************************************************************************************************************
	// Method to capture scrennshot for every step
	//********************************************************************************************************************
	public static String createScreenshot(WebDriver driver) 
	{

		UUID uuid = UUID.randomUUID();

		// generate screenshot as a file object
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try 
		{
			// copy file object to designated location
			FileUtils.copyFile(scrFile, new File(reportLocation + reportFolder + "\\" + imageLocation + uuid + ".jpeg"));

		}
		catch (Exception e)
		{
			System.out.println("Error while generating screenshot:\n" + e.toString());
		}
		return reportLocation+reportFolder+"\\" +imageLocation + uuid + ".jpeg";
	}

	public static void reportValidate(ExtentTest Report,boolean status,String Content)
	{
		if(status=true)
		{
			Report.log(LogStatus.PASS, Content+" Succesfull");
		}
		else
		{
			Report.log(LogStatus.FAIL, Content+" Unsuccesfull");
		}
	}

	public static void reportComparision(ExtentTest Report,String Expected,String Actual,String Content) {

		if(Expected.equalsIgnoreCase(Actual) || Actual.equalsIgnoreCase(Expected))
		{
			Report.log(LogStatus.PASS, Content+" Matched");
		}
		else
		{
			Report.log(LogStatus.FAIL, Content+" Mismatched");
		}

	}

	// to capture specific web element
	public static String CaptureElementScreenShot(WebDriver driver,String billtype) throws IOException, InterruptedException
	{
		Thread.sleep(1000);
		//JavascriptExecutor executor = (JavascriptExecutor)driver;
		//executor.executeScript("document.body.style.zoom = '0.9'");

		UUID uuid = UUID.randomUUID();


		WebElement e = null;
		try 
		{
			e = driver.findElement(By.id("tblLineItems_wrapper"));


			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);


			Point point = e.getLocation();

			int ImageWidth = e.getSize().getWidth();
			int ImageHeight = e.getSize().getHeight();
			BufferedImage  fullImg = ImageIO.read(scrFile);

			System.out.println("Width :"+ImageWidth);
			System.out.println("Height :"+ImageHeight);

			System.out.println("point.getX() :"+point.getX());
			System.out.println("point.getY() :"+ point.getY());

			if(billtype.equalsIgnoreCase("Medical"))
			{
				// Crop the entire page screenshot to get only element screenshot
				BufferedImage eleScreenshot= fullImg.getSubimage(245, 190,ImageWidth ,ImageHeight);
				ImageIO.write(eleScreenshot, "png",scrFile);
			}
			else
			{
				BufferedImage eleScreenshot= fullImg.getSubimage(245, 120,ImageWidth ,ImageHeight);
				ImageIO.write(eleScreenshot, "png",scrFile);
			}
			FileUtils.copyFile(scrFile, new File(reportLocation + reportFolder + "\\" + imageLocation + uuid + ".png"));
		}
		catch (Exception e1) 
		{
			e1.printStackTrace();
			System.out.println(e1);
		}



		return reportLocation+reportFolder+"\\" +imageLocation + uuid + ".png";
	}

	public static ExtentReports LineItemReport(String billType)
	{					
		//UUID uuid = UUID.randomUUID();
		ExtentReports extentReport = new ExtentReports(reportLocation+reportFolder+"//EOBReport_"+billType+"_BillType.html", true);
		return extentReport;

	}

	public static void lineItemEndReport(ExtentReports report, ExtentTest test)
	{
		report.endTest(test);
		report.flush();
		report.close();
	}

}
