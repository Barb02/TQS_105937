import io.github.bonigarcia.seljup.SeleniumJupiter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

import webpages.ConfirmationPage;
import webpages.HomePage;
import webpages.PurchasePage;
import webpages.ReservePage;

@ExtendWith(SeleniumJupiter.class)
public class BlazeDemoTest {
    
    @Test
	public void blazedemo(FirefoxDriver driver) {

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
