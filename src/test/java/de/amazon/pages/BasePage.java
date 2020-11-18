package de.amazon.pages;

import de.amazon.utilities.BrowserUtilities;
import de.amazon.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @FindBy(id = "nav-cart")
    public WebElement basketButton;

    /**
     * change language
     *
     * @param language
     */
    public void changeLanguage(String language) {
        BrowserUtilities.hover(languageBar);
        //Driver.get().findElement(By.cssSelector("#nav-flyout-icp a[href^=\"#switch-lang\"]"))
                //.findElement(By.xpath("//span[text()=\"" + language + "\"]")).click();
        Driver.get().findElement(By.xpath("//div[@id='nav-flyout-icp']//span[.='"+language+"']")).click();

    }

    /**
     * type a product name to search bar and search it
     *
     * @param item
     */
    public void searchItem(String item) {
        searchBox.sendKeys(item, Keys.ENTER);
    }

    /**
     * accept cookies, when it is shown
     */
    public void acceptCookies() {
        try {
            Driver.get().findElement(By.id("sp-cc-accept")).click();
            logger.info("Cookies were accepted");
        } catch (NoSuchElementException e) {
        }
    }

    /**
     * select search department
     *
     * @param department
     */
    public void selectSearchDepartment(String department) {
        searchDepartmentBox.click();
        BrowserUtilities.waitForClickable(Driver.get().findElement(By.xpath("//option[text()=\"" + department + "\"]")), 5).click();
    }
}
