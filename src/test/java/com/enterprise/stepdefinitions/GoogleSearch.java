package com.enterprise.stepdefinitions;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GoogleSearch {

    WebDriver driver = null;

    @Given("browser is open")
    public void browser_is_open() 
    {
    	WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
        driver.manage().window().maximize();
    }

    @And("user is on google search page")
    public void user_is_on_google_search_page() {
        driver.navigate().to("https://google.com");
    }

    @When("user enters a text in search box")
    public void user_enters_a_text_in_search_box() {
        // 'q' is the name attribute for Google's search box
        driver.findElement(By.name("q")).sendKeys("Selenium Automation");
    }

    @And("hits enter")
    public void hits_enter() {
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
    }

    @Then("user is navigated to search results")
    public void user_is_navigated_to_search_results() {
        if(driver.getPageSource().contains("Selenium Automation")) {
            System.out.println("Test Passed: Results found.");
        }
        
        // Always close the browser at the end
        driver.close();
        driver.quit();
    }
}