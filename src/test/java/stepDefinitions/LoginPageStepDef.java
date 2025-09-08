package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.LoginPageObjects;
import utils.AllureManager;
import utils.TestContextSetup;

import javax.xml.crypto.Data;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class LoginPageStepDef {

    TestContextSetup testContextSetup;

    public LoginPageStepDef(TestContextSetup testContextSetup){
        this.testContextSetup=testContextSetup;
    }

    @Given("user is on login page")
    public void userIsOnLoginPage() {

    }

    @When("^user enters (.+) and (.+) and clicks on Login buttton$")
    public void userEntersAndAndClicksOnLoginButtton(String userName, String password) throws InterruptedException {
        AllureManager.info("User will now enter userName and Password");
        testContextSetup.pageObjectManager.getLoginPageObjects().loginToApplication(userName,password);
        AllureManager.pass("User successfully entered the user name and password.");
        Thread.sleep(30000);

    }
}
