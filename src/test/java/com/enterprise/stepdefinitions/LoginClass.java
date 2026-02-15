package com.enterprise.stepdefinitions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.enterprise.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginClass {

    private static final Logger logger = LogManager.getLogger(LoginClass.class);
    LoginPage loginPage = new LoginPage();

    @Given("user is on login page")
    public void user_is_on_login_page() {
        logger.info("User is on the login page");
    }

    @When("user enters username and password")
    public void user_enters_username_and_password() {
        logger.info("User enters Username and Password");
    }

    @And("clicks on login button")
    public void clicks_on_login_button() {
        logger.info("User clicks on the Login Button");
    }

    @Then("user is navigated to the home page")
    public void user_is_navigated_to_the_home_page() {
        logger.info("User is Navigated to the Home Page");
    }
}