package pageObjectGenericeMethods;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjectDriver.controller;

public class Utilities extends controller
{

	public WebDriverWait wait=null;
	/* &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	 * Action class listed
	 * &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	 */

	/* Method Name : toFocus(By locator)
	 * Description : This function will mouse over on the element
	 */	
	public void toFocus(By locator) throws InterruptedException
	{		
		WebElement element =_driver.findElement(locator);				
		new Actions(_driver).moveToElement(element).perform();		
	}	


	/* Method Name : toFocusClick(By locator) 
	 * Description : This function is used to focus on element and click
	 */
	public void toFocusClick(By locator) throws InterruptedException
	{	
		WebElement element =_driver.findElement(locator);				
		new Actions(_driver).moveToElement(element).click().perform();	
	}


	/* Method Name : getDataFromTextBox(By locator)
	 * Description : This function is used to get attribute value
	 */
	public String getDataFromTextBox(By locator) {
		return _driver.findElement(locator).getAttribute("value");
	}

	/* Method Name : getWebElementtext(WebElement element)
	 * Description : This function is used to get the data
	 */
	public String getWebElementtext(WebElement element) throws InterruptedException {
		String strValue = element.getText();
		return strValue;
	}

	/* &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	 * Web driver class listed
	 * &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	 */
	/* Method Name : toClickElement(By locator)
	 * Description : This function will click on the element based on attribute
	 */
	public void toClickElement(By locator) throws InterruptedException 
	{
		_driver.findElement(locator).click();;
	}

	/* Method Name : toEnterData(By locator,String testValue) 
	 * Description : This function is used to focus on element and click
	 */
	public void toEnterData(By locator,String testValue) throws InterruptedException
	{
		Thread.sleep(1000);
		_driver.findElement(locator).sendKeys(Keys.chord(Keys.CONTROL, "a"),testValue);
	}

	/* Method Name : waitForElementToBeClickable(WebElement element)
	 * Description : This function will wait for element to click
	 */
	protected WebElement waitForElementToBeClickable(WebElement element)
	{		
		wait = new WebDriverWait(_driver,20);
		return wait.until(ExpectedConditions.elementToBeClickable(element)); 
	}

	/* Method Name : toSubmit(By locator)
	 * Description : This function used to click
	 */
	public void toSubmit(By locator) throws InterruptedException
	{
		_driver.findElement(locator).submit();	
	}

	/* Method Name : toChkBtnEnabled(By locator)
	 * Description : This function used to check element is enabled or not
	 */
	public boolean toChkBtnEnabled(By locator) throws InterruptedException
	{
		boolean enbVal = _driver.findElement(locator).isEnabled();
		Thread.sleep(1000);
		return enbVal;
	}

	/* Method Name : isSelected(By locator)
	 * Description : This function used to check element already selected or not
	 */
	public boolean isSelected(By locator)
	{
		WebElement element =_driver.findElement(locator);	
		element.isSelected();
		return true;
	}

	/* Description : This function used to check element already selected or not */		
	public void datefromtext(By locator,String date)
	{
		_driver.findElement(locator).sendKeys(date);
	}

	/* Description : This function used to select from drop down by using visible text */	 
	public void toSelectDatafromDropdown(By label,String dropdownValue)
	{
		Select selectDropdown = new Select(_driver.findElement(label));
		selectDropdown.selectByVisibleText(dropdownValue);
	}

	/* Description : This function used to select from drop down by using value */
	public void toSelectDatafromDropdownByValue(By label,String dropdownValue)
	{
		Select selectDropdown = new Select(_driver.findElement(label));				
		selectDropdown.selectByValue(dropdownValue);
	}

	/* Description : This function used to select from drop down by using locator and index */
	public String toSelectfromDropdown(By locator, String Data)
	{
		String retval = null;
		List<WebElement> options = _driver.findElements(locator);
		try
		{
			for(int i=0; i<options.size(); i++)
			{
				if(options.get(i).getText().equalsIgnoreCase(Data))
				{
					options.get(i).click();
					Thread.sleep(1000);		
				}
				retval=options.get(i).getText();
			}	
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
		return retval;
	}

	/* Description : This function used get text from drop down */
	public String toGetTextOfSlectedValue(By locator)
	{
		Select selectDropdown = new Select(_driver.findElement(locator));
		String selctedText = selectDropdown.getFirstSelectedOption().getText();
		return selctedText;
	}

	/* Description : This function used to scroll down*/
	public void toScrollDown()
	{
		((JavascriptExecutor) _driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}	

	/* Description : This function used to scroll down to particular web element*/
	public void toScrolltoWebElement(WebElement testElement)
	{
		((JavascriptExecutor) _driver).executeScript("arguments[0].scrollIntoView();", testElement);
	}

	/* Description : This function used to scroll down based on locator*/
	public void toScrolltoBylocator(By locator)
	{
		WebElement testElement =_driver.findElement(locator);
		((JavascriptExecutor) _driver).executeScript("arguments[0].scrollIntoView();", testElement);
	}

	/* Description : This function used to scroll down and get the value*/
	public String getVal(By locator)
	{
		WebElement element1 = _driver.findElement(locator);
		JavascriptExecutor e = (JavascriptExecutor) _driver;
		return (String) e.executeScript(String.format("return $('#%s').val();", element1.getAttribute("id")));
	}

	/* Description : This function used to get and set the value*/
	public String getValuebyJS(By locator)
	{
		JavascriptExecutor js = (JavascriptExecutor) _driver;
		WebElement element = _driver.findElement(locator);

		js.executeScript("arguments[0].setAttribute('type', '')",element);

		System.out.println(_driver.findElement(locator).getAttribute("value"));
		return _driver.findElement(locator).getAttribute("value");
	}

	/* Description : This function used to get by using locator */
	public String getLabel(By locator)
	{
		String strValue =  _driver.findElement(locator).getText();
		System.out.println("String Value"+strValue);
		return strValue;
	}

	/* Description : This function used to attribute value*/
	public String getLabelbyAttrbute(By locator)
	{
		String strValue =  _driver.findElement(locator).getAttribute("value");
		System.out.println("String Value"+strValue);
		return strValue.trim();
	}		
}
