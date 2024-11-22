package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

// extends base class
public class LoginPage extends BasePage {

//    create page object class
    // create constructor

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    //    webElement Locators
    @FindBy(name = "uid")
    WebElement txtuserId;

    @FindBy(name = "password")
    WebElement txtpassword;

    @FindBy(name = "btnLogin")
    WebElement btnLogin;



    //    Action methods
    public void user(String uid) {
        txtuserId.sendKeys(uid);
    }

    public void password(String pwd) {
        txtpassword.sendKeys(pwd);
    }

    public void loginButton() {
        btnLogin.click();
    }


}
