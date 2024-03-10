package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PurchasePage {

    private WebDriver driver;

    public PurchasePage(WebDriver driver){
       this.driver = driver;
       PageFactory.initElements(driver, this);
    }

    @FindBy(id="inputName")
    private WebElement inputName;

    @FindBy(id="address")
    private WebElement inputAddress;

    @FindBy(id="creditCardYear")
    private WebElement inputCreditCardYear;

    @FindBy(css=".btn-primary")
    private WebElement purchaseFlight;

    public void setName(String name){
        inputName.clear();
        inputName.sendKeys(name);
    }

    public void setAddress(String address){
        inputAddress.clear();
        inputAddress.sendKeys(address);
    }

    public void setCreditCardYear(String year){
        inputCreditCardYear.clear();
        inputCreditCardYear.sendKeys(year);
    }

    public void clickOnPurchaseFlight(){
        purchaseFlight.click();
    }
}
