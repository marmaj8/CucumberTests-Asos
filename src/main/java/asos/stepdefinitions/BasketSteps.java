package asos.stepdefinitions;

import asos.CucumberContext;
import asos.Money;
import asos.PropertyManager;
import asos.pageobjects.PagesManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;

public class BasketSteps {
    private static final String SELECT_OPTIONS_ERROR_TEXT = "Please select from the available colour and size options";

    private static final Logger logger = LogManager.getLogger(BasketSteps.class.getSimpleName());
    PagesManager pagesManager = CucumberContext.getInstance().getPagesManager();
    PropertyManager propertyManager = CucumberContext.getInstance().getPropertyManager();

    String name;
    Money price;

    @Given("Opened first product page for search term {string}")
    public void findProduct(String productKey) {
        String product = propertyManager.getProperty(productKey);

        pagesManager.mainLayout()
                .typeSearchTerm(product)
                .search();
        pagesManager.resultsPage()
                .enterProduct(0);
    }

    @Given("First product for search term {string} added to the bag")
    public void addFirstProductForTermToBag(String searchTermKey) {
        findProduct(searchTermKey);
        selectFirstOptions();
        addProductToBag();
    }

    @Given("Added to bag first products for search terms")
    public void addMultipleProductsToBag(DataTable searchTermsKeys) {
        searchTermsKeys.asList()
                .forEach(this::addFirstProductForTermToBag);
    }

    @Given("Opened bag page")
    @When("Open bag page")
    public void openBagPage() {
        pagesManager.mainLayout()
                .enterBag();
    }

    @When("Customer change quantity of product to {string}")
    public void setQuantityOfFirstProduct(String quantityKey) {
        int quantity = propertyManager.getIntProperty(quantityKey);
        pagesManager.bagPage()
                .selectQuantity(0, quantity)
                .applyChange();
    }

    @When("Customer select any options of product")
    public void selectFirstOptions() {
        pagesManager.productPage()
                .selectAllFirstProductOptions();
    }

    @When("Customer add product to the bag")
    public void addProductToBag() {
        pagesManager.productPage()
                .addToBag();

        name = pagesManager.productPage()
                .getName();
        price = pagesManager.productPage()
                .getPrice();
    }

    @When("Remove {string} product from list")
    public void removeProduct(String productNumberKey) {
        int productNumber = propertyManager.getIntProperty(productNumberKey);

        String productName = pagesManager.bagPage()
                .getAllProductsNames()
                .get(productNumber);

        pagesManager.bagPage()
                .removeFromBag(productNumber);

        List<String> productsNames = pagesManager.bagPage()
                .getAllProductsNames();

        Assert.assertFalse(productsNames.contains(productName));
    }

    @Then("Product is show in the bag")
    public void checkProductIsInBag() {
        List<String> names = pagesManager.bagPage()
                .getAllProductsNames();

        logger.info("Finding product position on list");
        int position = names.indexOf(name);
        Money actualPrice = pagesManager.bagPage()
                .getAllPrices()
                .get(position);

        logger.info("Asserting price of product");
        Assert.assertEquals(actualPrice, price);
    }

    @Then("Not selected options error showed")
    public void checkNotSelectedOptionsErrorShowed() {
        List<String> errors = pagesManager.productPage()
                .getErrors();
        Assert.assertTrue(errors.contains(SELECT_OPTIONS_ERROR_TEXT));
    }

    @Then("Total price for basket will be recalculated")
    public void checkTotalPrice() {
        List<Money> prices = pagesManager.bagPage()
                .getAllPrices();
        List<Integer> quantities = pagesManager.bagPage()
                .getAllQuantities();

        double totalPrice = 0;
        for (int i = 0; i < quantities.size(); i++) {
            totalPrice += quantities.get(i) * prices.get(i).getValue();
        }

        double totalPriceWrapper = pagesManager.bagPage()
                .getTotalPriceFromWrapper()
                .getValue();
        double totalPriceList = pagesManager.bagPage()
                .getTotalPriceFromList()
                .getValue();

        Assert.assertEquals(totalPrice, totalPriceWrapper, Money.MONEY_DELTA);
        Assert.assertEquals(totalPrice, totalPriceList, Money.MONEY_DELTA);
    }
}
