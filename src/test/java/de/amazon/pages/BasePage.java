package de.amazon.pages;

import de.amazon.utilities.BrowserUtilities;
import de.amazon.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */
    public BasePage() {
        PageFactory.initElements(Driver.get(), this);
    }

    /**
     * create logger to log infos, errors etc.
     */
    Logger logger = LoggerFactory.getLogger(BasePage.class);

    /**
     * Find Page WebElements
     */
    @FindBy(id = "icp-nav-flyout")
    public WebElement languageBar;

    @FindBy(id = "searchDropdownBox")
    public WebElement searchDepartmentBox;

    @FindBy(id = "twotabsearchtextbox")
    public WebElement searchBox;

    @FindBy(id = "nav-cart-count-container")
    public WebElement basketButton;

    /**
     * change language
     * @param language
     */
    public void changeLanguage(String language) {
        BrowserUtilities.hover(languageBar);
        WebElement selectedLanguage = Driver.get().findElement(By.xpath("//div[@id='nav-flyout-icp']//span[.='" + language + "']"));
        BrowserUtilities.waitForClickable(selectedLanguage,5).click();

        BrowserUtilities.hover(languageBar);
        List<WebElement> languages = Driver.get().findElements(By.cssSelector("#nav-flyout-icp a[href^='#switch-lang']"));
        BrowserUtilities.waitForPageToLoad(5);
        Assert.assertFalse(languages.contains(selectedLanguage));
        logger.info("Language is verified as: {}", language);

    }

    /**
     * type a product name to search bar and search it
     * @param item
     */
    public void searchItem(String item) {
        searchBox.sendKeys(item);
        Assert.assertEquals(item, searchBox.getAttribute("value"));
        logger.info("Item name send to searchbox is verified as: {}", item);
        searchBox.sendKeys(Keys.ENTER);
    }

    /**
     * accept cookies, when it is shown
     */
    public void acceptCookies() {
        try {
            List<WebElement> cookies = Driver.get().findElements(By.id("sp-cc-accept"));
            BrowserUtilities.waitForClickable(cookies.get(0),10).click();
            logger.info("Cookies were accepted");
        } catch (NoSuchElementException ignored) {
        }
    }

    /**
     * select search department
     * @param department
     */
    public void selectSearchDepartment(String department) {
        searchDepartmentBox.click();
        WebElement departmentsButton = Driver.get().findElement(By.xpath("//select[@class='nav-search-dropdown searchSelect']"));
        Select select=new Select(departmentsButton);
        select.selectByVisibleText(department);
        Assert.assertEquals("Department verification", select.getFirstSelectedOption().getText(),department);
        logger.info("Department is verified as: {}", department);
    }
}
