package de.amazon.stepDefinitions;


import de.amazon.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Hooks {
    Logger logger = LoggerFactory.getLogger(Hooks.class);

    /**
     * Before each test, we run this method, so that we setup WebDriver, screen and implicitly wait settings
     */
    @Before
    public void setup() {
        logger.info("\t --- Test started ---");
        // maximize the browser window
        Driver.get().manage().window().maximize();
        // set the implicitly wait time
        Driver.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * After each test, we run this method, so that we close the opened WebDriver and take screenshot, when test is FAIL.
     *
     * @param scenario, to check PASS or FAIL
     */
    @After
    public void tearDown(Scenario scenario) {
        // if test is FAIL, take screenshot
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        }
        // close WebDriver
        Driver.closeDriver();
        logger.info("\t --- Test finished ---");
    }
}
