package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReservePage {
    
    private WebDriver driver;

    public ReservePage(WebDriver driver){
       this.driver = driver;
       PageFactory.initElements(driver, this);
    }

    @FindBy(css = "tr:nth-child(4) .btn")
    WebElement chooseFlight;

    public void clickOnChooseFlight(){
        chooseFlight.click();
    }
}
