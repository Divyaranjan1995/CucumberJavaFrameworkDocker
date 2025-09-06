package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Map<Long, WebDriver> webDriverMap = new HashMap<>();

    //Initialize the Extent Report
    public synchronized static ExtentReports getReporter(){
        if(extent==null){
            String reportPath = System.getProperty("user.dir") + "/ExtentReports/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("OrangeHRM Report");
            spark.config().setTheme(Theme.DARK);


            extent  = new ExtentReports();

            extent.attachReporter(spark);

            //Adding system information
            extent.setSystemInfo("Operating System",System.getProperty("os.name"));
            extent.setSystemInfo("Java Version",System.getProperty("java.version"));
            extent.setSystemInfo("User Name",System.getProperty("user.name"));
        }

        return extent;
    }

    //Start the test
    public synchronized static ExtentTest startTest(String testName){
        ExtentTest extentTest = getReporter().createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    //End the Test
    public synchronized static void endTest(){
        getReporter().flush();
    }

    //Get Current Thread's Test
    public synchronized static ExtentTest getTest(){
        return test.get();
    }

    //Method to get the name of the current test
    public static String getTestName(){
        ExtentTest currentTest = getTest();
        if(currentTest != null){
            return currentTest.getModel().getName();
        }else {
            return "No test is currently active for this thread";
        }
    }

    //Log info

    public static void logInfo(String logMessage){
        getTest().info(logMessage);
    }

    public static void logPass(String logMessage){
        getTest().pass(logMessage);
    }

    public static void logPassWithScreenShot(String logMessage, String screenShotMessage, WebDriver driver){
        getTest().pass(logMessage);
        attachScreenShotToReport(driver,screenShotMessage);
    }

    public static void logFail(String logMessage, String screenShotMessage, WebDriver driver)
    {
        getTest().fail(logMessage);
        attachScreenShotToReport(driver,screenShotMessage);
    }

    public static void logSkip(String logMessage){
        getTest().skip(logMessage);
    }

    public static String takeScreenShot(String screenShotMessage, WebDriver driver){
        TakesScreenshot ts = (TakesScreenshot) driver;
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String screenShotPath = System.getProperty("user.dir")+"/ExtentReports/ScreenShots/"+screenShotMessage+"_"+timeStamp+".png";
        File targetFile = new File(screenShotPath);
        //sourceFile.renameTo(targetFile);
        try {
            FileUtils.copyFile(sourceFile,targetFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return convertToBase64(sourceFile);

    }

    //Convert Screenshot to Base64 format
    public static String convertToBase64(File screenShotFile) {
        String base64Format = "";
        byte[] fileContent;
        try {
            fileContent = FileUtils.readFileToByteArray(screenShotFile);
            base64Format = Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return base64Format;
    }

    //Attach ScreenShot to report using Base64
    public synchronized static void attachScreenShotToReport(WebDriver driver, String message){

        try{
            String screenShotBase64 = takeScreenShot(getTestName(),driver);
            getTest().info(message,com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotBase64).build());
        }catch (Exception e){
            getTest().fail("Failed to attach screenshot");
            e.printStackTrace();
        }

    }

    //Register webDriver for Current Thread
    public static void registerDriver(WebDriver driver){
        webDriverMap.put(Thread.currentThread().getId(),driver);
    }
}
