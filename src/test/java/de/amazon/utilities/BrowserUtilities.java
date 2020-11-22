package de.amazon.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class BrowserUtilities {

    /**
     * Moves the mouse to given element
     *
     * @param element on which to hover
     */
    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.get());
        actions.moveToElement(element).perform();
    }

    /**
     * waits for backgrounds processes on the browser to complete
     *
     * @param timeOutInSeconds
     */
    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(Driver.get(), timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

    /**
     * Clicks on an element using JavaScript
     *
     * @param element
     */
    public static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].click();", element);
    }

    /**
     * Waits for provided element to be clickable
     *
     * @param element
     * @param timeout
     * @return
     */
    public static WebElement waitForClickable(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Performs a pause
     *
     * @param seconds
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for the provided element to be visible on the page
     *
     * @param element
     * @param timeToWaitInSec
     * @return
     */
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), timeToWaitInSec);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * return a list of String from a list of elements
     *
     * @param list of webelements
     * @return list of String
     */
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }

    /**
     * return a list of Integer from a list of elements
     *
     * @param list of webelements
     * @return list of Integer
     * @type Select
     */
    public static List<Integer> getElementsTextAsInteger(List<WebElement> list) {
        List<Integer> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            Select select=new Select(el);
            elemTexts.add(Integer.parseInt(select.getFirstSelectedOption().getText().replace("\" ","").trim()));
        }
        return elemTexts;
    }

    /**
     * sum the product prices in the basket
     * @return
     */
    public static double calculateTotalPriceOfProducts(List<WebElement> elements) {

        double total = 0.00;
        for (WebElement productPrice : elements) {
            total += convert2TwoDecimalsDouble(productPrice.getText());
        }
        return convert2TwoDecimalsDouble(total);
    }

    /**
     * convert the String Price value to double with 2 decimals after point
     * @param text
     * @return
     */
    public static double convert2TwoDecimalsDouble(String text) {
        double value = Double.parseDouble(text.substring(1));
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * convert the double Price value to double with 2 decimals after point
     * @param value
     * @return
     */
    public static double convert2TwoDecimalsDouble(double value) {
        return Math.round(value * 100.0) / 100.0;
    }


    /**
     * get total amount of prices, written at bottom on the page,  in double (2 decimals) format
     * @return
     */
    public static double getSubtotal(WebElement element) {
        return convert2TwoDecimalsDouble(element.getText());
    }

}
