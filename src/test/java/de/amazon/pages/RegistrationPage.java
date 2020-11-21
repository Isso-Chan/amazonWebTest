package de.amazon.pages;

import de.amazon.utilities.ConfigurationReader;
import de.amazon.utilities.Driver;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationPage extends BasePage {

    /**
     * PageFactory design pattern, so the page WebElements are assigned automatically, when it opened.
     *
     * @Param Driver.get()
     */
    //public RegistrationPage() {
     //   PageFactory.initElements(Driver.get(), this);
   // }

    Logger logger = LoggerFactory.getLogger(RegistrationPage.class);

    /**
     * Find Page WebElements
     */

    @FindBy(id = "ap_email")
    public WebElement signInUsername;

    @FindBy(id = "ap_password")
    public WebElement password;

    @FindBy(id = "auth-signin-button")
    public WebElement signInAfterPassword;

    @FindBy(id = "continue-announce")
    public WebElement continueButton;

    @FindBy(id = "createAccountSubmit")
    public WebElement createAccountSubmit;

    @FindBy(xpath = "//div[@id='auth-error-message-box']//h4")
    public WebElement emailErrorMessage;

    @FindBy(xpath = "//h4")
    public WebElement errorMessage;

    @FindBy(id = "ap_customer_name")
    public WebElement newUsername;

    @FindBy(id = "ap_email")
    public WebElement newUserEmail;

    @FindBy(id = "ap_password")
    public WebElement newUserpassword;

    @FindBy(id = "ap_password_check")
    public WebElement newUserPasswordcheck;

    @FindBy(id = "auth-customerName-missing-alert")
    public WebElement nameMissingAlert;

    @FindBy(id = "auth-email-missing-alert")
    public WebElement emailMissingAlert;

    @FindBy(id = "auth-email-invalid-email-alert")
    public WebElement invalidEmailAlert;

    @FindBy(id = "auth-password-missing-alert")
    public WebElement passwordMissingAlert;

    @FindBy(id = "auth-password-invalid-password-alert")
    public WebElement invalidPasswordAlert;

    @FindBy(id = "auth-passwordCheck-missing-alert")
    public WebElement passwordCheckMissingAlert;

    @FindBy(id = "auth-password-mismatch-alert")
    public WebElement passwordMismatchAlert;


    public void enterEmailOrMobileNumber(String email){
        signInUsername.sendKeys(email, Keys.ENTER);
    }


    public void usernameEntry(String username) {
        // get username from configuration.properties file
        if(username.equals("username")) {
            username = ConfigurationReader.get("username");
        }

        //username entry verification
        enterEmailOrMobileNumber(username);
        Assert.assertEquals("Verification if username is entered",username, signInUsername.getAttribute("value"));
        logger.info("Username entry is verified");
    }

    public void passwordEntry(String pass) {
        // get password from configuration.properties file
        if(pass.equals("password")) {
            pass = ConfigurationReader.get(pass);
        }

        //password entry verification
        password.sendKeys(pass);
        Assert.assertEquals("Verification if username is entered",pass, password.getAttribute("value"));
        logger.info("Password entry is verified");
        signInAfterPassword.click();
    }

    public void MyAccount() {
        String actualUrl = Driver.get().getCurrentUrl();
        Assert.assertTrue("Login URL verification",actualUrl.contains("https://www.amazon.de/ap/signin"));
        logger.info("User log in is verified");
    }

    public void enterEmailAs(String email) {
        signInUsername.sendKeys(email, Keys.ENTER);
        Assert.assertEquals("Email entry is verified",email, new RegistrationPage().signInUsername.getAttribute("value"));
        logger.info("Wrong email entry is verified" );
    }

    public void iEnterNameAs(String name) {
        newUsername.sendKeys(name);
    }

    public void iEnterEmailAs(String email) {
        newUserEmail.sendKeys(email);
    }

    public void iEnterPasswordAs(String password) {
        newUserpassword.sendKeys(password);
    }

    public void iEnterPasswordAgain(String password) {
        newUserPasswordcheck.sendKeys(password, Keys.ENTER);
    }

    public void iSeeOTPVerificationPage() {
        String expected="https://www.amazon.de/ap/cvf/request?arb=";
        Assert.assertTrue("Verified that user need to enter OTP sent to the mail", Driver.get().getCurrentUrl().contains(expected));
        logger.info("Verified that user need to enter OTP sent to the mail");
    }

    public void iGetTheseErrorMessages(String nameError, String emailError, String passwordError, String passwordCheckError) {

        //Name is missing verification
        if(nameError.equals("missing")){
            Assert.assertTrue("nameMissingAlert verification", nameMissingAlert.isDisplayed());
            logger.info("nameMissingAlert ist verified");
        }else{
            Assert.assertFalse("nameMissingAlert verification", nameMissingAlert.isDisplayed());
            logger.info("Absent of nameMissingAlert ist verified");
        }

        //email is missing verification
        if(emailError.equals("missing")){
            Assert.assertTrue("emailMissingAlert verification", emailMissingAlert.isDisplayed());
            logger.info("emailMissingAlert ist verified");
        }else if(emailError.equals("invalid")) {
            Assert.assertTrue("invalidEmailAlert verification", invalidEmailAlert.isDisplayed());
            logger.info("emailMissingAlert ist verified");
        }else {
            Assert.assertFalse("emailAlert verification", emailMissingAlert.isDisplayed() && invalidEmailAlert.isDisplayed());
            logger.info("Absent of emailAlert ist verified");
        }

        //Password is missing verification
        if(passwordError.equals("missing")){
            Assert.assertTrue("passwordMissingAlert verification", passwordMissingAlert.isDisplayed());
            logger.info("passwordMissingAlert ist verified");
        }else if(passwordError.equals("invalid")){
            Assert.assertTrue("InvalidPasswordAlert verification", invalidPasswordAlert.isDisplayed());
            logger.info("InvalidPasswordAlert ist verified");
        }else{
            Assert.assertFalse("PasswordAlert verification", invalidPasswordAlert.isDisplayed() && passwordMissingAlert.isDisplayed());
            logger.info("Absent of PasswordAlert ist verified");
        }

        //Password check is missing verification
        if(passwordCheckError.equals("missing")){
            Assert.assertTrue("passwordCheckMissingAlert verification", passwordCheckMissingAlert.isDisplayed());
            logger.info("passwordCheckMissingAlert ist verified");
        }else if (passwordCheckError.equals("mismatch")){
            Assert.assertTrue("PasswordMismatchAlert verification", passwordMismatchAlert.isDisplayed());
            logger.info("PasswordMismatchAlert ist verified");
        }else{
            Assert.assertFalse("PasswordCheckhAlert verification", passwordMismatchAlert.isDisplayed() && passwordCheckMissingAlert.isDisplayed());
            logger.info("Absent of PasswordCheckhAlert ist verified");
        }
    }


}
