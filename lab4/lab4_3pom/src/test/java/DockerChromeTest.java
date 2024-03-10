import static io.github.bonigarcia.seljup.BrowserType.CHROME;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import webpages.ConfirmationPage;
import webpages.HomePage;
import webpages.PurchasePage;
import webpages.ReservePage;

@ExtendWith(SeleniumJupiter.class)
class DockerChromeTest {

    @Test
    void testChrome(@DockerBrowser(type = CHROME) WebDriver driver) {
        HomePage home = new HomePage(driver);
		home.clickOnFromPortDropdown();
		home.selectFromPortOption();
		home.clickOnToPortDropdown();
		home.selectToPortOption();
		home.clickOnFindFlights();

		ReservePage reserve = new ReservePage(driver);
		reserve.clickOnChooseFlight();

		PurchasePage purchase = new PurchasePage(driver);
		purchase.setName("Maria");
		purchase.setAddress("123");
		purchase.setCreditCardYear("2018");
		purchase.clickOnPurchaseFlight();

		ConfirmationPage confirmation = new ConfirmationPage(driver);

		assertThat(confirmation.pageIsOpen());
    }

}
