package asos.pageobjects;

import asos.Money;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class BagPage extends BasePage {

    @FindBy(xpath = "//*[contains(@class,'bag-holder')]//*[contains(@class,'bag-total-price')]")
    WebElement totalPriceWrapper;

    @FindBy(xpath = "//*[contains(@class,'bag-subtotal-price')]")
    WebElement totalPriceList;

    @FindAll(@FindBy(xpath = "//*[contains(@class,'bag-item-name')]"))
    List<WebElement> productsNames;

    @FindAll(@FindBy(xpath = "//ul[contains(@class,'bag-items')]//*[contains(@class,'bag-item-price--current')]"))
    List<WebElement> productsPrices;

    @FindBy(xpath = "//select[contains(@class,'bag-item-quantity')]")
    List<WebElement> productsQuantities;

    @FindAll(@FindBy(xpath = "//button[contains(@class,'bag-item-remove')]"))
    List<WebElement> removeFromBagButtons;

    @FindBy(xpath = "//button[contains(@class,'bag-item-edit-update')]")
    WebElement updateButton;

    public BagPage(WebDriver driver) {
        super(driver, BagPage.class.getSimpleName());
    }

    @Step("Set quantity of {0} product to {1}")
    public BagPage selectQuantity(int productNumber, int quantity) {
        waitForVisibility(totalPriceList);
        logger.info("Setting quantity of " + productNumber + "product to " + quantity);
        selectFromSelectBoxByValue(productsQuantities.get(productNumber), "" + quantity);
        return this;
    }

    @Step("Apply change of bag")
    public BagPage applyChange() {
        waitForVisibility(updateButton);
        logger.info("Applying change of bag");
        updateButton.click();
        waitForChange(totalPriceWrapper);
        return this;
    }

    @Step("Remove {0} product from bag")
    public BagPage removeFromBag(int productNumber) {
        waitForVisibility(totalPriceList);
        logger.info("Removing " + productNumber + " product from bag");
        removeFromBagButtons.get(productNumber)
                .click();
        waitForChange(totalPriceWrapper);
        return this;
    }

    @Step("Get names of all products in bag")
    public List<String> getAllProductsNames() {
        waitForVisibility(totalPriceList);
        logger.info("Getting names of all products in bag");
        return productsNames.stream()
                .map(element -> element.getText()
                        .trim())
                .collect(Collectors.toList());
    }

    @Step("Get prices of all products in bag")
    public List<Money> getAllPrices() {
        waitForVisibility(totalPriceList);
        logger.info("Getting prices of all products in bag");
        return productsPrices.stream()
                .map(element -> new Money(element.getText()))
                .collect(Collectors.toList());
    }

    @Step("Get total price from wrapper")
    public Money getTotalPriceFromWrapper() {
        logger.info("Getting total price from wrapper");
        return new Money(totalPriceWrapper.getText());
    }

    @Step("Get total price from list")
    public Money getTotalPriceFromList() {
        logger.info("Getting total price from list");
        return new Money(totalPriceList.getText());
    }

    @Step("Get all products quantities")
    public List<Integer> getAllQuantities() {
        logger.info("Getting all quantities");
        return productsQuantities.stream()
                .map(element -> Integer.parseInt(new Select(element)
                        .getFirstSelectedOption().getText()))
                .collect(Collectors.toList());
    }
}
