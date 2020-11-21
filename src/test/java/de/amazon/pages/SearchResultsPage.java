package de.amazon.pages;

import de.amazon.utilities.BrowserUtilities;
import de.amazon.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SearchResultsPage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     * @Param Driver.get()
     */
    //public SearchResultsPage() {
    //    PageFactory.initElements(Driver.get(), this);
    //}
    Logger logger = LoggerFactory.getLogger(SearchResultsPage.class);

    /**
     * Find Page WebElements
     */
    @FindBy(id = "s-result-sort-select")
    public WebElement resultSortSelect;

    @FindBy(xpath = "//a[normalize-space(text()) = 'Undo']")
    public List<WebElement> undo;

    @FindBy(css = ".a-color-state.a-text-bold")
    public WebElement searchedItemName;

    @FindBy(xpath = "//div[@data-component-type=\"s-search-result\"][not(contains(@class,\"AdHolder\"))]")
    public List<WebElement> searchedItemsShownInPage;

    @FindBy(xpath = "//div[@class='a-section a-spacing-small a-spacing-top-small']/span[1]")
    public WebElement searchResultsLineText;

    @FindBy(xpath = "//ul[@class='a-pagination']//li")
    public List<WebElement> otherPagesButtons;


    /**
     * Sort items according to sort option parameter.
     * @Param sort option (e.g. "Price: Low to High")
     */
    public void sortItems(String sortOption, String searchItem) {
        Assert.assertEquals(searchItem, searchedItemName.getText().replace("\"", ""));
        logger.info("Searched item was verified as: {}", searchItem);
        BrowserUtilities.waitForClickable(resultSortSelect,10);
        Select select=new Select(resultSortSelect);
        BrowserUtilities.waitForClickable(select.getOptions().get(1),5);
        select.selectByVisibleText(sortOption);

    }

    /**
     * Sort items according to sort option parameter.
     * Then verify product amount shown in page
     * @Param sort option (e.g. "Price: Low to High")
     */
    public WebElement getCheapestElement(String productName) {

        BrowserUtilities.waitForPageToLoad(5);
        List<WebElement> searchResults = Driver.get().findElements(
                By.xpath("((//div[@data-component-type=\"s-search-result\"][not(contains(@class,\"AdHolder\"))][contains(.//span,\""
                        + productName + "\")])[1]//a)"));

                String itemNumInPage = searchResultsLineText.getText().split(" ")[0];
                if(itemNumInPage.contains("-")){
                    itemNumInPage=itemNumInPage.split("-")[1];
        }

        //product amount shown in the page is verified
        Assert.assertEquals("Verification of product amount in page", Integer.valueOf(searchedItemsShownInPage.size()), Integer.valueOf(itemNumInPage));
        logger.info("Total product amount shown in page is verified as: {}", itemNumInPage);

        //Verification of other pages for products to be shown after searching
        if(searchResultsLineText.getText().contains("of")){
            Assert.assertTrue(otherPagesButtons.size()>0);
            logger.info("Verification of more than one page is done");

        }

        WebElement cheapestElement = searchResults.get(2);
        return cheapestElement;

    }

    /**
     * undo product names' automatically translate of Amazon
     * P.S.: Amazon translates sometimes product names automatically
     */
    public void undoSearchItemTranslate() {
        if (undo.size() != 0) {
            undo.get(0).click();
        }
    }
}
