package asos;

import asos.pageobjects.PagesManager;
import org.openqa.selenium.WebDriver;

public class CucumberContext {
    WebDriver driver;
    PagesManager pagesManager;
    PropertyManager propertyManager;

    private static CucumberContext instance;

    private CucumberContext() {
    }

    public static CucumberContext getInstance() {
        if (instance == null) {
            instance = new CucumberContext();
        }
        return instance;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void setPagesManager(PagesManager pagesManager) {
        this.pagesManager = pagesManager;
    }

    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public PagesManager getPagesManager() {
        return pagesManager;
    }

    public PropertyManager getPropertyManager() {
        return propertyManager;
    }
}
