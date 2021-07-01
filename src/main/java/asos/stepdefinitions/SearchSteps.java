package asos.stepdefinitions;

import asos.CucumberContext;
import asos.Money;
import asos.PropertyManager;
import asos.pageobjects.PagesManager;
import asos.pageobjects.ResultsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class SearchSteps {
    private static final Logger logger = LogManager.getLogger(SearchSteps.class.getSimpleName());
    PagesManager pagesManager = CucumberContext.getInstance().getPagesManager();
    PropertyManager propertyManager = CucumberContext.getInstance().getPropertyManager();
    SoftAssert softAssert = new SoftAssert();

    ResultsPage.SortOption option;

    @Given("Opened search result for {string}")
    public void searchFor(String searchTermKey) {
        String searchTerm = propertyManager.getProperty(searchTermKey);
        pagesManager.mainLayout()
                .typeSearchTerm(searchTerm)
                .search();
    }

    @When("Customer select sorting by price {string}")
    public void sortResultByPrice(String sortOptionKey) {
        String sortOptionText = propertyManager.getProperty(sortOptionKey);
        switch (sortOptionText) {
            case "asc":
                option = ResultsPage.SortOption.PRICE_ASC;
                break;
            case "desc":
                option = ResultsPage.SortOption.PRICE_DESC;
                break;
            default:
                logger.info("Sort term not supported");
                option = null;
        }
        pagesManager.resultsPage()
                .sortBy(option);
    }

    @Then("Search result are sorted")
    public void checkResultSortStat() {
        List<Money> prices = pagesManager.resultsPage()
                .getAllPrices();


        Money previous = prices.get(0);
        for (int i = 1; i < prices.size(); i++) {
            Money current = prices.get(i);
            switch (option) {
                case PRICE_ASC:
                    softAssert.assertTrue(previous.compareTo(current) < 1,
                            previous + " is bigger than " + current + " when should not");
                    break;
                case PRICE_DESC:
                    softAssert.assertTrue(previous.compareTo(current) > -1,
                            previous + " is lesser than " + current + " when should not");
            }
            previous = current;
        }
        logger.info("Asserting results are sorted by " + option);
        softAssert.assertAll();
    }
}
