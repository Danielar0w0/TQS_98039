package pt.ua.deti.tqs.hw1.functional_tests.functionaltests;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StepDefinitions {

    private WebDriver driver;

    @Before
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");

        driver = new ChromeDriver(options);
    }

    @Given("I am on the Country page")
    public void I_visit_country_page() {
        driver.get("http://localhost:63343/frontend/pages/country.html");
    }

    @When("I search for {string}")
    public void search_for(String query) {
        WebElement input = driver.findElement(By.id("search_input"));

        // Enter something to search for
        input.sendKeys(query);

        WebElement search = driver.findElement(By.className("btn"));
        search.click();
    }

    @Then("the search should start with {string}")
    public void checkCountry(String textStartsWith) {
        // Google's search is rendered dynamically with JavaScript
        // Wait for the page to load timeout after ten seconds
        new WebDriverWait(driver,1L).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                String result = d.findElement(By.id("current_search")).getText().toLowerCase();
                return result.startsWith(textStartsWith.toLowerCase());
            }
        });
    }

    @Given("I am on the Recent page")
    public void I_visit_recent_page() {
        driver.get("http://localhost:63343/frontend/pages/recent.html");
    }

    @When("I search for {string} on date {string}")
    public void search_for(String query, String date) {
        WebElement input = driver.findElement(By.id("search_input"));
        WebElement input_date = driver.findElement(By.id("search_day"));

        // Enter something to search for
        input.sendKeys(query);
        input_date.sendKeys(date);

        WebElement search = driver.findElement(By.className("btn"));
        search.click();
    }

    @After
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
