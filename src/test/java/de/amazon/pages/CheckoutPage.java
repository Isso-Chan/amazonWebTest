package de.amazon.pages;

import de.amazon.stepDefinitions.MiniSmokeStepDefs;
import de.amazon.utilities.BrowserUtilities;
import de.amazon.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CheckoutPage extends BasePage {

    /**
     * create logger to log infos, errors etc.
     */
    Logger logger = LoggerFactory.getLogger(ProductPage.class);

    @FindBy(xpath = "//h1")
    public WebElement checkoutHeading;

    @FindBy(xpath = "//*[@aria-label='Delivery address']")
    public WebElement deliveryAddressRow;

    @FindBy(xpath = "//*[@aria-label='Delivery address']/div[1]")
    public WebElement myDeliveryAddress;

    @FindBy(xpath = "//*[@aria-label='Payment method']")
    public WebElement paymentMethodRow;

    @FindBy(xpath = "//*[@aria-label='Payment method']//span[@id='credit-card-name']")
    public WebElement paymentWithCreditcard;

    @FindBy(xpath = "//div[@id='spc-orders']//div[@class='a-box-inner']")
    public List<WebElement> productTypes;

    @FindBy(xpath = "//div[@id='spc-orders']//div[@class='a-box-inner']//select")
    public List<WebElement> productQuantities;

    @FindBy(xpath = "//h1//span[@class='a-color-link clickable-heading']")
    public WebElement totalItemInHeading;

    @FindBy(xpath = "//div[@id='spc-orders']//div[@class='a-box-inner']//span[@class='a-color-price']")
    public List<WebElement> productPrices;

    @FindBy(xpath = "//table[@id='subtotals-marketplace-table']//td[@class='a-text-right aok-nowrap a-nowrap']")
    public List<WebElement> totalPrice;

    //verify the checkout page
    public void ISeeCheckoutPage() {
        Driver.get().navigate().refresh();
        BrowserUtilities.waitForClickable(checkoutHeading,10);
        String heading=checkoutHeading.getText().split(" ")[0].trim();
        Assert.assertEquals("Verification of page", heading, "Checkout");
        logger.info("Checkout page is verified");
    }

    public void SignedInMyAmazonAccount() {
        new MiniSmokeStepDefs().i_am_on_the_homepage();
        RegistrationPage registrationPage=new RegistrationPage();
        registrationPage.signIn.click();
        registrationPage.usernameEntry("username");
        registrationPage.passwordEntry("password");

    }

    public void verifyPageItems() {
        Assert.assertTrue("Delivery address row verification",deliveryAddressRow.isDisplayed());
        logger.info("Deliver address row is verified");
        Assert.assertTrue("Payment method row verification", paymentMethodRow.isDisplayed());
        logger.info("Payment method row is verified");
        Assert.assertTrue("Creditcard payment verification", paymentWithCreditcard.isDisplayed());
        logger.info("Payment with creditcard is verified");

        //Verify the quantities of products
        List<Integer> productQuantities = BrowserUtilities.getElementsTextAsInteger(this.productQuantities);
        Integer productSum=0;
        for (Integer quantity: productQuantities) {
            productSum+=quantity;
        }
        Assert.assertEquals(productSum.toString()+" items", totalItemInHeading.getText());
        logger.info("Total item amount is verified");

        //Verify total cost
        List<String> pPrices = BrowserUtilities.getElementsText(productPrices);
        double calculatedPrice = BrowserUtilities.calculateTotalPriceOfProducts(productPrices);
        double subtotal = BrowserUtilities.getSubtotal(totalPrice.get(2));

        Assert.assertEquals("Total price verification",subtotal, calculatedPrice, 0D);
        logger.info("Total price is verified");

    }
}
