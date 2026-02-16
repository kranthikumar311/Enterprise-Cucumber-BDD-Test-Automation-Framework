# Enterprise Cucumber BDD Test Automation Framework

A production-grade, enterprise-ready test automation framework built with **Cucumber BDD**, **Selenium WebDriver**, and **Java 17+**. Designed for scalability, parallel execution, and seamless CI/CD integration.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17+ (LTS) | Programming language |
| Selenium WebDriver | 4.25.0 | Browser automation |
| Cucumber | 7.18.1 | BDD framework |
| JUnit 5 | 5.10.3 | Test platform engine |
| Log4j2 | 2.23.1 | Logging framework |
| ExtentReports | 1.14.0 | HTML test reporting |
| WebDriverManager | 5.9.2 | Automatic driver management |
| Maven | 3.9+ | Build and dependency management |
| PicoContainer | 7.18.1 | Dependency injection |

---

## Project Structure

```
CucumberJava/
│
├── src/main/java/com/enterprise/
│   ├── config/
│   │   └── ConfigReader.java              # Reads environment-specific properties
│   ├── driver/
│   │   └── DriverManager.java             # ThreadLocal WebDriver management
│   ├── pages/
│   │   ├── BasePage.java                  # Base class with reusable page methods
│   │   ├── GoogleSearchPage.java          # Google Search page object
│   │   └── LoginPage.java                # Login page object
│   └── utils/
│       ├── WaitUtils.java                 # Explicit wait utilities
│       ├── ScreenshotUtils.java           # Screenshot capture utilities
│       └── ElementUtils.java              # Safe element interaction utilities
│
├── src/test/java/com/enterprise/
│   ├── hooks/
│   │   └── Hooks.java                     # @Before / @After lifecycle hooks
│   ├── runners/
│   │   └── TestRunner.java                # JUnit 5 Suite runner
│   └── stepdefinitions/
│       ├── GoogleSearch.java              # Google search step definitions
│       └── LoginClass.java               # Login step definitions
│
├── src/test/resources/
│   ├── features/
│   │   ├── demo.feature                   # Login feature scenarios
│   │   └── GoogleSearch.feature           # Search feature scenarios
│   ├── config/
│   │   ├── qa.properties                  # QA environment configuration
│   │   └── staging.properties             # Staging environment configuration
│   ├── junit-platform.properties          # Cucumber + parallel execution config
│   ├── extent.properties                  # ExtentReports configuration
│   ├── extent-config.xml                  # ExtentReports theme and appearance
│   └── log4j2.xml                         # Log4j2 logging configuration
│
├── .github/workflows/test.yml             # GitHub Actions CI/CD pipeline
├── Jenkinsfile                            # Jenkins CI/CD pipeline
├── Dockerfile                             # Containerized test execution
├── pom.xml                                # Maven configuration
├── .gitignore                             # Git ignore rules
└── README.md                              # This file
```

---

## Design Patterns

### Page Object Model (POM)

Every web page is represented by a Java class. Locators and page actions are encapsulated in the page class, keeping step definitions clean and readable.

```
Feature File → Step Definition → Page Object → BasePage → Selenium WebDriver
  (WHAT)          (GLUE)          (HOW)        (SAFE)      (EXECUTES)
```

### ThreadLocal Driver Management

`DriverManager` uses `ThreadLocal<WebDriver>` to provide each test thread with its own isolated browser instance. This enables safe parallel execution without shared state.

### Configuration-Driven Execution

All settings (browser, URLs, timeouts, headless mode) are externalized to `.properties` files. Command-line parameters override file defaults, enabling flexible CI/CD pipelines.

---

## Prerequisites

Before running the framework, ensure you have the following installed:

- **Java JDK 17 or higher** — Download from [Adoptium](https://adoptium.net/temurin/releases/?version=17)
- **Maven 3.9+** — Download from [Maven](https://maven.apache.org/download.cgi)
- **Chrome / Firefox / Edge** — At least one browser installed
- **Git** — For version control

Verify your installations:

```bash
java -version
mvn -version
git --version
```

---

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-org/CucumberJava.git
cd CucumberJava
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

### 3. Run Smoke Tests

```bash
mvn clean test
```

This runs `@smoke` tagged scenarios on Chrome in QA environment by default.

---

## Running Tests

### Basic Commands

```bash
# Run smoke tests (default)
mvn clean test

# Run using a specific profile
mvn clean test -Psmoke
mvn clean test -Pregression
mvn clean test -Psanity
mvn clean test -Pcritical
mvn clean test -Pall
```

### Environment Selection

```bash
# QA environment (default)
mvn clean test -Denv=qa

# Staging environment
mvn clean test -Denv=staging
```

### Browser Selection

```bash
# Chrome (default)
mvn clean test -Dbrowser=chrome

# Firefox
mvn clean test -Dbrowser=firefox

# Edge
mvn clean test -Dbrowser=edge
```

### Headless Mode

```bash
# Run without opening a visible browser (for CI/CD)
mvn clean test -Dheadless=true
```

### Tag-Based Execution

```bash
# Run specific tags
mvn clean test -Dcucumber.filter.tags="@login"
mvn clean test -Dcucumber.filter.tags="@search"
mvn clean test -Dcucumber.filter.tags="@critical"

# Combine tags with AND
mvn clean test -Dcucumber.filter.tags="@login and @critical"

# Combine tags with OR
mvn clean test -Dcucumber.filter.tags="@smoke or @regression"

# Exclude tags
mvn clean test -Dcucumber.filter.tags="not @wip"
```

### Full CI/CD Command Example

```bash
mvn clean test -Pregression -Denv=staging -Dbrowser=chrome -Dheadless=true
```

---

## Parallel Execution

Parallel execution is enabled by default with 4 threads. Configuration is in `junit-platform.properties`:

```properties
cucumber.execution.parallel.enabled=true
cucumber.execution.parallel.config.strategy=fixed
cucumber.execution.parallel.config.fixed.parallelism=4
cucumber.execution.parallel.config.fixed.max-pool-size=4
```

**Recommended thread count based on machine resources:**

| Machine | RAM | Recommended Threads |
|---|---|---|
| Laptop | 8 GB | 2–3 |
| Desktop | 16 GB | 4–6 |
| CI/CD Server | 32 GB+ | 8–12 |

To disable parallel execution:

```properties
cucumber.execution.parallel.enabled=false
```

---

## Tagging Strategy

### Tag Categories

| Tag Type | Examples | Purpose |
|---|---|---|
| Suite | `@smoke`, `@regression`, `@sanity` | Test suite classification |
| Feature | `@login`, `@search`, `@payment` | Feature area |
| Priority | `@critical`, `@high`, `@medium`, `@low` | Test importance |
| Status | `@wip`, `@skip`, `@flaky` | Current test state |

### Tagging Rules

1. Every scenario **must** have at least one suite tag (`@smoke`, `@regression`, or `@sanity`)
2. Every scenario **must** have a priority tag (`@critical`, `@high`, `@medium`, `@low`)
3. Feature-level tags go on the `Feature:` line (inherited by all scenarios)
4. Never run `@wip` tests in CI/CD pipelines
5. Investigate and fix `@flaky` tests promptly

### Maven Profiles

| Profile | Command | Runs |
|---|---|---|
| smoke | `mvn test -Psmoke` | `@smoke` scenarios |
| regression | `mvn test -Pregression` | `@regression` scenarios |
| sanity | `mvn test -Psanity` | `@sanity` scenarios |
| critical | `mvn test -Pcritical` | `@critical` scenarios |
| login | `mvn test -Plogin` | `@login` scenarios |
| search | `mvn test -Psearch` | `@search` scenarios |
| all | `mvn test -Pall` | All scenarios |

---

## Reports

The framework generates multiple report formats after every test run.

### ExtentReport (Primary — for stakeholders)

**Location:** `target/ExtentReports/ExtentReport.html`

Features: dashboard with pie charts, pass/fail breakdown, step-level details, screenshots on failure, system info metadata.

### Cucumber HTML Report

**Location:** `target/CucumberReports/CucumberReport.html`

Cucumber's built-in HTML report with scenario and step details.

### Cucumber JSON Report (for CI/CD)

**Location:** `target/CucumberReports/cucumber.json`

JSON format consumed by Jenkins Cucumber plugin, GitLab, and other CI/CD dashboards.

### Log Files

**Location:** `target/logs/test-execution.log`

Timestamped logs with thread names, log levels, and class names. Configured via `log4j2.xml`.

### Screenshots

**Location:** `target/screenshots/`

Automatically captured on test failure and attached to the ExtentReport.

---

## Configuration Files

### Environment Properties (`src/test/resources/config/`)

```properties
# qa.properties
browser=chrome
headless=false
base.url=https://www.google.com
implicit.wait=10
page.load.timeout=30
explicit.wait=15
screenshot.on.failure=true
```

**Adding a new environment:** Create a new file (e.g., `prod.properties`) in the `config/` folder, then run with `-Denv=prod`.

### Configuration Priority

Command-line parameters always override properties file values:

```
Command line (-Dbrowser=firefox)  →  HIGHEST priority
Properties file (browser=chrome)  →  DEFAULT
```

---

## Adding New Tests

### Step 1: Create a Feature File

Create a new `.feature` file in `src/test/resources/features/`:

```gherkin
@checkout
Feature: Checkout functionality

  @smoke @critical
  Scenario: Successful checkout with valid payment
    Given user has items in cart
    When user proceeds to checkout
    And user enters payment details
    Then order confirmation is displayed
```

### Step 2: Create a Page Object

Create a new class in `com.enterprise.pages`:

```java
package com.enterprise.pages;

import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {

    private By cartTotal = By.id("cart-total");
    private By checkoutButton = By.id("checkout-btn");
    private By confirmationMessage = By.id("confirmation");

    public void clickCheckout() {
        click(checkoutButton);
    }

    public String getCartTotal() {
        return getText(cartTotal);
    }

    public boolean isConfirmationDisplayed() {
        return isDisplayed(confirmationMessage);
    }
}
```

### Step 3: Create Step Definitions

Create a new class in `com.enterprise.stepdefinitions`:

```java
package com.enterprise.stepdefinitions;

import com.enterprise.pages.CheckoutPage;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CheckoutSteps {

    private static final Logger logger = LogManager.getLogger(CheckoutSteps.class);
    CheckoutPage checkoutPage = new CheckoutPage();

    @When("user proceeds to checkout")
    public void user_proceeds_to_checkout() {
        checkoutPage.clickCheckout();
        logger.info("User proceeded to checkout");
    }

    @Then("order confirmation is displayed")
    public void order_confirmation_is_displayed() {
        assert checkoutPage.isConfirmationDisplayed();
        logger.info("Order confirmation displayed successfully");
    }
}
```

### Step 4: Run

```bash
mvn clean test -Dcucumber.filter.tags="@checkout"
```

---

## CI/CD Integration

### Jenkins

The `Jenkinsfile` in the project root provides a parameterized pipeline with environment, browser, profile, and headless mode selection. Required Jenkins plugins: Cucumber Reports, HTML Publisher.

### GitHub Actions

The `.github/workflows/test.yml` workflow runs automatically on push/PR to main and supports manual triggers with parameters. Reports are uploaded as downloadable artifacts.

### Docker

```bash
# Build the image
docker build -t cucumber-tests .

# Run smoke tests
docker run cucumber-tests

# Run regression on staging
docker run cucumber-tests -Pregression -Denv=staging -Dheadless=true
```

---

## Logging

The framework uses Log4j2 with three output destinations:

| Destination | Location | Purpose |
|---|---|---|
| Console | Terminal output | Real-time monitoring |
| File Log | `target/logs/test-execution.log` | Post-run debugging |
| Rolling Log | `target/logs/test-rolling.log` | Archived logs (auto-rotates at 10MB) |

### Log Levels

| Level | Usage |
|---|---|
| `DEBUG` | Detailed developer info |
| `INFO` | Normal test flow |
| `WARN` | Unexpected but non-breaking issues |
| `ERROR` | Test failures |
| `FATAL` | Framework cannot continue |

### Usage in Code

```java
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

private static final Logger logger = LogManager.getLogger(YourClass.class);

logger.info("User navigated to {}", pageName);
logger.error("Element not found: {}", locator);
```

---

## Utility Classes

### WaitUtils

Static explicit wait methods usable from anywhere in the framework.

```java
WaitUtils.waitForClickable(By.id("submit"), 10);
WaitUtils.waitForVisible(By.id("message"), 15);
WaitUtils.waitForTitleContains("Dashboard", 10);
WaitUtils.waitForUrlContains("/home", 10);
```

### ScreenshotUtils

Screenshot capture for debugging and reporting.

```java
ScreenshotUtils.takeScreenshot("login_page");         // Saves to file
ScreenshotUtils.getScreenshotAsBytes();                // For report attachment
```

### ElementUtils

Safe element interactions with JavaScript fallbacks.

```java
ElementUtils.jsClick(By.id("button"));                 // JS click (bypasses overlays)
ElementUtils.scrollToElement(By.id("footer"));         // Scroll into view
ElementUtils.highlightElement(By.id("field"));         // Visual debugging
ElementUtils.isElementPresent(By.id("popup"));         // Safe existence check
```

---

## Troubleshooting

### Common Issues

**Browser not launching:**
Ensure the browser is installed and WebDriverManager has internet access to download the driver.

**Config file not found:**
Check that `src/test/resources/config/qa.properties` exists and the `-Denv` value matches a valid file name.

**Parallel execution failures:**
Reduce thread count in `junit-platform.properties` or increase machine resources.

**Eclipse "Cannot find junit.framework.TestCase":**
Run tests via `mvn clean test` in the terminal instead of Eclipse's "Run As → JUnit Test". Or change the Eclipse run configuration's test runner to JUnit 5.

**Stale Element Reference:**
Use `WaitUtils` explicit waits instead of `Thread.sleep()` or implicit waits.

---

## Contributing

1. Create a feature branch: `git checkout -b feature/your-feature`
2. Follow the existing package structure and naming conventions
3. Add appropriate tags to all new scenarios
4. Ensure all tests pass: `mvn clean test -Pall`
5. Submit a pull request to `develop` branch

---

## License

This project is proprietary. All rights reserved.
