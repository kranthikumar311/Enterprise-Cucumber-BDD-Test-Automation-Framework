package com.enterprise.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.enterprise.driver.DriverManager;

public class ScreenshotUtils {

    private ScreenshotUtils() {
        // Private constructor — use static methods only
    }

    // Get screenshot as byte array (for attaching to reports)
    public static byte[] getScreenshotAsBytes() {
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
        return ts.getScreenshotAs(OutputType.BYTES);
    }

    // Save screenshot to file and return the file path
    public static String takeScreenshot(String screenshotName) {
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Create timestamp for unique filename
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destination = "target/screenshots/" + screenshotName + "_" + timestamp + ".png";

        try {
            File destFile = new File(destination);
            FileUtils.copyFile(source, destFile);
            System.out.println("Screenshot saved: " + destination);
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
        }

        return destination;
    }

    // Take screenshot with default name
    public static String takeScreenshot() {
        return takeScreenshot("screenshot");
    }
}