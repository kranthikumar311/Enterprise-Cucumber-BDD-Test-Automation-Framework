package com.enterprise.driver;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

    // ThreadLocal gives each thread its own WebDriver instance
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // Private constructor — no one can create an instance of this class
    private DriverManager() 
    {
    }

    // Returns the WebDriver for the current thread
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Creates a new WebDriver based on browser name
    public static void initDriver(String browser, boolean headless) {

        if (driver.get() != null) {
            return; // Driver already exists for this thread
        }

        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--remote-allow-origins=*");
                webDriver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                webDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                edgeOptions.addArguments("--start-maximized");
                webDriver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        // Set global timeouts
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // Store in ThreadLocal
        driver.set(webDriver);
    }

    // Closes the browser and removes the driver from ThreadLocal
    public static void quitDriver()
    {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();  // Important: prevents memory leaks
        }
    }
}