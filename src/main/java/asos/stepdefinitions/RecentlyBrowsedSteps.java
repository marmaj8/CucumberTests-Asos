package asos.stepdefinitions;

import asos.CucumberContext;
import asos.PropertyManager;
import asos.pageobjects.PagesManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.testng.asserts.SoftAssert;

import java.util.LinkedList;
import java.util.List;

public class RecentlyBrowsedSteps {
    private static final Logger logger = LogManager.getLogger(RecentlyBrowsedSteps.class.getSimpleName());
    PagesManager pagesManager = CucumberContext.getInstance().getPagesManager();
    PropertyManager propertyManager = CucumberContext.getInstance().getPropertyManager();
    SoftAssert softAssert = new SoftAssert();

    List<String> rememberedValues = new LinkedList<>();

    @Given("Opened home page")
    public void openHomePage() {
        pagesManager.homePage().openPage();
        pagesManager.mainLayout().closeWelcomeMessage();
    }

    @When("User enter multiple products from search")
    public void enterMultipleProducts(DataTable productsKeys) {
        productsKeys.asList()
                .forEach(productKey -> {
                    String product = propertyManager.getProperty(productKey);

                    pagesManager.mainLayout()
                            .typeSearchTerm(product)
                            .search();
                    pagesManager.resultsPage()
                            .enterProduct(0);
                    rememberedValues.add(0, pagesManager.productPage().getName());
                });
    }

    @When("Click clear recently browsed buttons")
    public void clearRecentlyEntered() {
        pagesManager.productPage()
                .clearRecentlyBrowsed();
    }

    @Then("Recently entered products should be on the list")
    public void compareToRecentlyEnteredList() {
        List<String> names = pagesManager.productPage()
                .getAllRecentlyBrowsedNames();

        rememberedValues.remove(0);

        int count = rememberedValues.size();
        // if older records exist
        if (count > names.size()) {
            count = names.size();
        }

        logger.info("Asserting any recently browsed element exist");
        Assert.assertTrue(count > 0);

        for (int i = 0; i < count; i++) {
            String actual = names.get(i);
            String expected = rememberedValues.get(i);
            softAssert.assertEquals(actual, expected, "For " + i + " recently browsed expected '" + expected + "' but found '" + actual + "'");
        }

        logger.info("Asserting names of recently browsed products");
        softAssert.assertAll();
    }

    @Then("List of recently entered products should be cleared")
    public void checkIfRecentlyEnteredIsEmpty() {
        List<String> names = pagesManager.productPage()
                .getAllRecentlyBrowsedNames();

        logger.info("Asserting size of recently browsed");
        Assert.assertEquals(names.size(), 0);
    }
}
