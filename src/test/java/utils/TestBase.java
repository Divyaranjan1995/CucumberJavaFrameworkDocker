package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class TestBase {

    // Thread-safe WebDriver
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // To load properties only once
    private static Properties prop;

    static {
        try {
            FileInputStream propFile = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/properties/config.properties");
            prop = new Properties();
            prop.load(propFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    public WebDriver getDriver() {
        if(driver.get()==null){
            try {
                webDriverManager();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return driver.get();
    }

    public void webDriverManager() throws IOException {
        String url = prop.getProperty("url");
        String browserName = System.getProperty("browser", prop.getProperty("browser"));
        String runMode = prop.getProperty("runMode");  // local or docker
        String gridUrl = prop.getProperty("gridUrl");

            WebDriver webDriver;

            if ("docker".equalsIgnoreCase(runMode)) {
                // --- Running in Docker (RemoteWebDriver) ---
                DesiredCapabilities capabilities = new DesiredCapabilities();

                if (browserName.equalsIgnoreCase("chrome")) {
                    capabilities.setBrowserName("chrome");
                } else if (browserName.equalsIgnoreCase("edge")) {
                    capabilities.setBrowserName("MicrosoftEdge");
                } else {
                    capabilities.setBrowserName("firefox");
                }

                try {
                    webDriver = new RemoteWebDriver(new URL(gridUrl), capabilities);
                } catch (Exception e) {
                    throw new RuntimeException("Unable to connect to Selenium Grid in Docker: " + e.getMessage());
                }

            } else {
                // --- Running locally ---
                if (browserName.equalsIgnoreCase("chrome")) {
                    webDriver = new ChromeDriver();
                } else if (browserName.equalsIgnoreCase("firefox")) {
                    webDriver = new FirefoxDriver();
                } else {
                    webDriver = new EdgeDriver();
                }
            }

            ExtentManager.registerDriver(webDriver);

            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            webDriver.get(url);

            // Assign driver to ThreadLocal
            driver.set(webDriver);

    }

    // Cleanup method to avoid memory leaks
    public void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
