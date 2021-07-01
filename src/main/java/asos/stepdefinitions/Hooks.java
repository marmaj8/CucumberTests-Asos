package asos.stepdefinitions;

import asos.CucumberContext;
import asos.PropertyManager;
import asos.drivers.DriverManager;
import asos.pageobjects.PagesManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private final static String PROPERTY_FILE = "asos.properties";

    @Before
    public void Setup() {
        WebDriver driver = DriverManager.getDriver("Chrome");

        CucumberContext context = CucumberContext.getInstance();

        context.setDriver(driver);
        context.setPagesManager(new PagesManager(driver));
        context.setPropertyManager(new PropertyManager(PROPERTY_FILE));
    }

    @After
    public void cleanUp() {
        CucumberContext.getInstance().getDriver().quit();
    }
}
