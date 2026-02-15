package com.enterprise.utils;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.enterprise.driver.DriverManager;

public class WaitUtils {

    private WaitUtils() {
        // Private constructor — use static methods only
    }

    private static WebDriverWait getWait(int seconds) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds));
    }

    // Wait for element to be clickable
    public static WebElement waitForClickable(By locator, int seconds) {
        return getWait(seconds).until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Wait for element to be visible
    public static WebElement waitForVisible(By locator, int seconds) {
        return getWait(seconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait for element to disappear
    public static boolean waitForInvisible(By locator, int seconds) {
        return getWait(seconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // Wait for text to be present in element
    public static boolean waitForTextPresent(By locator, String text, int seconds) {
        return getWait(seconds).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // Wait for page title to contain text
    public static boolean waitForTitleContains(String titleText, int seconds) {
        return getWait(seconds).until(ExpectedConditions.titleContains(titleText));
    }

    // Wait for URL to contain text
    public static boolean waitForUrlContains(String urlText, int seconds) {
        return getWait(seconds).until(ExpectedConditions.urlContains(urlText));
    }

    // Wait for all elements to be visible
    public static List<WebElement> waitForAllVisible(By locator, int seconds) {
        return getWait(seconds).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // Wait for element to be present in DOM (may not be visible)
    public static WebElement waitForPresence(By locator, int seconds) {
        return getWait(seconds).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Wait with custom polling interval
    public static WebElement waitWithPolling(By locator, int timeoutSeconds, int pollingMillis) {
        WebDriverWait wait = new WebDriverWait(
            DriverManager.getDriver(),
            Duration.ofSeconds(timeoutSeconds),
            Duration.ofMillis(pollingMillis)
        );
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}