package com.enterprise.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    // Static block runs once when the class is first loaded
    static {
        try {
            String env = System.getProperty("env", "qa");
            String filePath = "src/test/resources/config/" + env + ".properties";
            FileInputStream fis = new FileInputStream(filePath);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not load config file: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBrowser() {
        return properties.getProperty("browser", "chrome");
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "30"));
    }
}