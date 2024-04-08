@bustrip_sample
Feature: Check reservation status 
    Users can check their reservation status by entering the reservation token

Background: 
    Given the user is at the website "http://localhost:8080/"
    And the user has a reservation with the token d9a6be9f-7b90-4bae-8d01-896313ce28e3

Scenario: Check reservation status with valid token
    When the user enters the reservation token
    Then the user should be redirected to the reservation status page


Scenario: Check reservation status with invalid token
    When the user enters an invalid reservation token
    Then the user should see an "Invalid token" message