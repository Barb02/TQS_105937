package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private WebDriver driver;

    private static String PAGE_URL="https://blazedemo.com/";

    public HomePage(WebDriver driver){
       this.driver = driver;
       driver.get(PAGE_URL);
       PageFactory.initElements(driver, this);
    }

    //Locators

    @FindBy(name="fromPort")
    private WebElement fromPortDropdown;

    @FindBy(xpath = "//option[. = 'Portland']")
    WebElement fromOption;

    @FindBy(name="toPort")
    private WebElement toPortDropdown;

    @FindBy(xpath = "//option[. = 'Dublin']")
    WebElement toOption;

    @FindBy(css = ".btn-primary")
    WebElement findFlights;

    public void clickOnFromPortDropdown(){
        fromPortDropdown.click();
    }

    public void selectFromPortOption (){
        fromOption.click();
    }

    public void clickOnToPortDropdown(){
        fromPortDropdown.click();
    }

    public void selectToPortOption (){
        toOption.click();
    }

    public void clickOnFindFlights(){
        findFlights.click();
    }

}
