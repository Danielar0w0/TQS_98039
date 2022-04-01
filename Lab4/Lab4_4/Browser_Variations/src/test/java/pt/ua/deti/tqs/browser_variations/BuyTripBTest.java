package pt.ua.deti.tqs.browser_variations;

import io.github.bonigarcia.seljup.BrowserType;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SeleniumJupiter.class)
class BuyTripBTest {

    @Test
    void testBuyTrip(@DockerBrowser(type = BrowserType.CHROME) WebDriver driver) {
        BuyTrip buyTrip = new BuyTrip(driver);
        buyTrip.with("Portland", "New York");
        assertThat(buyTrip.success()).isTrue();
    }

}