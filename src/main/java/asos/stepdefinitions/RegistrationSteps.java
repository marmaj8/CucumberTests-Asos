package asos.stepdefinitions;

import asos.CucumberContext;
import asos.PropertyManager;
import asos.pageobjects.PagesManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

public class RegistrationSteps {
    private static final Logger logger = LogManager.getLogger(RegistrationSteps.class.getSimpleName());
    PagesManager pagesManager = CucumberContext.getInstance().getPagesManager();
    PropertyManager propertyManager = CucumberContext.getInstance().getPropertyManager();

    @Given("Opened registration page")
    public void openRegistrationPage() {
        pagesManager.mainLayout()
                .enterRegistrationPage();
    }

    @When("Customer in registration enter email {string}")
    public void typeEmail(String emailKey) {
        String email = propertyManager.getProperty(emailKey);

        pagesManager.registrationPage()
                .typeEmail(email);
    }

    @When("Customer in registration enter first name {string}")
    public void typeFirstName(String firstNameKey) {
        String firstName = propertyManager.getProperty(firstNameKey);

        pagesManager.registrationPage()
                .typeFirstName(firstName);
    }

    @When("Customer in registration enter last name {string}")
    public void typeLastName(String lastNameKey) {
        String lastName = propertyManager.getProperty(lastNameKey);

        pagesManager.registrationPage()
                .typeLastName(lastName);
    }

    @When("Customer in registration enter password {string}")
    public void typePassword(String passwordKey) {
        String password = propertyManager.getProperty(passwordKey);

        pagesManager.registrationPage()
                .typePassword(password);
    }

    @When("Try to register")
    public void register() {
        pagesManager.registrationPage()
                .register();
    }

    @Then("Following registration errors shows {string}, {string}, {string}, {string}")
    public void checkErrors(String emailErrorKey, String firstNameErrorKey, String lastNameErrorKey, String passwordErrorKey) {
        String actualEmailError = pagesManager.registrationPage()
                .getEmailError();
        String actualFirstNameError = pagesManager.registrationPage()
                .getFirstNameError();
        String actualLastNameError = pagesManager.registrationPage()
                .getLastNameError();
        String actualPasswordError = pagesManager.registrationPage()
                .getPasswordError();

        logger.info("Asserting registration text box errors");
        Assert.assertEquals(propertyManager.getProperty(emailErrorKey), actualEmailError);
        Assert.assertEquals(propertyManager.getProperty(firstNameErrorKey), actualFirstNameError);
        Assert.assertEquals(propertyManager.getProperty(lastNameErrorKey), actualLastNameError);
        Assert.assertEquals(propertyManager.getProperty(passwordErrorKey), actualPasswordError);
    }
}
