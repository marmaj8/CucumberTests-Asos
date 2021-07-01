package asos.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainLayout extends BasePage {

    @FindBy(xpath = "//*[@id='chrome-header']")
    WebElement header;

    @FindBy(xpath = "//input[@id='chrome-search']")
    WebElement searchTextBox;

    @FindBy(xpath = "//button[@data-testid='search-button-inline']")
    WebElement searchButton;

    @FindBy(xpath = "//*[@data-testid='miniBagIcon']")
    WebElement bagIcon;

    @FindBy(xpath = "//*[@data-test-id='bag-link']")
    WebElement goToBagButton;

    @FindBy(xpath = "//*[@data-testid='welcome-message']//button[@data-testid='close-button']")
    WebElement closeWelcomeMessageButton;

    @FindBy(xpath = "//*[@data-testid='myAccountIcon']")
    WebElement accountIcon;

    @FindBy(xpath = "//*[@data-testid='signup-link']")
    WebElement goToRegistrationPageButton;

    public MainLayout(WebDriver driver) {
        super(driver, MainLayout.class.getSimpleName());
    }

    @Step("Type search term: {0}")
    public MainLayout typeSearchTerm(String searchTerm) {
        logger.info("Typing '" + searchTerm + "' into search bar");
        searchTextBox.sendKeys(searchTerm);
        return this;
    }

    @Step("Search")
    public MainLayout search() {
        logger.info("Starting searching");
        searchButton.click();
        return this;
    }

    @Step("Enter bag page")
    public MainLayout enterBag() {
        logger.info("Entering bag page");
        scrollToElement(header);
        actions.moveToElement(bagIcon).perform();
        waitForElementToBeClickable(goToBagButton);
        goToBagButton.click();
        return this;
    }

    @Step("Close welcome message")
    public MainLayout closeWelcomeMessage() {
        logger.info("Closing welcome message");
        try {
            waitForElementToBeClickable(closeWelcomeMessageButton);
            closeWelcomeMessageButton.click();
        } catch (Exception e) {
            logger.info("Welcome message not found");
        }
        return this;
    }

    @Step("Enter registration page")
    public MainLayout enterRegistrationPage() {
        logger.info("Entering registration page");
        scrollToElement(header);
        actions.moveToElement(accountIcon).perform();
        waitForElementToBeClickable(goToRegistrationPageButton);
        goToRegistrationPageButton.click();
        return this;
    }
}
