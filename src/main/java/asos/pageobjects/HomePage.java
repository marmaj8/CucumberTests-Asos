package asos.pageobjects;

import asos.CucumberContext;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    static final String HOME_PAGE_PROPERTY_KEY = "home.page.url";

    public HomePage(WebDriver driver) {
        super(driver, HomePage.class.getSimpleName());
    }

    @Step("Open home page")
    public HomePage openPage() {
        logger.info("Opening home page");
        driver.navigate().to(CucumberContext.getInstance()
                .getPropertyManager()
                .getProperty(HOME_PAGE_PROPERTY_KEY));
        return this;
    }
}
