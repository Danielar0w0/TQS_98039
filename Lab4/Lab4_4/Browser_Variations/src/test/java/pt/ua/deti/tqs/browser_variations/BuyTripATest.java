package pt.ua.deti.tqs.browser_variations;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BuyTripATest {

    WebDriver driver;
    BuyTrip buyTrip;

    @BeforeEach
    void setup() {
        driver = new HtmlUnitDriver(BrowserVersion.CHROME);
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