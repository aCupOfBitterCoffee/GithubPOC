Feature: Log out

Scenario: Login with valid credentials
    Given User has logged in for home
    When User click logout
    Then The session will be cleared