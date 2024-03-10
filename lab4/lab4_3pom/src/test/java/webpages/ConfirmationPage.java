package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage {
    
    private WebDriver driver;

    public ConfirmationPage(WebDriver driver){
       this.driver = driver;
       PageFactory.initElements(driver, this);
    }

    @FindBy(tagName ="title")
    private WebElement title;

    public boolean pageIsOpen(){
        return title.getText().toString().contains("BlazeDemo Confirmation");
    }
}
