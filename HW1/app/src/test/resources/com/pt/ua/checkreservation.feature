@check_sample
Feature: Check reservation status 
    Users can check their reservation status by entering the reservation token

Background: 
    Given the user is at the website "http://localhost:8080/" to check the status of a reservation

Scenario: Check reservation status with valid token
    When the user enters the reservation token "c1c4577b-0c71-40f6-b42a-d30461792708"
    And the user clicks on the button to check the reservation status
    Then the user should be redirected to the corresponding reservation status page

Scenario: Check reservation status with invalid token
    When the user enters the reservation token "1234"
    And the user clicks on the button to check the reservation status
    Then the user should see an "Invalid token!" message