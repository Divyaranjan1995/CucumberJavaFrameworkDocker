package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ExtentManager;

import java.time.Duration;


public class LoginPageObjects {

    WebDriver driver;

    public LoginPageObjects(WebDriver driver){
        this.driver=driver;
    }

    public By txtBoxUserName = By.name("username");
    public By txtBoxPassword = By.name("password");
    public By btnLogin = By.xpath("//button[@type='submit']");

    public void loginToApplication(String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtBoxPassword));
        ExtentManager.logInfo("User Enters Username and Password");
        driver.findElement(txtBoxUserName).sendKeys(username);
        driver.findElement(txtBoxPassword).sendKeys(password);
        driver.findElement(btnLogin).click();
        ExtentManager.logPass("User successfully Clicks on Login Button");


    }
}
