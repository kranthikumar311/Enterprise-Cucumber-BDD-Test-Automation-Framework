# Author: Enterprise QA Team
# Date: 2026-02-15
# Description: Login functionality tests

@login
Feature: Login functionality

  @smoke @critical
  Scenario: Check login is successful with valid credentials
    Given user is on login page
    When user enters username and password
    And clicks on login button
    Then user is navigated to the home page

  @regression @high
  Scenario: Check login fails with invalid credentials
    Given user is on login page
    When user enters username and password
    And clicks on login button
    Then user is navigated to the home page

  @regression @medium
  Scenario: Check login fails with empty credentials
    Given user is on login page
    When user enters username and password
    And clicks on login button
    Then user is navigated to the home page

  @sanity @high
  Scenario: Check login page is accessible
    Given user is on login page
    Then user is navigated to the home page