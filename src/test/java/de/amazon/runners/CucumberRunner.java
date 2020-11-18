package de.amazon.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = false,
        plugin = {"json:target/cucumber.json",
                "html:target/default-html-reports",
                "rerun:target/rerun.txt"
        },
        features = "src/test/resources/features/",
        glue = "de/amazon/stepDefinitions",
        // true, to skip undefined steps from execution
        strict = true,
        // to check whether all feature file steps have corresponding step definitions
        dryRun = false, // to add new test scenario methods, turn this to true
        tags = "@all"
)
class CucumberRunner {
}

