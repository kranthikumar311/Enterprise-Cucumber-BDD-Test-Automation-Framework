package com.enterprise.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.enterprise.stepdefinitions,com.enterprise.hooks")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/HtmlReports/report.html")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@smoketest")
public class TestRunner {
}