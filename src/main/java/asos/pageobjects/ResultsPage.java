package asos.pageobjects;

import asos.Money;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class ResultsPage extends BasePage {
    private final static String ALL_END_NODES_OF_FATHER_XPATH = "..//*[not(*)]";

    @FindAll(@FindBy(xpath = "//ul[@data-auto-id='mediumRefinements']//li//button"))
    List<WebElement> filterSorterOptions;

    @FindAll(@FindBy(xpath = "//*[@data-auto-id='mediumRefinements']//li//li"))
    List<WebElement> possibleOptions;

    @FindAll(@FindBy(xpath = "//article[contains(@id,'product')]//a"))
    List<WebElement> productsLinks;

    @FindAll(@FindBy(xpath = "//*[@data-auto-id='productTilePrice']"))
    List<WebElement> productsPrices;

    @FindBy(xpath = "//*[@data-auto-id='productList']//span[@role='alert']")
    WebElement loadingIndicator;

    @FindBy(xpath = "//*[@data-auto-id='styleCount']")
    WebElement foundedElementsNumber;

    public ResultsPage(WebDriver driver) {
        super(driver, ResultsPage.class.getSimpleName());
    }

    @Step("Sort results by '{0}'")
    public ResultsPage sortBy(SortOption sortOption) {
        waitForVisibility(foundedElementsNumber);

        // sort always first
        logger.info("Expanding sort options");
        filterSorterOptions.get(0)
                .click();
        logger.info("Selecting sort option '" + sortOption + "'");
        possibleOptions.get(sortOption.getPosition())
                .click();

        waitForChange(loadingIndicator);
        waitForChange(loadingIndicator);
        return this;
    }

    @Step("Enter {0} product from list")
    public ResultsPage enterProduct(int productNumber) {
        logger.info("Entering " + productNumber + " product from list");
        productsLinks.get(productNumber)
                .click();
        return this;
    }

    @Step("Get all prices from results")
    public List<Money> getAllPrices() {
        logger.info("Getting prices from results");
        return productsPrices.stream()
                .map(element -> {
                    List<WebElement> subEndNodes = element
                            .findElements(By.xpath(ALL_END_NODES_OF_FATHER_XPATH));
                    return new Money(subEndNodes
                            .get(subEndNodes.size() - 1)
                            .getText());
                })
                .collect(Collectors.toList());
    }

    public enum SortOption {
        FAVORITE(0),
        NEW(1),
        PRICE_DESC(2),
        PRICE_ASC(3);

        int position;

        SortOption(int pos) {
            position = pos;
        }

        public int getPosition() {
            return position;
        }
    }
}
