package test.pages.web;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import test.utils.WebElementInteractions;

public class WebLoginPage {
	WebDriver browserDriver;
	private WebElementInteractions webElementInteractions = new WebElementInteractions();

	public WebLoginPage(WebDriver driver){
		browserDriver=driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(id = "user-name")
	WebElement emailInput;

	@FindBy(id="password")
	WebElement passwordInput;
	
	@FindBy(id = "login-button")
	WebElement loginBtn; 
	
	@FindBy(xpath = "//div[@id='shopping_cart_container']/a[1]")
	WebElement cartIcon; 
	
	
	@FindBy(css = "#userTable tbody tr") 
    List<WebElement> tableBodyRows;




	public void insertEmail(String email)
	{webElementInteractions.sendKeysWebElement(emailInput,email );
		
	}

	public void insertPassword(String password) {
		webElementInteractions.sendKeysWebElement(passwordInput, password);
	}

	public void clickLoginBtn() {
		webElementInteractions.clickWebElement(loginBtn, "loginBtn");	
	}
	

	public void confirmLoggeinSuccessfully(int timeInSec) throws IOException {
		webElementInteractions.waitForElementToDisplay(cartIcon, "cartIcon", timeInSec);
	}

}