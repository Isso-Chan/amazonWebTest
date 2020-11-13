package de.amazon.pages;

import de.amazon.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BasketPage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */
    public BasketPage() {
        PageFactory.initElements(Driver.get(), this);
    }

    /**
     * Find Page WebElements
     */
    @FindBy(className = "sc-product-price")
    public List<WebElement> productPrices;

    @FindBy(css = "#sc-subtotal-amount-activecart > span")
    public WebElement subtotal;

    @FindBy(css = "#sc-buy-box-ptc-button input")
    public WebElement proceedToCheckout;


    /**
     * sum the product prices in the basket
     *
     * @return
     */
    public double calculateTotalPriceOfProducts() {

        double total = 0.00;
        for (WebElement productPrice : productPrices) {
            total += convert2TwoDecimalsDouble(productPrice.getText());
        }
        return convert2TwoDecimalsDouble(total);
    }

    /**
     * convert the String Price value to double with 2 decimals after point
     *
     * @param text
     * @return
     */
    public double convert2TwoDecimalsDouble(String text) {
        double value = Double.parseDouble(text.substring(1));
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * convert the double Price value to double with 2 decimals after point
     *
     * @param value
     * @return
     */
    public double convert2TwoDecimalsDouble(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * get total amount of prices, written at bottom on the page,  in double (2 decimals) format
     *
     * @return
     */
    public double getSubtotal() {
        return convert2TwoDecimalsDouble(subtotal.getText());
    }
}
