package test.utils;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.MediaEntityBuilder;
import base.test.BaseTestWeb;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.functions.ExpectedCondition;
import test.listeners.*;

public class WebElementInteractions extends TestListener {

	private WebDriverWait wait;
	private static BaseTestWeb baseTest;
	private static WebDriver browserDriver;

	public WebElementInteractions() {

		baseTest = new BaseTestWeb();
		browserDriver = baseTest.getDriver();
		this.wait = new WebDriverWait(baseTest.getDriver(), Duration.ofSeconds(30));

	}

	// click web element
	public void clickWebElement(WebElement element, String elementName) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			Log4jLogger.loggerInfo(elementName + " [button] " + " clicked Successfully! on " + baseTest.getBrowserName());
			test.pass(elementName + " [button] " + " clicked Successfully! on " + baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to click a [button] " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);

		}
	}

	// scroll to displayed

	public void scrollToElementDisplayed(WebElement element) {
		try {
			// Wait until element is present in DOM & visible
			wait.until(ExpectedConditions.visibilityOf(element));
			// Scroll into view
			((JavascriptExecutor) baseTest.getDriver())
					.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

			Log4jLogger.loggerInfo("Scrolled to element successfully on " + baseTest.getBrowserName());
			test.pass("Scrolled to element successfully on " + baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError("Failed to scroll to element: " + e.getMessage());
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);

			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver())
					.getScreenshotAs(OutputType.BASE64);
			test.fail("Scroll failed: " + e.getMessage(),
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

			onTestFailure(result);
		}
	}


	// send keys
	public void sendKeysWebElement(WebElement element, String msg) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.sendKeys(msg);
			Log4jLogger.loggerInfo(msg + " Sent Successfully! on " + baseTest.getBrowserName());
			test.pass(msg + " Sent Successfully! on " + baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to send keys to the input box: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
		}
	}

	// send keys with clear
	public void sendKeysWithClearWebElement(WebElement element, String msg) throws Exception {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			Thread.sleep(1000);
			element.sendKeys(msg);
			Log4jLogger.loggerInfo(msg + " Sent Successfully! on " + baseTest.getBrowserName());
			test.pass(msg + " Sent Successfully! on " + baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());

			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to send keys to the input box: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);


		}
	}

	// select a dropdown
	public void selectDropdownByVisibleText(WebElement dropdownElement, String visibleText) {
		try {
			Select dropdown = new Select(dropdownElement);
			dropdown.selectByVisibleText(visibleText);
			Log4jLogger.loggerInfo("" + visibleText + " Sent Successfully! on " + baseTest.getBrowserName());
			test.pass("" + visibleText + " Sent Successfully! on " + baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());
			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to select from dropdown: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
		}

	}

	public static void waitForPageToLoad(int timeoutInSeconds) {

		try {
			WebDriverWait wait = new WebDriverWait(baseTest.getDriver(), Duration.ofSeconds(timeoutInSeconds));
			wait.until((ExpectedCondition<Boolean>) wd ->
					((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());
		}
		Log4jLogger.loggerInfo("Page has fully loaded on " + baseTest.getBrowserName());

	}

	public  void clickElementWithJS(WebElement element) {

		try {
			JavascriptExecutor jsExecutor = (JavascriptExecutor) baseTest.getDriver();
			jsExecutor.executeScript("arguments[0].click();", element);
			Log4jLogger.loggerInfo("button clicked Successfully! on " + baseTest.getBrowserName());
			test.pass(" button clicked on Successfully" + baseTest.getBrowserName());

		} catch (Exception e) {

			Log4jLogger.loggerError(e.getMessage());
			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed to send keys to the input box: " + e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);

		}

	}


	public static WebDriver returnBrowserDriver() {
		return browserDriver;
	}

	public void failOnElementDipslay(WebElement element, String elementName,int timeInSec) throws IOException {

		try {

			WebDriverWait waitForElementToDisappear = new WebDriverWait(baseTest.getDriver(), Duration.ofSeconds(timeInSec));

			// check element displayed
			waitForElementToDisappear.until(ExpectedConditions.invisibilityOf(element));

			Log4jLogger.loggerInfo(elementName + " Not Displayed on "+baseTest.getBrowserName());
			test.pass(elementName +" Not Displayed on "+baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError("Failed :"+elementName+" Displayed  on: "+baseTest.getBrowserName());
			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed :"+elementName+" Displayed  on: " + baseTest.getBrowserName(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
			throw new IOException("Manually triggered IOException");
		}
	}

	public void waitForElementToDisplay (WebElement element, String elementName,int timeInSec) throws IOException {

		try {
			WebDriverWait waitForElementToAppear = new WebDriverWait(baseTest.getDriver(), Duration.ofSeconds(timeInSec));
			waitForElementToAppear.until(ExpectedConditions.visibilityOf(element));
			Log4jLogger.loggerInfo(elementName + " Displayed on :"+baseTest.getBrowserName());	            	           
			test.pass(elementName +" Displayed on :"+baseTest.getBrowserName());

		} catch (Exception e) {
			Log4jLogger.loggerError("Failed :"+e+" "+baseTest.getBrowserName());
			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Failed :"+elementName+" : " + baseTest.getBrowserName(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);


		}
	}


	public void compareText(String actual, String expected) {
		try {
			// Assertion with message
			Assert.assertEquals(actual, expected,
					"Text comparison failed: expected [" + expected + "] but found [" + actual + "]");

			// Log pass case
			Log4jLogger.loggerInfo(actual + " : matches expected : " + expected + " on " + baseTest.getBrowserName());
			test.pass(actual + " : matches expected : " + expected + " on " + baseTest.getBrowserName());

		} catch (AssertionError ae) {
			// Log failure
			Log4jLogger.loggerError(actual + " : does not match expected : " + expected + " on " + baseTest.getBrowserName());
			Log4jLogger.loggerError(ae.getMessage());

			// Mark TestNG result as failed
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(ae);

			// Screenshot for failure
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail(ae.getMessage(),
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

			// Custom failure handling
			onTestFailure(result);

			// Re-throw to ensure TestNG marks it properly
			throw ae;

		} catch (Exception e) {
			// Unexpected exception handling
			Log4jLogger.loggerError("Unexpected error during text comparison: " + e.getMessage());

			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);

			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Unexpected error: " + e.getMessage(),
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

			onTestFailure(result);

			throw new RuntimeException(e);
		}
	}


	public void compareText(WebElement element, String expectedText) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			String actualText = element.getText();

			// Assertion
			Assert.assertTrue(actualText.contains(expectedText),
					"Expected text [" + expectedText + "] not found in element text [" + actualText + "]");

			// Pass logging
			Log4jLogger.loggerInfo(actualText + " : contains expected text : " + expectedText + " on " + baseTest.getBrowserName());
			test.pass(actualText + " : contains expected text : " + expectedText + " on " + baseTest.getBrowserName());

		} catch (AssertionError ae) {
			// Log assertion failure
			Log4jLogger.loggerError("Assertion failed: " + ae.getMessage());

			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(ae);

			// Screenshot for failure
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Assertion failed: " + ae.getMessage(),
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

			onTestFailure(result);

			// Rethrow so TestNG marks test as failed
			throw ae;

		} catch (Exception e) {
			// Unexpected exception handling
			Log4jLogger.loggerError("Error during text comparison: " + e.getMessage());

			Assert.fail("Exception while comparing text: " + e.getMessage());

			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);

			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail("Could not compare text: " + e.getMessage(),
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

			onTestFailure(result);
		}
	}




	public boolean getTextFromElement(WebElement element) {
		boolean ElementText=false;
		try {
			wait.until(ExpectedConditions.visibilityOf(element));			
			if(element.getText()!=null){
				Log4jLogger.loggerInfo(element.getText()+ ": Extracted succesfully :"+" on "+baseTest.getBrowserName());
				test.pass(element.getText()+ ":  Extracted succesfully : on " +baseTest.getBrowserName());
				ElementText=true;
			}

			else {
				Log4jLogger.loggerError("Element contains Null on :"+baseTest.getBrowserName());
			}

		} catch (Exception e) {
			Log4jLogger.loggerError(e.getMessage());
			// Manually invoke the failure handling
			ITestResult result = Reporter.getCurrentTestResult();
			result.setStatus(ITestResult.FAILURE);
			result.setThrowable(e);
			String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
			test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			onTestFailure(result);
		}
		return ElementText;
	}

	  public List<String> getTextFromTable(WebElement tableElement) {
		    List<String> tableData = new ArrayList<>();
		    try {
		        wait.until(ExpectedConditions.visibilityOf(tableElement));
		        
		        List<WebElement> rows = tableElement.findElements(By.tagName("tr"));
		        for (WebElement row : rows) {
		            List<WebElement> cells = row.findElements(By.tagName("td"));
		            for (WebElement cell : cells) {
		                String cellText = cell.getText();
		                if (cellText != null && !cellText.isEmpty()) {
		                    Log4jLogger.loggerInfo("Extracted: " + cellText + " on " + baseTest.getBrowserName());
		                    test.pass("Extracted: " + cellText + " on " + baseTest.getBrowserName());
		                    tableData.add(cellText);
		                } else {
		                    Log4jLogger.loggerError("Empty or null cell value detected on: " + baseTest.getBrowserName());
		                }
		            }
		        }
		    } catch (Exception e) {
		        Log4jLogger.loggerError(e.getMessage());
		        ITestResult result = Reporter.getCurrentTestResult();
		        result.setStatus(ITestResult.FAILURE);
		        result.setThrowable(e);
		        String base64Screenshot = ((TakesScreenshot) baseTest.getDriver()).getScreenshotAs(OutputType.BASE64);
		        test.fail(e.getMessage(), MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
		        onTestFailure(result);
		    }
		    return tableData;
		}


	public String captureScreenshot(String screenshotName) {
		String base64Screenshot = "";
		try {
			TakesScreenshot ts = (TakesScreenshot) baseTest.getDriver();
			base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);

			// Log success
			Log4jLogger.loggerInfo("Screenshot captured: " + screenshotName + " on " + baseTest.getBrowserName());
			test.pass("Screenshot captured: " + screenshotName,
					MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());

		} catch (Exception e) {
			Log4jLogger.loggerError("Failed to capture screenshot: " + e.getMessage());
			test.fail("Failed to capture screenshot: " + e.getMessage());
		}
		return base64Screenshot;
	}


}
