#Author: tgajski
@smokeTest
Feature: Huddle API endpoints testing

  Scenario: List Users
    Given the endpoint "https://reqres.in/api/users"
    When a GET request is sent
    Then the response code is 200
    And the response contains a list of all users

  Scenario: Register Unsuccessful
    Given the endpoint "https://reqres.in/api/register"
    When a POST request is sent
    And undefined user data is sent
    Then the response code is 400

  Scenario Outline: Delete User
    Given the endpoint "https://reqres.in/api/users/<userID>"
    When a DELETE request is sent
    Then the response code is 204

  Scenario Outline: Delayed Response
    Given the endpoint "https://reqres.in/api/users?delay=<delayValue>"
    When a GET request is sent
    Then the response code is 200
    And the response contains a list of all users

    Examples: 
      | userID     |
      |          2 |
      | delayValue |
      |          5 |
      |         10 |
