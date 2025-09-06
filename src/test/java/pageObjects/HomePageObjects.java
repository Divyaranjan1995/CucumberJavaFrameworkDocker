package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePageObjects {

    WebDriver driver;


    public HomePageObjects(WebDriver driver){
        this.driver=driver;
    }

    public WebElement getTabElement(String tab){
        return driver.findElement(By.xpath("//p[text()='"+tab+"']"));
    }
}
