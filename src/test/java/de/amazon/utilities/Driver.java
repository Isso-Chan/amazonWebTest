package de.amazon.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class Driver {
    /**
     * create logger to log infos, errors etc.
     */
    Logger logger = LoggerFactory.getLogger(Driver.class);

    /**
     * Singleton pattern, constructor is private
     */
    private Driver() {
    }

    /**
     * InheritableThreadLocal  --> this is like a container, bag, pool.
     * in this pool we can have separate objects for each thread
     * for each thread, in InheritableThreadLocal we can have separate object for that thread
     * driver class will provide separate webdriver object per thread
     */
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    public static WebDriver get() {
        Logger logger = LoggerFactory.getLogger(Driver.class);

        //if this thread doesn't have driver - create it and add to pool
        if (driverPool.get() == null) {

            // if we pass the driver from terminal then use that one
            // if we do not pass the driver from terminal then use the properties file
            String browser = System.getProperty("browser") != null ? browser = System.getProperty("browser") :
                    // this line tells which browser should open based on the value from properties file
                    ConfigurationReader.get("browser");

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver());
                    break;
                case "chrome-headless":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver(new ChromeOptions().setHeadless(true)));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    break;
                case "firefox-headless":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver(new FirefoxOptions().setHeadless(true)));
                    break;
                case "edge":
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Edge");
                    WebDriverManager.edgedriver().setup();
                    driverPool.set(new EdgeDriver());
                    break;
                case "safari":
                    if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                        throw new WebDriverException("Your OS doesn't support Safari");
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driverPool.set(new SafariDriver());
                    break;
                case "remote_chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    String hubURL = "Selenium grid hub url";
                    try {
                        logger.info("************* HUB is tried *************");
                        driverPool.set(new RemoteWebDriver(new URL(hubURL), chromeOptions));
                        logger.info("************* HUB is worked *************");
                    } catch (MalformedURLException e) {
                        throw new WebDriverException("could not be connected to the remote machine");
                    }
                    break;
                default:
                    throw new WebDriverException("\"browser\" in the configuration properties file is not valid");
            }
        }
        return driverPool.get();
    }

    /**
     * close all windows and remove WebDriver from our driver pool
     */
    public static void closeDriver() {
        driverPool.get().quit();
        driverPool.remove();
    }
}