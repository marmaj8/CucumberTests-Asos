package asos.pageobjects;

import asos.Money;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ProductPage extends BasePage {
    private final static String IMAGE_DESCRIPTION_ATTRIBUTE = "alt";

    @FindBy(xpath = "//*[@class='product-hero']//h1")
    WebElement productName;

    @FindBy(xpath = "//*[@data-id='current-price']")
    WebElement productPrice;

    //depending on product button can have different xpath
    @FindAll({@FindBy(xpath = "//*[@id='product-add']//button"),
            @FindBy(xpath = "//*[@data-test-id='add-button']")})
    WebElement addToBagButton;

    @FindBy(xpath = "//*[@id='aside-content']//select")
    List<WebElement> productOptionsSelectors;

    @FindBy(xpath = "//*[contains(@id,'Error')]")
    List<WebElement> optionsErrors;

    @FindAll(@FindBy(xpath = "//*[@id='recentlyViewed']//*[@data-test-id='carouselList']//a//img"))
    List<WebElement> recentlyBrowsedProductsImages;

    @FindBy(xpath = "//*[@data-test-id='recentlyViewedClearAll']")
    WebElement clearRecentlyBrowsedListButton;

    public ProductPage(WebDriver driver) {
        super(driver, ProductPage.class.getSimpleName());
    }

    @Step("Get product name")
    public String getName() {
        logger.info("Getting product name");
        return productName.getText()
                .trim();
    }

    @Step("Get product price")
    public Money getPrice() {
        logger.info("Getting product price");
        return new Money(productPrice.getText());
    }

    @Step("Add product to bag")
    public ProductPage addToBag() {
        logger.info("Adding product to bag");
        addToBagButton.click();
        return this;
    }

    @Step("Select all first product options")
    public ProductPage selectAllFirstProductOptions() {
        logger.info("Selecting all first product options");
        productOptionsSelectors.stream()
                .filter(WebElement::isDisplayed)
                .forEach(element -> selectFromSelectBoxByPosition(element, 1));
        return this;
    }

    @Step("Get all errors")
    public List<String> getErrors() {
        logger.info("Getting all errors");
        return optionsErrors.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Clear recently browsed products")
    public ProductPage clearRecentlyBrowsed() {
        logger.info("Clearing recently browsed product");
        clearRecentlyBrowsedListButton.click();
        return this;
    }

    @Step("Get all names of recently browsed products")
    public List<String> getAllRecentlyBrowsedNames() {
        logger.info("Getting all names of recently browsed product");
        return recentlyBrowsedProductsImages.stream()
                .map(element -> element.getAttribute(IMAGE_DESCRIPTION_ATTRIBUTE))
                .collect(Collectors.toList());
    }
}
