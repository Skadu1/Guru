package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BankPage;
import pageObjects.LoginPage;

public class LoginTest extends BaseClass {



    @Test(groups={"sanity"})
    public void login() throws InterruptedException {
        try {
            logger.info("*******  Starting TC001_LoginTest  ******* ");
            // calling login page
            LoginPage Lp = new LoginPage(driver);

            Lp.user(p.getProperty("UserID"));
            logger.info("Enters username......");
            Thread.sleep(3000);

            Lp.password(p.getProperty("Password"));
            logger.info("Enters password......");
            Thread.sleep(3000);

            Lp.loginButton();
            logger.info("clicks login button......");
            Thread.sleep(3000);

            //calling bank page
            BankPage bp= new BankPage(driver);
           boolean accountExist= bp.ifAccountExists();
           Assert.assertTrue(accountExist);
            logger.info("validates confirmation message......");
            String confmsg = bp.getConfirmationMessage();
            Assert.assertEquals(confmsg, "Welcome To Manager's Page of Guru99 Bank");

            bp.logOut();
            alert();
            Thread.sleep(2000);


        } catch (Exception e) {
//            logger.error("test case failed");
//            logger.debug("debug logs...");
            Assert.fail();

        }
        logger.info("*******  Ending TC001_LoginTest  ******* ");

    }
}
