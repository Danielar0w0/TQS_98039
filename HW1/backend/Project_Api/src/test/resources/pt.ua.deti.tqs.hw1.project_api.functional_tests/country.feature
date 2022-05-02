Feature: Country

  Scenario: Search for a country
    Given I am on the Country page
    When I search for "USA"
    Then the search should start with "USA"

  Scenario: Search for a country on a certain date
    Given I am on the Recent page
    When I search for "USA" on date "02/05/2021"
    Then the search should start with "USA-02/05/2021"