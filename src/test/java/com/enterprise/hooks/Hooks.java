package com.enterprise.hooks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.enterprise.config.ConfigReader;
import com.enterprise.driver.DriverManager;
import com.enterprise.utils.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        logger.info("================================================");
        logger.info("Starting Scenario: {}", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());
        logger.info("================================================");

        String browser = ConfigReader.getBrowser();
        boolean headless = ConfigReader.isHeadless();

        DriverManager.initDriver(browser, headless);
    }

    @After
    public void tearDown(Scenario scenario) {

        if (scenario.isFailed()) {
            logger.error("Scenario FAILED: {}", scenario.getName());
            try {
                byte[] screenshot = ScreenshotUtils.getScreenshotAsBytes();
                scenario.attach(screenshot, "image/png", scenario.getName());
                ScreenshotUtils.takeScreenshot(scenario.getName().replace(" ", "_"));
                logger.info("Screenshot captured for failed scenario");
            } catch (Exception e) {
                logger.error("Could not capture screenshot: {}", e.getMessage());
            }
        }

        logger.info("================================================");
        logger.info("Finished Scenario: {}", scenario.getName());
        logger.info("Status: {}", scenario.getStatus());
        logger.info("================================================");

        DriverManager.quitDriver();
    }
}