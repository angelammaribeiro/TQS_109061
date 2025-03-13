import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)  // 1. Use the Selenium-Jupiter extension to inject WebDriver
class HelloWorldChromeJupiter2Test {

    // 2. No need for explicit setup of WebDriver, it will be injected into the test methods
    @Test
    void test(WebDriver driver) {  // 3. Inject WebDriver instance into the test method
        // Exercise
        String initialUrl = "https://bonigarcia.dev/selenium-webdriver-java/index.html"; // Assuming this is the initial page
        driver.get(initialUrl);

        // Click on the "Slow calculator" link
        driver.findElement(By.linkText("Slow calculator")).click();

        // Assert that we are on the correct page
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl).isEqualTo("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");

        // Optional: Verify page title to ensure correct page
        String title = driver.getTitle();
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }
}