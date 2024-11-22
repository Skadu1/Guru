package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BankPage extends BasePage{

    //constructor
    public BankPage(WebDriver driver)
    {
        super(driver);
    }


    //Element Locators

    @FindBy(xpath = "//a[text()='Log out']")
    WebElement btnLogOut;

    @FindBy(xpath = "//marquee[contains(text(),\"Welcome To Manager's \")]")
    WebElement confMessage;


    //Action methods
    public void logOut()
    {
        JavascriptExecutor jse= (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0, 250);");
        btnLogOut.click();
    }

    public String getConfirmationMessage()
    {
        try {
            return(confMessage.getText());
        } catch (Exception e) {
            return(e.getMessage());
        }

    }

    public boolean ifAccountExists()
    {
        try{
            return(confMessage.isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }
}
