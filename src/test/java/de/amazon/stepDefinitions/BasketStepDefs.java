package de.amazon.stepDefinitions;

import de.amazon.pages.BasketPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasketStepDefs {

    Logger logger = LoggerFactory.getLogger(BasketPage.class);


    @When("I remove an item from basket")
    public void IRemoveAnItemFromBasket() {
        BasketPage basketPage=new BasketPage();
        basketPage.removeAnItemFromBasket();
    }

    @Then("I see that the amount of items in basket changes")
    public void ISeeThatTheAmountOfItemsInBasketChanges() {
        new BasketPage().SeeThatTheAmountOfItemsInBasketChanges();
    }

    @When("I change the amount of any item in basket")
    public void IChangeTheAmountOfAnyItemInBasket() {
        new BasketPage().ChangeTheAmountOfAnyItemInBasket();
    }
}
