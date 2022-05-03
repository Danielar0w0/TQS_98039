package pt.ua.deti.tqs.lab5_3.demo;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StepsDefinition {

    private WebDriver driver;

    @Given("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver = WebDriverManager.chromedriver().create();
        driver.get(url);
        driver.manage().window().setSize(new Dimension(1205, 1032));
    }

    @When("I selected the ports {string} \\(departure) and {string} \\(destination)")
    public void iSelectPorts(String fromPort, String toPort) {

        driver.findElement(By.name("fromPort")).click();
        WebElement dropdown = driver.findElement(By.name("fromPort"));
        dropdown.findElement(By.xpath("//option[. = '" + fromPort + "']")).click();

        driver.findElement(By.name("toPort")).click();
        WebElement dropdown2 = driver.findElement(By.name("toPort"));
        dropdown2.findElement(By.xpath("//option[. = '" + toPort + "']")).click();
    }

    @When("I choose the flight {int}")
    public void iChooseFlight(int flightNumber) {
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.cssSelector("tr:nth-child(" + flightNumber + ") .btn")).click();
    }

    @When("I purchase the flight")
    public void iPurchaseFlight() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("I should see the message {string}")
    public void iSeeMessage(String message) {
        assertThat(driver.findElement(By.cssSelector("h1")).getText()).contains(message);
    }
}
