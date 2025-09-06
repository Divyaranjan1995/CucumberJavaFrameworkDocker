package runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/",
        glue = "stepDefinitions",
        monochrome = true,
        dryRun = false,
        plugin = { "pretty",
                "html:target/cucumber.html",
                "json:target/cucumber.json",
                "rerun:target/failedScenarios.txt",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"

        },
        tags ="@Orange"
)
public class RunnerTest extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios(){
                return  super.scenarios();
        }
}
