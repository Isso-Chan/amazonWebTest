package de.amazon.stepDefinitions;

import de.amazon.pages.*;
import de.amazon.utilities.BrowserUtilities;
import de.amazon.utilities.ConfigurationReader;
import de.amazon.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;


public class MiniSmokeStepDefs {

    Logger logger = LoggerFactory.getLogger(BasePage.class);
    String searchItem;

    @Given("I am on the homepage")
    public void i_am_on_the_homepage() {
        // get homepage URL from configuration.properties by ConfigurationReader
        String url = ConfigurationReader.get("url");
        // open the page
        Driver.get().get(url);
        BrowserUtilities.waitForPageToLoad(5);
        Driver.get().manage().deleteAllCookies();
        new Homepage().acceptCookies();
    }

    @Given("The language as {string} is selected")
    public void the_language_as_is_selected(String language) {
        // create a Homepage instance
        Homepage homepage = new Homepage();
        // change language depends on given language option
        homepage.changeLanguage(language);
    }

    @When("Search department as {string} is selected")
    public void search_department_as_is_selected(String department) {
        new Homepage().selectSearchDepartment(department);
    }

    @When("I search the product {string}")
    public void i_search_the_product(String item) {
        Homepage homepage = new Homepage();
        homepage.searchItem(item);
        searchItem=item;
        new SearchResultsPage().undoSearchItemTranslate();
    }

    @When("I sort the products by {string}")
    public void i_sort_the_products_by(String sortOption) {
        SearchResultsPage searchRP = new SearchResultsPage();
        searchRP.sortItems(sortOption, searchItem);
        new SearchResultsPage().undoSearchItemTranslate();
    }

    @When("I add the cheapest {string} to the basket")
    public void i_add_the_cheapest_to_the_basket(String item) {
        SearchResultsPage searchRP = new SearchResultsPage();
        // click with jsExecutor, because some times products does not be at the page view (window)
        // jsExecutor support us that we can click all elements in DOM, not page view (window)
        BrowserUtilities.clickWithJS(searchRP.getCheapestElement(item));
        new ProductPage().addToBasket();
    }

    @When("I navigate to basket page")
    public void i_navigate_to_basket_page() {
        new Homepage().basketButton.click();
    }

    @Then("I see that the basket calculates the result correctly")
    public void i_see_that_the_basket_calculates_the_result_correctly() {
        BasketPage basketPage = new BasketPage();
        double expectedValue = basketPage.calculateTotalPriceOfProducts();
        double actualValue = basketPage.getSubtotal();
        assertEquals(expectedValue, actualValue, 0D);
        logger.info("Correct calculation of basket is verified as {}", actualValue);
    }

    @When("I proceed to checkout")
    public void i_proceed_to_checkout() {
        new BasketPage().proceedToCheckout.click();
        logger.info("Proceed to checkout");
    }

    @Then("I get redirected to the {string} page")
    public void i_get_redirected_to_the_page(String pageName) {
        String actualTitle = Driver.get().getTitle();
        String expectedTitle = pageName;
        assertEquals("Verify that user is on the Amazon Login Page", expectedTitle, actualTitle);
        logger.info("Verified that user is on Login page");

    }


}
