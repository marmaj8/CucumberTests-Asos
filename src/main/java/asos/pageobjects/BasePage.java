package asos.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected Logger logger;
    protected Actions actions;

    public BasePage(WebDriver driver, String className) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
        logger = LogManager.getLogger(className);
        actions = new Actions(driver);
    }

    protected void selectFromSelectBoxByValue(WebElement element, String value) {
        logger.info("Trying to select option with value '" + value + "' from select box '" + element + "'");
        Select select = new Select(element);

        try {
            select.selectByValue(value);
        } catch (Exception e) {
            logger.error("Option cannot be selected");
            throw e;
        }
    }

    protected void selectFromSelectBoxByPosition(WebElement element, int index) {
        logger.info("Trying to select option with index '" + index + "' from select box '" + element + "'");
        Select select = new Select(element);

        try {
            select.selectByIndex(index);
        } catch (Exception e) {
            logger.error("Option cannot be selected");
            throw e;
        }
    }

    protected void waitForElementToBeClickable(WebElement element) {
        logger.info("Waiting for element '" + element + "'");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForChange(WebElement element) {
        logger.info("Waiting for change");
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(element, element.getText())));
        } catch (Exception ex) {
            logger.info("Element '" + element + "' not changed");
        }
    }

    protected void waitForVisibility(WebElement... elements) {
        logger.info("Waiting for visibility of elements");
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    protected void scrollToElement(WebElement element) {
        logger.info("Scrolling to element '" + element + "'");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
