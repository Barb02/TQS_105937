import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import static org.assertj.core.api.Assertions.assertThat;

class HelloWorldChromeJupiterTest {

    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver; 

    @BeforeAll
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup(); 
    }

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver(); 
    }

    @Test
    void test() {
        // Exercise
        String sutUrl = "https://martinfowler.com/bliki/PageObject.html";
        driver.get(sutUrl); 
        String title = driver.getTitle(); 
        log.info("The title of {} is {}", sutUrl, title); 

        // Verify
        assertThat(title).isEqualTo("Page Object");
    }

    @AfterEach
    void teardown() {
        driver.quit(); 
    }

}