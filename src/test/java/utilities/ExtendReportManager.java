package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import testCases.BaseClass;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtendReportManager implements ITestListener
{
    public ExtentReports extent;
    public ExtentTest test;
    public ExtentSparkReporter sr;
    String reportName;
//    BaseClass bc;

    @Override
    public void onStart(ITestContext context) {
       /* SimpleDateFormat df= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date dt= new Date();
        String currentdatetimestamp=df.format(dt); */

        //time stamp
        String timeStamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        // report name format in current timestamp
        reportName = "test-report-"+timeStamp+".html";

        // Create an instance of ExtentSparkReporter and specify the path where the report will be generated
        sr = new ExtentSparkReporter(".\\reports\\"+reportName);

        // Configure the SparkReporter settings
        sr.config().setDocumentTitle("Test Automation Report");
        sr.config().setReportName("Functional Test Report");
        sr.config().setTheme(Theme.DARK);  // Optional: Dark theme

        // Create an instance of ExtentReports and attach the SparkReporter
        extent = new ExtentReports();
        extent.attachReporter(sr);

        extent.setSystemInfo("Computer Name","LocalHost");
        extent.setSystemInfo("Application Name","Guru");
        extent.setSystemInfo("Environment","QA");
        extent.setSystemInfo("Tester Name",System.getProperty("user.name"));

        String os=context.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("os",os);

        String browser=context.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser Name",browser);

        List<String> includedGroups=context.getCurrentXmlTest().getIncludedGroups();
        if(!includedGroups.isEmpty())
        {
            extent.setSystemInfo("groups",includedGroups.toString());
        }

    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test in the report when a test starts
        test = extent.createTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());// to display groups in the report
        // Log success if the test passes
        test.pass("Test passed successfully.");
        test.log(Status.PASS,"Test case passed is "+result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test=extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());// to display groups in the report
        // Log failure and add screenshot if needed
        test.fail("test failed");
        test.log(Status.FAIL,"Test case failed is "+result.getName());
        test.fail(result.getThrowable());
try {
    //BaseClass bc = new BaseClass();
//    bc=new BaseClass();
    String imgPath = new BaseClass().captureScreenshot(result.getName());
    test.addScreenCaptureFromPath(imgPath);
} catch (Exception e)
{
    e.printStackTrace();
}

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test=extent.createTest(result.getTestClass().getName());
        // Log skipped tests
        test.skip("Test skipped.");
        test.log(Status.SKIP,"Test skipped is "+result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the extent report at the end of the test run
        extent.flush();
        String pathofextendreport=System.getProperty("user.dir")+"\\reports\\"+reportName;
        File extendReport=new File(pathofextendreport);
       try
       {
           Desktop.getDesktop().browse(extendReport.toURI());
       }
       catch (Exception e) {
           e.printStackTrace();
       }

    }

}
