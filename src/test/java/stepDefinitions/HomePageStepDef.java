package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageObjects.HomePageObjects;
import utils.AllureManager;
import utils.ExtentManager;
import utils.TestContextSetup;

public class HomePageStepDef {

    TestContextSetup testContextSetup;

    public HomePageStepDef(TestContextSetup testContextSetup){
        this.testContextSetup=testContextSetup;
    }


    @And("user validates the Home Page Title")
    public void userValidatesTheHomePageTitle() {
        String expectedTitle = "OrangeHRM";
        String actualTitle = testContextSetup.testBase.getDriver().getTitle();
        if(expectedTitle.equals(actualTitle)){
            ExtentManager.logPassWithScreenShot("Actual and Expected Title got matched","HomePageScreenShot",testContextSetup.testBase.getDriver());
        }else {
            ExtentManager.logFail("Actual Title" + " : "+actualTitle+" , "+"Expected Title" + " : "+expectedTitle,"HomePageScreenShot",testContextSetup.testBase.getDriver());
        }
    }

    @Then("^user validates the Homapage contains (.+)$")
    public void userValidatesTheHomapageContains(String tab) {
        try{
            WebElement tabElement = testContextSetup.pageObjectManager.getHomePageObjects().getTabElement(tab);
            if(tabElement.isDisplayed()){
                ExtentManager.logPassWithScreenShot(tab+" is Displayed","TabScreenShot",testContextSetup.testBase.getDriver());
                AllureManager.pass(tab+" is Displayed");
                AllureManager.attachScreenshot(testContextSetup.testBase.getDriver(),"Tab Is Displayed");
            }
        }catch (Exception e){
            ExtentManager.logFail(tab+" is not Displayed","TabScreenShot",testContextSetup.testBase.getDriver());
            ExtentManager.logInfo(e.getMessage());
            AllureManager.logException(e.getMessage());
            AllureManager.attachScreenshot(testContextSetup.testBase.getDriver(),"Tab Is not Displayed");
            AllureManager.fail(tab+" is not Displayed");
        }

        testContextSetup.genericUtils.switchToChildWindow();
    }
}
