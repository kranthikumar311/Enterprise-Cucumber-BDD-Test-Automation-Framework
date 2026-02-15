package com.enterprise.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    // -------- Locators --------
    private By usernameField = By.id("username");
    private By passwordField = By.id("password");
    private By loginButton = By.id("loginBtn");
    private By homePageHeader = By.id("homeHeader");

    // -------- Page Actions --------

    public void openLoginPage(String url) {
        navigateTo(url);
    }

    public void enterUsername(String username) {
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isHomePageDisplayed() {
        return isDisplayed(homePageHeader);
    }
}