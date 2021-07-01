package asos;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/",
        glue = "asos/stepdefinitions")
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
}
