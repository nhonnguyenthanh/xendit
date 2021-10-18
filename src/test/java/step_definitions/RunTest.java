package step_definitions;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/java/features/calculator.feature",
        glue= {"src/test/java/step_definitions"},
        plugin = { "pretty", "html:target/cucumber/cucumber.html", "json:target/cucumber/cucumber.json"},
        monochrome = true)
public class RunTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
