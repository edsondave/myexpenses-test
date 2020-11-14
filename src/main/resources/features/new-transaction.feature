Feature: Create new transaction

  Scenario: Create a basic transaction
    Given I'm on the new transaction form
    When I fill the new transaction form
    Then the transaction is created