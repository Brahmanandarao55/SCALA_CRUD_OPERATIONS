Feature: Person operations

  Scenario: Create person table
    Given a person repository
    When I create the person table
    Then the table should be created

  Scenario: Insert person from file
    Given a person repository
    When I insert persons from the file "data.csv"
    Then the persons should be inserted

  Scenario: Get person by ID
    Given a person repository with a person having ID 1
    When I get the person by ID 1
    Then the person's details should be retrieved

  Scenario: Update person details
    Given a person repository with a person having ID 1
    When I update the person details with ID 1
    Then the person's details should be updated

  Scenario: Delete person by ID
    Given a person repository with a person having ID 1
    When I delete the person by ID 1
    Then the person should be deleted

  Scenario: Retrieve all persons
    Given a person repository with persons
    When I retrieve all persons
    Then all persons' details should be retrieved
