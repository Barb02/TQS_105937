// Generated by Selenium IDE
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class BlazedemoTest {

	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@BeforeEach
	public void setUp() {
		driver = new FirefoxDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}
	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void blazedemo() {
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
