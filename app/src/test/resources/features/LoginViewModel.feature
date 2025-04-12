Feature: LoginViewModel

  Scenario: Login with valid credentials
    Given a LoginViewModel instance
    And a username "aCupOfBitterCoffee"
    And a password "Abc@2025"
    And a token "validToken"
    When the user attempts to login
    Then the login API should be called

  Scenario: Login with invalid username
    Given a LoginViewModel instance
    And a username "wrongUser"
    And a password "Abc@2025"
    And a token "validToken"
    When the user attempts to login
    Then the login state should have error message "The username or password is incorrect, please re-enterp"

  Scenario: Login with missing token
    Given a LoginViewModel instance
    And a username "aCupOfBitterCoffee"
    And a password "Abc@2025"
    And a token ""
    When the user attempts to login
    Then the login state should have error message "Get token failed, please restart the app"
