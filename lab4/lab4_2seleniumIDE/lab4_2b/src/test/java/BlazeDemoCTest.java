import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
public class BlazeDemoCTest {
    
    @Test
	public void blazedemo(FirefoxDriver driver) {
		driver.get("https://blazedemo.com/");
		driver.manage().window().setSize(new Dimension(550, 660));
		driver.findElement(By.name("fromPort")).click();
		{
			WebElement dropdown = driver.findElement(By.name("fromPort"));
			dropdown.findElement(By.xpath("//option[. = 'Portland']")).click();
		}
		driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(4)")).click();
		driver.findElement(By.name("toPort")).click();
		{
			WebElement dropdown = driver.findElement(By.name("toPort"));
			dropdown.findElement(By.xpath("//option[. = 'Dublin']")).click();
		}
		driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(6)")).click();
		driver.findElement(By.cssSelector(".btn-primary")).click();
		driver.findElement(By.cssSelector("tr:nth-child(4) .btn")).click();
		driver.findElement(By.id("inputName")).click();
		driver.findElement(By.id("inputName")).sendKeys("Maria");
		driver.findElement(By.id("address")).click();
		driver.findElement(By.id("address")).sendKeys("123");
		driver.findElement(By.cssSelector(".control-group:nth-child(3) > .control-label")).click();
		driver.findElement(By.cssSelector(".control-group:nth-child(4) > .controls")).click();
		driver.findElement(By.id("creditCardYear")).click();
		driver.findElement(By.id("creditCardYear")).sendKeys("2018");
		driver.findElement(By.cssSelector("form")).click();
		driver.findElement(By.cssSelector(".btn-primary")).click();
		assertThat(driver.getTitle()).isEqualTo("BlazeDemo Confirmation");
	}
}
