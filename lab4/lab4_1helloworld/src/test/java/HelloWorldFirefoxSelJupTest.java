import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
class HelloWorldFirefoxSelJupTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    void test(FirefoxDriver driver) {
        // Same test logic than other "hello world" tests
        // Exercise
        String sutUrl = "https://martinfowler.com/bliki/PageObject.html";
        driver.get(sutUrl); 
        String title = driver.getTitle(); 
        log.info("The title of {} is {}", sutUrl, title); 

        // Verify
        assertThat(title).isEqualTo("Page Object");
    }

}
