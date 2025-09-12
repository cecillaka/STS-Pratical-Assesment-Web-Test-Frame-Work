package base.test;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import test.utils.Log4jLogger;

public class BaseTestWeb {

    private DesiredCapabilities desiredCapabilities;
    private static WebDriver driver;
    private static String stringBrowserName;
    private static String StringseverURL;

    public void initializeBrowserDriver() {
        try {
            // Initialize DesiredCapabilities
            desiredCapabilities = new DesiredCapabilities();

            // Set browser and platform
            switch (stringBrowserName.toLowerCase()) {
                case "chrome":
                    desiredCapabilities.setBrowserName("chrome");
                    desiredCapabilities.setPlatform(Platform.ANY);
                    break;

                case "firefox":
                    desiredCapabilities.setBrowserName("firefox");
                    desiredCapabilities.setPlatform(Platform.ANY);
                    break;

                case "edge":
                    desiredCapabilities.setBrowserName("MicrosoftEdge");
                    desiredCapabilities.setPlatform(Platform.ANY);
                    break;

                default:
                    Log4jLogger.loggerWarn("Unsupported browser: " + stringBrowserName);                  
                    return;
            }
        
        	Log4jLogger.loggerInfo("Setting up RemoteWebDriver for browser: " + stringBrowserName);
            // Initialize the RemoteWebDriver
            driver = new RemoteWebDriver(new URL(StringseverURL), desiredCapabilities);
            Log4jLogger.loggerInfo("RemoteWebDriver initialized successfully!");

        } catch (MalformedURLException e) {           
            Log4jLogger.loggerError("Invalid URL for Selenium Grid Hub: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
        	Log4jLogger.loggerError("Error during driver setup or navigation: " + e.getMessage());
            e.printStackTrace();
        }
        
      //  return driver;
    }

    // Getter for the WebDriver instance
    public WebDriver getDriver() {
        return driver;
    }

    public static void setBrowseAndServerName(String browserName ,String severURL) {
    	 stringBrowserName=browserName;
         StringseverURL=severURL;
    }
    
    public String getBrowserName() {
    	return stringBrowserName;
    }
    // Close the driver
    public void tearDown() {
    	try {
			Thread.sleep(5000);
			 if (driver != null) {
		            Log4jLogger.loggerWarn("Closing Browser Driver..."); 
		            driver.quit();
		        }
		} catch (InterruptedException e) {
			 Log4jLogger.loggerError(e.getMessage()); 	
		}
       
    }
}
