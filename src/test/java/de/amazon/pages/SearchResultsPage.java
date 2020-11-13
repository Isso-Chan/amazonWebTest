package de.amazon.pages;

import de.amazon.utilities.BrowserUtilities;
import de.amazon.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class SearchResultsPage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */
    public SearchResultsPage() {
        PageFactory.initElements(Driver.get(), this);
    }

    /**
     * Find Page WebElements
     */
    @FindBy(id = "s-result-sort-select")
    public WebElement resultSortSelect;

    @FindBy(xpath = "//a[normalize-space(text()) = 'Undo']")
    public List<WebElement> undo;

    /**
     * Sort items according to sort option parameter.
     *
     * @Param sort option (e.g. "Price: Low to High")
     */
    public void sortItems(String sortOption) {
        resultSortSelect.findElement(By.xpath("//option[text()=\"" + sortOption + "\"]")).click();
    }

    /**
     * Sort items according to sort option parameter.
     *
     * @Param sort option (e.g. "Price: Low to High")
     */
    public WebElement getCheapestElement(String productName) {

        BrowserUtilities.waitForPageToLoad(5);
        WebElement cheapestElement = Driver.get().findElement(
                By.xpath("((//div[@data-component-type=\"s-search-result\"][not(contains(@class,\"AdHolder\"))][contains(.//span,\""
                        + productName + "\")])[1]//a)[2]"));
        return cheapestElement;

    }

    /**
     * undo product names' automatically translate of Amazon
     *
     * P.S.: Amazon translates sometimes product names automatically
     */
    public void undoSearchItemTranslate() {
        if (undo.size() != 0) {
            undo.get(0).click();
        }
    }
}
