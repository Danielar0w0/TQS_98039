package pt.ua.deti.tqs.page_object;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BuyTripTest {

    WebDriver driver;
    BuyTrip buyTrip;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.chromedriver().create();
        buyTrip = new BuyTrip(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void testBuyTrip() {
       buyTrip.with("Portland", "New York");
       assertThat(buyTrip.success()).isTrue();
    }

}