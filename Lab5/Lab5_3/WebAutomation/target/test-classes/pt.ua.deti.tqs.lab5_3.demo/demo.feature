Feature: Buy Trip
  Scenario: : Successful buy
    Given I navigate to "https://blazedemo.com/"
    When I selected the ports "Portland" (departure) and "New York" (destination)
    And I choose the flight 3
    And I purchase the flight
    Then I should see the message "Thank you for your purchase today!"