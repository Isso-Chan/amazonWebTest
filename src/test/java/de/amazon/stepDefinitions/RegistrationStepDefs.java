package de.amazon.stepDefinitions;

import de.amazon.pages.BasePage;
import de.amazon.pages.RegistrationPage;
import de.amazon.utilities.ConfigurationReader;
import de.amazon.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationStepDefs {

    Logger logger = LoggerFactory.getLogger(RegistrationStepDefs.class);



    @When("I click sign-in button")
    public void iClickSignInButton() {
        new RegistrationPage().signIn.click();
    }

    @And("I enter my username as {string}")
    public void I_Enter_My_Username_As(String username) {
        RegistrationPage registrationPage=new RegistrationPage();
        registrationPage.usernameEntry(username);

    }

    @And("I enter my password as {string}")
    public void I_Enter_My_password_As (String password) {
        RegistrationPage registrationPage=new RegistrationPage();
        registrationPage.passwordEntry(password);

    }

    @Then("I login my account")
    public void I_login_My_Account() {
        new RegistrationPage().MyAccount();
    }


    @When("I enter {string} as Email")
    public void I_Enter_As_Email(String email) {
        RegistrationPage registrationPage=new RegistrationPage();
        registrationPage.enterEmailAs(email);
    }

    @Then("I get error message on Email")
    public void I_Get_Error_Message_On_Email() {
        Assert.assertTrue(new RegistrationPage().emailErrorMessage.isDisplayed());
        logger.info("Wrong email warning message is verified as: {} ", new RegistrationPage().emailErrorMessage.getText());
    }

    @Then("I get error message on password")
    public void I_Get_Error_Message_On_Password() {
        Driver.get().navigate().refresh();
        Assert.assertTrue(new RegistrationPage().errorMessage.isDisplayed());
        logger.info("Wrong email warning message is verified as: {} ", new RegistrationPage().errorMessage.getText());
    }

    @When("I as a new user click the button to create an account")
    public void iAsANewUserClickTheButtonToCreateAnAccount() {
        new RegistrationPage().createAccountSubmit.click();
    }

    @And("I enter name as {string}")
    public void iEnterNameAs(String name) {
        RegistrationPage registrationPage=new RegistrationPage();
        registrationPage.iEnterNameAs(name);
    }

    @And("I enter email as {string}")
    public void iEnterEmailAs(String email) {
        RegistrationPage registrationPage=new RegistrationPage();
        registrationPage.iEnterEmailAs(email);
    }

    @And("I enter password as {string}")
    public void iEnterPasswordAs(String password) {
        RegistrationPage registrationPage=new RegistrationPage();
        registrationPage.iEnterPasswordAs(password) ;
    }

    @And("I enter password again {string}")
    public void iEnterPasswordAgain(String password) {
        RegistrationPage registrationPage=new RegistrationPage();
        registrationPage.iEnterPasswordAgain(password) ;
    }

    @Then("I see OTP verification page")
    public void iSeeOTPVerificationPage() {
        RegistrationPage registrationPage=new RegistrationPage();
        registrationPage.iSeeOTPVerificationPage();
    }

    @Then("I get these error messages {string}  {string} {string} {string}")
    public void iGetTheseErrorMessages(String nameError, String emailError, String passwordError, String passwordCheckError) {
        RegistrationPage registrationPage=new RegistrationPage();
        registrationPage.iGetTheseErrorMessages(nameError, emailError, passwordError, passwordCheckError) ;
    }
}
