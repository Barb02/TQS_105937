package com.pt.ua.app.selenium;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Alert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BookTripSteps {
    
    WebDriver driver;

    @Given("the user is at the website {string} to book a trip")
    public void setURL(String url){
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-headless");
        driver = new FirefoxDriver(options);
        driver.get(url);
    }

    @When("the user chooses {string} as the origin city")
    public void the_user_chooses_as_the_origin_city(String string) {
        WebElement dropdown = driver.findElement(By.id("selectedOrigin"));
        dropdown.findElement(By.xpath("//option[. = '" + string + "']")).click();
    }

    @When("the user chooses the first destination city")
    public void the_user_chooses_as_the_destination_city() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.cssSelector("#selectedDestination > option:nth-child(2)")).click();
    }

    @When("the user chooses {string} as the start date")
    public void the_user_chooses_13_04t08_as_the_start_date(String localDateTime) {
        driver.findElement(By.id("startDateTime")).click();
        driver.findElement(By.id("startDateTime")).sendKeys(localDateTime);
    }

    @When("the user chooses {string} as the end date")
    public void the_user_chooses_13_04t16_as_the_end_date(String localDateTime) {
        driver.findElement(By.id("endDateTime")).click();
        driver.findElement(By.id("endDateTime")).sendKeys(localDateTime);
    }

    @When("the user chooses {string} as the currency")
    public void the_user_chooses_as_the_currency(String string) {
        WebElement dropdown = driver.findElement(By.name("selectedCurrency"));
        dropdown.findElement(By.xpath("//option[. = '" + string + "']")).click();
    }

    @When("the user clicks on the button to search")
    public void the_user_clicks_on_the_button_search() {
        driver.findElement(By.id("submitTripSearch")).click();
    }
    
    @Then("the user should see a list of available trips with the link {string}")
    public void the_user_should_see_a_list_of_available_trips(String string) {
        assertThat(driver.findElement(By.linkText(string))).isNotNull();
    }

    @When("the user clicks on the link {string}")
    public void the_user_clicks_on_the_link(String string) {
        driver.findElement(By.linkText(string)).click();
    }

    @Then("the user should be redirected to the reservation page")
    public void the_user_should_be_redirected_to_the_reservation_page() {
        assertThat(driver.findElement(By.id("reservationForm"))).isNotNull();
    }

    @When("the user fills the form with valid values")
    public void the_user_fills_the_form_with_valid_values() {
        driver.findElement(By.id("numberOfTickets")).click();
        driver.findElement(By.id("numberOfTickets")).sendKeys("1");
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("Joao Andrade Melo");
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("Rua 123");
        driver.findElement(By.id("reservationForm")).click();
        driver.findElement(By.id("phone")).click();
        driver.findElement(By.id("phone")).sendKeys("123912893");
        driver.findElement(By.id("reservationForm")).click();
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys("123912837");
        driver.findElement(By.id("cardNumber")).click();
        driver.findElement(By.id("cardNumber")).sendKeys("12737816239");
        driver.findElement(By.id("expirationMonth")).click();
        driver.findElement(By.id("expirationMonth")).sendKeys("12");
        driver.findElement(By.id("reservationForm")).click();
        driver.findElement(By.id("expirationYear")).click();
        driver.findElement(By.id("expirationYear")).sendKeys("2025");
        driver.findElement(By.id("reservationForm")).click();
        driver.findElement(By.id("cardCvv")).click();
        driver.findElement(By.id("cardCvv")).sendKeys("123");
        driver.findElement(By.id("reservationForm")).click();
        driver.findElement(By.id("cardHolderName")).click();
        driver.findElement(By.id("cardHolderName")).sendKeys("Joao A");
    }

    @When("the user fills the form with an invalid number of seats value")
    public void the_user_fills_the_form_with_invalid_values(){
        driver.findElement(By.id("numberOfTickets")).click();
        driver.findElement(By.id("numberOfTickets")).sendKeys("20");
        driver.findElement(By.id("name")).click();
    }

    @Then("an alert should appear with an error message")
    public void an_alert_should_appear_with_an_error_message() {
        Alert alert = driver.switchTo().alert();
        assertThat(alert).isNotNull();
        alert.accept();
    }

    @Then("the button should be disabled")
    public void the_button_should_be_disabled() {
        assertThat(driver.findElement(By.id("submitButton")).isEnabled()).isFalse();
        driver.close();
    }

    @When("the user clicks on the button to book")
    public void the_user_clicks_on_the_button_book() {
        driver.findElement(By.id("submitButton")).click();
    }

    @Then("the user should be redirected to the reservation status page")
    public void the_user_should_be_redirected_to_the_reservation_status_page() {
        assertThat(driver.findElement(By.id("reservationStatus"))).isNotNull();
        driver.close();
    }

}
