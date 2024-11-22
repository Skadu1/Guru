package testCases;


import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {
//    public static WebDriver driver;
    public  WebDriver driver;
    public Logger logger;
    public Properties p;
    public Alert al;

    @BeforeClass(groups = {"sanity","regression"})
    @Parameters ({"os","browser"})
    public void setUp(String os, String br) throws IOException {
        FileReader file= new FileReader("./src/test/resources/config.properties");
        p=new Properties();
        p.load(file);
        logger=LogManager.getLogger(this.getClass()); //log4j2

        if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
        {
            DesiredCapabilities dc= new DesiredCapabilities();
            //os
 if(os.equalsIgnoreCase("windows"))
 {
     dc.setPlatform(Platform.WIN10);
 }
 else if(os.equalsIgnoreCase("mac"))
 {
     dc.setPlatform(Platform.MAC);
 }
 else
 {
     System.out.println("No matching OS");
 }
            //browser

            switch(br.toLowerCase())
            {
                case "chrome": dc.setBrowserName("chrome"); break;
                case "edge": dc.setBrowserName("MicrosoftEdge"); break;
                case "firefox": dc.setBrowserName("firefox");break;
                default:System.out.println("No matching browser");return;
            }
       driver= new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),dc);
        }

        if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
            switch (br.toLowerCase()) {
                case "chrome":
                    System.setProperty("webdriver.chrome.driver",
                            System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    new EdgeDriver();
                    break;
                case "firefox":
                    new FirefoxDriver();
                    break;
                default:
                    System.out.print("Invalid browser");
                    return;
            }
        }
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.get(p.getProperty("appURL"));
    }

    @AfterClass(groups = {"sanity","regression"})
    public void tearDown()
    {
        driver.close();
    }

    public void alert()
    {
        al=driver.switchTo().alert();
         al.accept();
    }

    public String captureScreenshot(String tname) throws IOException {
        //time stamp
        String timeStamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        TakesScreenshot srcshot=(TakesScreenshot) driver;
        File sourcefile=srcshot.getScreenshotAs(OutputType.FILE);
        String filepath=System.getProperty("user.dir")+"\\screenshots\\"+tname+"_"+timeStamp+".png";
        File destfile= new File(filepath);
//        sourcefile.renameTo(destfile);
        FileUtils.copyFile(sourcefile, destfile);
        return filepath;
    }
   /* public String randomString()
    {
//       this is to generate a random String
        String generatedString= RandomStringUtils.randomAlphabetic(5);

//       this is to generate a random number
        Random random = new Random();
        int randomNumber = random.nextInt(1000); // Generates a number between 0 and 999

        return generatedString;
    }

    public String alphaNumeric()
    {
        String alpha= RandomStringUtils.randomAlphanumeric(6);
        return alpha;
    } */

    /* public String faker()
    {
        // Create a Faker instance
        Faker faker = new Faker();

        // Generate random user data
        String randomUsername = faker.name().username();
        String randomPassword = faker.internet().password();
        String randomEmail = faker.internet().emailAddress();
        // Generate a random positive number with 6 digits
        long randomPositiveNumber = faker.number().randomNumber(6, false);  // 6 digits
        return randomUsername;
    } */

}
