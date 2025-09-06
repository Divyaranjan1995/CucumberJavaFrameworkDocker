package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ExtentManager;
import utils.TestContextSetup;

import java.io.IOException;
import java.lang.reflect.Executable;

public class Hooks {

    TestContextSetup testContextSetup;

    public  Hooks(TestContextSetup testContextSetup){
        this.testContextSetup=testContextSetup;
    }

    @Before
    public void setup(Scenario scenario){
        ExtentManager.getReporter();
        ExtentManager.startTest(scenario.getName());

    }

    @After
    public void tearDown() throws IOException {
        testContextSetup.testBase.quitDriver();
        ExtentManager.endTest();
    }
}
