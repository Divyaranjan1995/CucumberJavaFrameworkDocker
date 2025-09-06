package utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.ByteArrayInputStream;

/**
 * AllureManager - Utility class to manage Allure Reporting
 * Similar to ExtentReports usage (info, pass, fail, attachScreenshot, logs)
 */
public class AllureManager {

    private AllureManager() {
        // private constructor to prevent instantiation
    }

    /**
     * Log informational step
     */
    public static void info(String message) {
        Allure.step(message, Status.BROKEN); // BROKEN used to show log; doesn't affect pass/fail
    }

    /**
     * Log passed step
     */
    public static void pass(String message) {
        Allure.step(message, Status.PASSED);
    }

    /**
     * Log failed step
     */
    public static void fail(String message) {
        Allure.step(message, Status.FAILED);
        Assert.fail(message);
    }

    /**
     * Attach plain text log
     */
    public static void attachLog(String name, String content) {
        Allure.addAttachment(name, "text/plain", content);
    }

    //Attach exception message

    public static void logException(String message){
        Allure.step(message,Status.FAILED);
    }

    /**
     * Attach screenshot
     */
    public static void attachScreenshot(WebDriver driver, String screenshotName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            Allure.step("Failed to capture screenshot: " + e.getMessage(), Status.BROKEN);
        }
    }
}
