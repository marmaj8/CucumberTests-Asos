package asos.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BasePage {

    @FindBy(xpath = "//input[@id='Email']")
    WebElement emailTextBox;

    @FindBy(xpath = "//input[@id='FirstName']")
    WebElement firstNameTextBox;

    @FindBy(xpath = "//input[@id='LastName']")
    WebElement lastNameTextBox;

    @FindBy(xpath = "//input[@id='Password']")
    WebElement passwordTextBox;

    @FindBy(xpath = "//*[@id='Email-error']")
    WebElement emailError;

    @FindBy(xpath = "//*[@id='FirstName-error']")
    WebElement firstNameError;

    @FindBy(xpath = "//*[@id='LastName-error']")
    WebElement lastNameError;

    @FindBy(xpath = "//*[@id='Password-error']")
    WebElement passwordError;

    @FindBy(xpath = "//*[@id='register']")
    WebElement registerButton;

    public RegistrationPage(WebDriver driver) {
        super(driver, RegistrationPage.class.getSimpleName());
    }

    @Step("Type email: {0}")
    public RegistrationPage typeEmail(String email) {
        logger.info("Typing '" + email + "' into email text box");
        emailTextBox.sendKeys(email + Keys.TAB);
        return this;
    }

    @Step("Type first name: {0}")
    public RegistrationPage typeFirstName(String firstName) {
        logger.info("Typing '" + firstName + "' into first name text box");
        firstNameTextBox.sendKeys(firstName + Keys.TAB);
        return this;
    }

    @Step("Type last name: {0}")
    public RegistrationPage typeLastName(String lastName) {
        logger.info("Typing '" + lastName + "' into last name text box");
        lastNameTextBox.sendKeys(lastName + Keys.TAB);
        return this;
    }

    @Step("Type password: {0}")
    public RegistrationPage typePassword(String password) {
        logger.info("Typing '" + password + "' into password text box");
        passwordTextBox.sendKeys(password + Keys.TAB);
        return this;
    }

    @Step("Register")
    public RegistrationPage register() {
        logger.info("Registering");
        registerButton.click();
        try {
            waitForVisibility(firstNameError);
        } catch (Exception ex) {
            logger.info("Not all errors are visible");
        }
        return this;
    }

    @Step("Get email error")
    public String getEmailError() {
        logger.info("Getting email error");
        try {
            return emailError.getText()
                    .trim();
        } catch (Exception ex) {
            logger.info("Email error not found");
            return "";
        }
    }

    @Step("Get first name error")
    public String getFirstNameError() {
        logger.info("Getting first name error");
        try {
            return firstNameError.getText()
                    .trim();
        } catch (Exception ex) {
            logger.info("First name error not found");
            return "";
        }
    }

    @Step("Get last name error")
    public String getLastNameError() {
        logger.info("Getting last name error");
        try {
            return lastNameError.getText()
                    .trim();
        } catch (Exception ex) {
            logger.info("Last name error not found");
            return "";
        }
    }

    @Step("Get password error")
    public String getPasswordError() {
        logger.info("Getting password error");
        try {
            return passwordError.getText()
                    .trim();
        } catch (Exception ex) {
            logger.info("Password error not found");
            return "";
        }
    }
}
