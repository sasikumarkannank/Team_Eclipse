package pageObjectDriver;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import pageObjectGenericeMethods.ProjectSpecificMethods;
import pageObjectGenericeMethods.Reader;
import pageObjectReports.Reports;

public class controller  {

	public static WebDriver _driver = null;
	public static ExtentReports _extent=null;
	public static Reader fillobjLocator = new Reader(ProjectSpecificMethods.getDatafrompropfile("FilePageobjectLocator"));
	public static Reader fillobjData = new Reader(ProjectSpecificMethods.getDatafrompropfile("FilePageobjectTestData"));
	Reader fillobj = new Reader();
	//ProjectSpecificMethods psm = new ProjectSpecificMethods();


	@BeforeSuite
	public void initilialize() throws IOException, InterruptedException 
	{
		String browserName = ProjectSpecificMethods.getDatafrompropfile("BrowserName");
		
		System.out.println("user.dir: " + System.getProperty("user.dir"));
	
		this.launchBrowser(browserName);		
		
		//TimeUnit.SECONDS.sleep(1);
		_driver.manage().window().maximize();

		_driver.get(ProjectSpecificMethods.getDatafrompropfile("url"));
		
		_extent = Reports.startReport();
	}

	@AfterTest
	public void after() 
	{
		_extent.close();

		if(_driver!=null)
			_driver.close();
	}
	
	public void launchBrowser(String browserName)
	{

		if(browserName.contains("Chrome")) 
		{
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "//driver//chromedriver_win32//chromedriver.exe");
			_driver = new ChromeDriver();
		}
		else if(browserName.contains("FireFox"))
		{
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir") + "//driver//geckodriver//geckodriver.exe");
			_driver = new FirefoxDriver();
		}
		else if(browserName.contains("InternetExplorer")) 
		{
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir") + "//driver//IEDriverServer//IEDriverServer.exe");
			_driver = new InternetExplorerDriver();
		}
		else
		{
			System.err.print("Browser is Null");
		}
	}
}
