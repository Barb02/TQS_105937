package com.pt.ua;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BlazeDemoSteps {
    
    WebDriver driver;

    @Given("the user is at the website {string}")
    public void setURL(String url) {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
    }

    @Given("the user is at {word} page")
    public void verifyInitialPage(String page){
        assertThat(driver.getTitle()).isEqualTo(page);
    }

    @When("the user chooses {string} as the departure city")
    public void setDepartureCity(String city){
        driver.findElement(By.name("fromPort")).click();
		{
			WebElement dropdown = driver.findElement(By.name("fromPort"));
			dropdown.findElement(By.xpath("//option[. = '"+ city +"']")).click();
		}
    }

    @When("the user chooses {string} as the destination city")
    public void setDestinationCity(String city){
        driver.findElement(By.name("toPort")).click();
		{
			WebElement dropdown = driver.findElement(By.name("toPort"));
			dropdown.findElement(By.xpath("//option[. = '"+ city +"']")).click();
		}
    }

    @When("the user clicks on the button {string}")
    public void clickOnButton(String buttonName){
        driver.findElement(By.cssSelector("input[value='" + buttonName + "']")).click();
    }

    @When("the user fills the form with valid values")
    public void setFormValues(){
        driver.findElement(By.id("inputName")).sendKeys("John Doe");
        driver.findElement(By.id("address")).sendKeys("123 Abbey Road");
        driver.findElement(By.id("city")).sendKeys("Liverpool");
        driver.findElement(By.id("zipCode")).sendKeys("99999");
        driver.findElement(By.id("creditCardNumber")).sendKeys("33333333");
        driver.findElement(By.id("nameOnCard")).sendKeys("John M. Doe");
    }

    @Then("the user should be redirected to the {word} page")
    public void verifyRedirectPage(String page){
        assertThat(driver.getTitle().toLowerCase()).contains(page);
    }
    
}
