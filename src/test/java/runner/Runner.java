package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = "./src/test/resources/features",
		snippets = SnippetType.CAMELCASE,
		monochrome = true,
		dryRun = false,
        plugin = {
            "pretty",
            "junit:results.xml",
            "com.hpe.alm.octane.OctaneGherkinFormatter:gherkin-results/OctaneGherkinResults.xml"
        }
)
public class Runner {}
