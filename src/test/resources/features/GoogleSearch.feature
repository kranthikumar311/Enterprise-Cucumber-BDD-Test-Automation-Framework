# Author: Enterprise QA Team
# Description: Google search functionality tests

@search
Feature: Google search functionality

  @smoke @critical
  Scenario: Validate google search is working
    Given browser is open
    And user is on google search page
    When user enters a text in search box
    And hits enter
    Then user is navigated to search results

  @regression @high
  Scenario: Validate google search returns relevant results
    Given browser is open
    And user is on google search page
    When user enters a text in search box
    And hits enter
    Then user is navigated to search results