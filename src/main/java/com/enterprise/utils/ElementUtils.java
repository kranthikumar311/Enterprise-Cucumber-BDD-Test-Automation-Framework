package com.enterprise.utils;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.enterprise.driver.DriverManager;

public class ElementUtils {

    private ElementUtils() {
        // Private constructor — use static methods only
    }

    private static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    // Scroll to an element using JavaScript
    public static void scrollToElement(By locator) {
        WebElement element = getDriver().findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    // Click using JavaScript (useful when normal click is intercepted)
    public static void jsClick(By locator) {
        WebElement element = getDriver().findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    // Highlight an element (useful for debugging)
    public static void highlightElement(By locator) {
        WebElement element = getDriver().findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(
            "arguments[0].style.border='3px solid red'; arguments[0].style.backgroundColor='yellow';",
            element
        );
    }

    // Get all options text from a dropdown or list
    public static List<WebElement> getAllElements(By locator) {
        return getDriver().findElements(locator);
    }

    // Check if element exists in DOM (without waiting)
    public static boolean isElementPresent(By locator) {
        return getDriver().findElements(locator).size() > 0;
    }

    // Get attribute value of an element
    public static String getAttribute(By locator, String attribute) {
        return getDriver().findElement(locator).getAttribute(attribute);
    }

    // Clear a text field and type new text
    public static void clearAndType(By locator, String text) {
        WebElement element = WaitUtils.waitForVisible(locator, 10);
        element.clear();
        element.sendKeys(text);
    }

    // Scroll to top of page
    public static void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, 0);");
    }

    // Scroll to bottom of page
    public static void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    // Get count of elements matching a locator
    public static int getElementCount(By locator) {
        return getDriver().findElements(locator).size();
    }
}