package utils;

import org.openqa.selenium.WebDriver;

import java.util.Set;

public class GenericUtils {

    WebDriver driver;

    public GenericUtils(WebDriver driver){
        this.driver=driver;
    }


    public void switchToChildWindow(){
        Set<String> windows = driver.getWindowHandles();
    }
}
