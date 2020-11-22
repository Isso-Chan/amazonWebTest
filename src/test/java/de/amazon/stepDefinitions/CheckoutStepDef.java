package de.amazon.stepDefinitions;

import de.amazon.pages.BasketPage;
import de.amazon.pages.CheckoutPage;
import de.amazon.pages.ProductPage;
import de.amazon.utilities.BrowserUtilities;
import de.amazon.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckoutStepDef {

    /**
     * create logger to log infos, errors etc.
     */
    Logger logger = LoggerFactory.getLogger(ProductPage.class);


    @Given("I signed in my Amazon account")
    public void iSignedInMyAmazonAccount() {
        new CheckoutPage().SignedInMyAmazonAccount();

    }

    @And("I am in basket page")
    public void IAmInBasketPage() {
        new BasketPage().basketButton.click();
    }

    @Then("I see that all requirements about delivery are shown")
    public void ISeeThatAllRequirementsAboutDeliveryAreShown() {
        ISeeCheckoutPage();
        new CheckoutPage().verifyPageItems();
    }

    @Then("I see checkout page")
    public void ISeeCheckoutPage() {
        CheckoutPage checkoutPage=new CheckoutPage();
        checkoutPage.ISeeCheckoutPage();
    }
}
