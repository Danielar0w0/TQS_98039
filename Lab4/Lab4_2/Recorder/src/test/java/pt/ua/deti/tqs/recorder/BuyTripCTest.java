package pt.ua.deti.tqs.recorder;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BuyTripCTest {

  @Test
  public void buyTrip(ChromeDriver driver) {

    driver.get("https://blazedemo.com/");
    driver.manage().window().setSize(new Dimension(1205, 1032));
    driver.findElement(By.name("fromPort")).click();

    {
      WebElement dropdown = driver.findElement(By.name("fromPort"));
      dropdown.findElement(By.xpath("//option[. = 'Portland']")).click();
    }

    driver.findElement(By.name("toPort")).click();

    {
      WebElement dropdown = driver.findElement(By.name("toPort"));
      dropdown.findElement(By.xpath("//option[. = 'New York']")).click();
    }

    driver.findElement(By.cssSelector(".btn-primary")).click();
    driver.findElement(By.cssSelector("tr:nth-child(3) .btn")).click();
    driver.findElement(By.id("inputName")).click();
    driver.findElement(By.id("inputName")).sendKeys("Daniela Dias");
    driver.findElement(By.id("address")).sendKeys("Praça Da Água Fria, Ílhavo, 57");
    driver.findElement(By.id("city")).sendKeys("Aveiro");
    driver.findElement(By.id("state")).sendKeys("Região");
    driver.findElement(By.id("zipCode")).sendKeys("3830-101");
    driver.findElement(By.id("creditCardNumber")).click();
    driver.findElement(By.id("creditCardNumber")).sendKeys("9039232032");
    driver.findElement(By.id("nameOnCard")).click();
    driver.findElement(By.id("nameOnCard")).sendKeys("Daniela Dias");
    driver.findElement(By.cssSelector(".checkbox")).click();
    driver.findElement(By.cssSelector(".btn-primary")).click();
    assertThat(driver.findElement(By.cssSelector("h1")).getText()).contains("BlazeDemo Confirmation");
    //assertThat(driver.findElement(By.cssSelector("h1")).getText()).doesNotContain("BlazeDemo Confirmation");
  }
}
