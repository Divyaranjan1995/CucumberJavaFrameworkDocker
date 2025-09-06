package runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "@target/failedScenarios.txt",
        glue = "stepDefinitions",
        monochrome = true,
        dryRun = false,
        plugin = { "pretty",
                "html:target/cucumber.html",
                "json:target/cucumber.json"

        }
)
public class ReRunRunner extends AbstractTestNGCucumberTests {

        @Override
        @DataProvider(parallel = true)
        public Object[][] scenarios(){
                return  super.scenarios();
        }
}
