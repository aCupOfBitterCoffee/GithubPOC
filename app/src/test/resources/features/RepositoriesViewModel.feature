Feature: RepositoriesViewModel

  Scenario: User does not log in and search
    Given User is not logged in
    When The search type is "10 popular"
    Then The response success

  Scenario: User does not log in and search
    Given User is not logged in
    When The search type is Language and language is "Kotlin"
    Then The response success

  Scenario: User logged in and search
    Given User has logged in
    When The search type is "My Repos"
    Then The response success
