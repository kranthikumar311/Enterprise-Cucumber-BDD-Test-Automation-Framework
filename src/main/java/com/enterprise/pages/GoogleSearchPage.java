package com.enterprise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class GoogleSearchPage extends BasePage {

    // -------- Locators --------
    // All element locators are defined here in one place
    private By searchBox = By.name("q");
    private By searchButton = By.name("btnK");

    // -------- Page Actions --------

    public void openGooglePage(String url) {
        navigateTo(url);
    }

    public void enterSearchText(String searchText) {
        type(searchBox, searchText);
    }

    public void pressEnter() {
        driver.findElement(searchBox).sendKeys(Keys.ENTER);
    }

    public void clickSearchButton() {
        click(searchButton);
    }

    public boolean isResultsPageDisplayed(String expectedText) {
        return getPageSource().contains(expectedText);
    }
}