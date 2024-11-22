package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BankPage;
import pageObjects.LoginPage;
import utilities.DataProviders;

public class DataDrivenLoginTest extends BaseClass
{

    @Test(dataProvider="dp1",dataProviderClass = DataProviders.class,groups = {"sanity"})
    public void VerifyLogin (String username, String pwd, String exp)
    {
        try {
            logger.info("*******  Starting TC001_LoginTest  ******* ");
            // calling login page
            LoginPage Lp = new LoginPage(driver);

            Lp.user(username);
            logger.info("Enters username......");
            Thread.sleep(3000);

            Lp.password(pwd);
            logger.info("Enters password......");
            Thread.sleep(3000);

            Lp.loginButton();
            logger.info("clicks login button......");
            Thread.sleep(3000);

            //calling bank page
            BankPage bp= new BankPage(driver);
            boolean accountExist= bp.ifAccountExists();
//            Assert.assertTrue(accountExist);

            /*
Valid data--- login successful ---test pass --log out
             login failed ----test fail

 Invalid data--login successful ---test fail --log out
               login failed---test pass
 */

            if(exp.equalsIgnoreCase("valid"))
            {
               if (accountExist==true)
               {
                   bp.logOut();
                Assert.assertTrue(true);
                   logger.info("login Successful......");
                   alert();
                Thread.sleep(2000);
               }
               else
               {
                   logger.info("login failed......");
                   alert();
                   Assert.assertTrue(false);
               }
            }


            if(exp.equalsIgnoreCase("invalid"))
            {
                if(accountExist==true)
                {
                    bp.logOut();
                    Assert.assertTrue(false);
                    alert();
                    Thread.sleep(2000);
                }
                else
                {
                    logger.info("Invalid Data......");
                    alert();
                    Assert.assertTrue(true);

                }
            }


        } catch (Exception e) {
//            logger.error("test case failed");
//            logger.debug("debug logs...");
            Assert.fail();

        }
        logger.info("*******  Ending Data Driven LoginTest  ******* ");

    }
    }

