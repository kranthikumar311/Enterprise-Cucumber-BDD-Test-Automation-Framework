package com.enterprise.stepdefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.enterprise.config.ConfigReader;
import com.enterprise.pages.GoogleSearchPage;
import io.cucumber.java.en.*;

public class GoogleSearch {

    private static final Logger logger = LogManager.getLogger(GoogleSearch.class);
    GoogleSearchPage googleSearchPage = new GoogleSearchPage();

    @Given("browser is open")
    public void browser_is_open() {
        logger.info("Browser is open and ready");
    }

    @And("user is on google search page")
    public void user_is_on_google_search_page() {
        googleSearchPage.openGooglePage(ConfigReader.getBaseUrl());
        logger.info("Navigated to Google search page");
    }

    @When("user enters a text in search box")
    public void user_enters_a_text_in_search_box() {
        googleSearchPage.enterSearchText("Selenium Automation");
        logger.info("Entered search text: Selenium Automation");
    }

    @And("hits enter")
    public void hits_enter() {
        googleSearchPage.pressEnter();
        logger.info("Pressed Enter key");
    }

    @Then("user is navigated to search results")
    public void user_is_navigated_to_search_results() {
        if (googleSearchPage.isResultsPageDisplayed("Selenium Automation")) {
            logger.info("Test Passed: Search results found");
        } else {
            logger.error("Test Failed: Search results not found");
        }
    }
}