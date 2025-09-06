package pageObjects;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    WebDriver driver;

    LoginPageObjects loginPageObjects;
    HomePageObjects homePageObjects;

    public PageObjectManager(WebDriver driver){
        this.driver=driver;
    }

    public LoginPageObjects getLoginPageObjects(){
        return loginPageObjects = new LoginPageObjects(driver);
    }

    public HomePageObjects getHomePageObjects(){
        return homePageObjects = new HomePageObjects(driver);
    }
}
