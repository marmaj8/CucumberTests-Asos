package asos.pageobjects;

import org.openqa.selenium.WebDriver;

public class PagesManager {
    WebDriver driver;
    HomePage homePage;
    MainLayout mainLayout;
    ResultsPage resultsPage;
    ProductPage productPage;
    BagPage bagPage;
    RegistrationPage registrationPage;

    public PagesManager(WebDriver driver) {
        this.driver = driver;
        homePage = new HomePage(driver);
        mainLayout = new MainLayout(driver);
        resultsPage = new ResultsPage(driver);
        productPage = new ProductPage(driver);
        bagPage = new BagPage(driver);
        registrationPage = new RegistrationPage(driver);
    }

    public HomePage homePage() {
        return homePage;
    }

    public MainLayout mainLayout() {
        return mainLayout;
    }

    public ResultsPage resultsPage() {
        return resultsPage;
    }

    public ProductPage productPage() {
        return productPage;
    }

    public BagPage bagPage() {
        return bagPage;
    }

    public RegistrationPage registrationPage() {
        return registrationPage;
    }
}
