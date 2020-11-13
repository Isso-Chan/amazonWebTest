package de.amazon.pages;

import de.amazon.utilities.Driver;
import org.openqa.selenium.support.PageFactory;

public class Homepage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */
    public Homepage() {
        PageFactory.initElements(Driver.get(), this);
    }


}
