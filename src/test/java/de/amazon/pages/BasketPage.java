package de.amazon.pages;

import de.amazon.utilities.BrowserUtilities;
import de.amazon.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

public class BasketPage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     * @Param Driver.get()
     */
   // public BasketPage() {
   //     PageFactory.initElements(Driver.get(), this);
   // }
    Logger logger = LoggerFactory.getLogger(BasketPage.class);
    static int beforeChange, afterChange, quantityOfChange, selectedItemNu;

    /**
     * Find Page WebElements
     */
    @FindBy(xpath = "//div[@data-name='Active Items']/div//p[1]/span")
    public List<WebElement> productPrices;

    @FindBy(css = "#sc-subtotal-amount-activecart > span")
    public WebElement subtotal;

    @FindBy(id = "sc-subtotal-label-activecart")
    public WebElement itemsInBasket;

    @FindBy(css = ".sc-action-quantity select")
    public List<WebElement> quantitiesOfItems;

    @FindBy(xpath = "//input[@data-action='delete']")
    public List<WebElement> deleteButtons;

    @FindBy(css = "#sc-buy-box-ptc-button input")
    public WebElement proceedToCheckout;

    @FindBy(xpath = "//h1")
    public WebElement basketInfo;



    /**
     * sum the product prices in the basket
     * @return
     */
    /*
    public double calculateTotalPriceOfProducts() {

        double total = 0.00;
        for (WebElement productPrice : productPrices) {
            total += convert2TwoDecimalsDouble(productPrice.getText());
        }
        return convert2TwoDecimalsDouble(total);
    }*/

    /**
     * convert the String Price value to double with 2 decimals after point
     * @param text
     * @return
     */
    /*
    public double convert2TwoDecimalsDouble(String text) {
        double value = Double.parseDouble(text.substring(1));
        return Math.round(value * 100.0) / 100.0;
    }*/

    /**
     * convert the double Price value to double with 2 decimals after point
     * @param value
     * @return
     */
    /*
    public double convert2TwoDecimalsDouble(double value) {
        return Math.round(value * 100.0) / 100.0;
    }*/

    /**
     * get total amount of prices, written at bottom on the page,  in double (2 decimals) format
     * @return
     */
    /*
    public double getSubtotal() {
        return convert2TwoDecimalsDouble(subtotal.getText());
    }
    */

    /*
     * Removes an item from basket and verify it
     */
    public void removeAnItemFromBasket() {
        basketButton.click();
        beforeChange=getItemSubTotalInBasket();
        selectedItemNu=(int)(Math.random() * (deleteButtons.size()-1)) ;
        if (beforeChange!=0){ //If the basket is not empty
            quantityOfChange=-getQuantityOfSelectedItem();
            deleteButtons.get(selectedItemNu).click();
            afterChange=getItemSubTotalInBasket();
        }else{
            quantityOfChange=0;
            afterChange=0;
        }

    }

    //get the amount from subtotal
    public int getItemSubTotalInBasket(){
        int number=0;
        String basket=basketInfo.getText().trim();
        //System.out.println("basket = " + basket);

        if(basket.equals("Added to Basket") || basket.equals("Shopping Basket")){
            number=Integer.parseInt(itemsInBasket.getText().split(" ")[1].trim().substring(1));

        }else{
            System.out.println("Your basket is emty!");
        }
        return number;
    }


    public void selectAnAmountFromSelectedItem(){
        Select select=new Select(quantitiesOfItems.get(selectedItemNu));
        int previousAmount = getQuantityOfSelectedItem();
        System.out.println("previousAmount = " + previousAmount);
        int sQuantity=0;
        do {
            sQuantity= (int) (Math.random() * select.getOptions().size()-1);
        }while(sQuantity==previousAmount);

        System.out.println("selectedItemQuantity = " + sQuantity);
        select.selectByIndex(sQuantity);
        quantityOfChange=sQuantity-previousAmount;
        afterChange=getItemSubTotalInBasket();
        System.out.println("quantityOfChange = " + quantityOfChange);
        Driver.get().navigate().refresh();

    }


    public int getQuantityOfSelectedItem(){
        Select slc=new Select(quantitiesOfItems.get(selectedItemNu));
        return Integer.parseInt(slc.getFirstSelectedOption().getText().replaceAll("(.[\\w ].) ","").trim());
    }


    public void SeeThatTheAmountOfItemsInBasketChanges() {
        System.out.println("beforeChange in SeeThatTheAmountOfItemsInBasketChanges= " + beforeChange);
        System.out.println("afterChange in SeeThatTheAmountOfItemsInBasketChanges= " + afterChange);
        System.out.println("quantityOfChange = " + quantityOfChange);
        Assert.assertEquals("Item amount change verification", quantityOfChange, (afterChange-beforeChange));
        logger.info("Item amount change is verified ");
    }

    public void ChangeTheAmountOfAnyItemInBasket() {
        basketButton.click();
        //gets the amount of items is basket from subtotal
        beforeChange=getItemSubTotalInBasket();
        System.out.println("beforeChange = " + beforeChange);

        //selects one item randomly
        selectedItemNu=(int)(Math.random() * deleteButtons.size()-1) ;
        System.out.println("selectedItemNu = " + selectedItemNu);
        //gets the quantity of selected item
        quantityOfChange=getQuantityOfSelectedItem();
        selectAnAmountFromSelectedItem();
        afterChange=getItemSubTotalInBasket();
        System.out.println("afterChange = " + afterChange);

    }
}
