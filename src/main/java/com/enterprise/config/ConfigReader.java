package com.enterprise.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;

    static {
        try {
            String env = System.getProperty("env", "qa");
            String fileName = "config/" + env + ".properties";
            logger.info("Loading configuration from classpath: {}", fileName);

            // Load from classpath instead of file system
            InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(fileName);

            if (inputStream == null) {
                throw new RuntimeException("Config file not found on classpath: " + fileName);
            }

            properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
            logger.info("Configuration loaded successfully for environment: {}", env);
        } catch (IOException e) {
            logger.fatal("Could not load config file: {}", e.getMessage());
            throw new RuntimeException("Could not load config file: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        logger.debug("Config property [{}] = {}", key, value);
        return value;
    }

    public static String getBrowser() {
        String cmdBrowser = System.getProperty("browser");
        if (cmdBrowser != null && !cmdBrowser.isEmpty()) {
            logger.info("Browser overridden from command line: {}", cmdBrowser);
            return cmdBrowser;
        }
        return properties.getProperty("browser", "chrome");
    }

    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public static boolean isHeadless() {
        String cmdHeadless = System.getProperty("headless");
        if (cmdHeadless != null && !cmdHeadless.isEmpty()) {
            logger.info("Headless mode overridden from command line: {}", cmdHeadless);
            return Boolean.parseBoolean(cmdHeadless);
        }
        return Boolean.parseBoolean(properties.getProperty("headless", "false"));
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "30"));
    }
}