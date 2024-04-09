package com.pt.ua.app.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

public class CheckReservationSteps {

    WebDriver driver;

    @Given("the user is at the website {string} to check the status of a reservation")
    public void setURL(String url){
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(url);
    }
    
    @When("the user enters the reservation token {string}")
    public void the_user_enters_the_reservation_token(String string) {
        driver.findElement(By.id("token")).click();
        driver.findElement(By.id("token")).sendKeys(string);
    }

    @When("the user clicks on the button to check the reservation status")
    public void the_user_clicks_on_the_button_search() {
        driver.findElement(By.id("tokenSubmit")).click();
    }

    @Then("the user should be redirected to the corresponding reservation status page")
    public void the_user_should_be_redirected_to_the_corresponding_reservation_status_page() {
        assertThat(driver.findElement(By.id("reservationStatus"))).isNotNull();;
        driver.close();
    }

    @Then("the user should see an {string} message")
    public void the_user_should_see_an_message(String string) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        assertThat(driver.findElement(By.id("errorToken")).getText()).isEqualTo(string);
        driver.close();
    }
}
