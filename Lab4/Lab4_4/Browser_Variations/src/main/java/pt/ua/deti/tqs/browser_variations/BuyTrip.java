package pt.ua.deti.tqs.browser_variations;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BuyTrip {

    WebDriver driver;

    /*
    By fromPort = By.name("fromPort");
    By toPort = By.name("toPort");
    By findButton = By.cssSelector(".btn-primary");
    By flightButton = By.cssSelector("tr:nth-child(3) .btn");
    By conclusionButton = By.cssSelector(".btn-primary");
     */

    @FindBy(name = "fromPort")
    @CacheLookup
    WebElement fromPort;

    @FindBy(name = "toPort")
    @CacheLookup
    WebElement toPort;

    @FindBy(css = ".btn-primary")
    @CacheLookup
    WebElement findButton;

    @FindBy(css = "tr:nth-child(3) .btn")
    @CacheLookup
    WebElement flightButton;

    @FindBy(css = ".btn-primary")
    @CacheLookup
    WebElement conclusionButton;

    @FindBy(tagName = "h1")
    @CacheLookup
    WebElement successText;

    public BuyTrip(WebDriver driver) {
        this.driver = driver;
        //Initialise Elements
        PageFactory.initElements(driver, this);
        driver.get("https://blazedemo.com/");
    }

    public void with(String optionFromPort, String optionToPort) {

        /*
        WebElement dropdown = driver.findElement(fromPort);
        dropdown.findElement(By.xpath("//option[. = '" + optionFromPort + "']")).click();

        dropdown = driver.findElement(toPort);
        dropdown.findElement(By.xpath("//option[. = '" + optionToPort + "']")).click();

        driver.findElement(findButton).click();
        driver.findElement(flightButton).click();
        driver.findElement(conclusionButton).click();
         */

        Select dropdown = new Select(fromPort);
        dropdown.selectByValue(optionFromPort);

        dropdown = new Select(toPort);
        dropdown.selectByValue(optionToPort);

        findButton.click();
        flightButton.click();
        conclusionButton.click();
    }

    public boolean success() {
        // return driver.findElement(By.cssSelector("h1")).getText().contains("Thank you for your purchase today!");
        return successText.getText().contains("Thank you for your purchase today!");
    }
}
