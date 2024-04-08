@book_trip_sample
Feature: Book a trip in the website BusTrip
    Users can book a bus trip trough the BusTrip website

Background: 
    Given the user is at the website "http://localhost:8080/" to book a trip


Scenario: Book a trip reservation
    When the user chooses "Aveiro" as the origin city
        And the user chooses the first destination city
        And the user chooses "041320240800" as the start date
        And the user chooses "041320241600" as the end date
        And the user chooses "EUR" as the currency
        And the user clicks on the button to search
    Then the user should see a list of available trips with the link "Click here to book this trip" 

    When the user clicks on the link "Click here to book this trip" 
    Then the user should be redirected to the reservation page

    When the user fills the form with valid values
        And the user clicks on the button to book
    Then the user should be redirected to the reservation status page


Scenario: Book a trip reservation with an invalid number of seat value
    When the user chooses "Aveiro" as the origin city
        And the user chooses the first destination city
        And the user chooses "041320240800" as the start date
        And the user chooses "041320241600" as the end date
        And the user chooses "EUR" as the currency
        And the user clicks on the button to search
    Then the user should see a list of available trips with the link "Click here to book this trip" 

    When the user clicks on the link "Click here to book this trip" 
    Then the user should be redirected to the reservation page

    When the user fills the form with an invalid number of seats value
    Then an alert should appear with an error message
    And the button should be disabled