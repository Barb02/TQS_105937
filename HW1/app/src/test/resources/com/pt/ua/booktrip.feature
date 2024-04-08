@bustrip_sample
Feature: Book a trip in the website BusTrip
    Users can book a bus trip trough the BusTrip website

Background: 
    Given the user is at the website "http://localhost:8080/"


Scenario: Book a trip reservation
    Given the user is at BusTrip page
    When the user chooses "Aveiro" as the departure city
        And the user chooses "Porto" as the destination city
        And the user chooses 2024-04-13T08:00 as the start date
        And the user chooses 2024-04-13T16:00 as the end date
        And the user chooses "EUR" as the currency
        And the user clicks on the button "Search"
    Then the user should see a list of available trips

    When the user clicks on the button "Click here to book this trip"
    Then the user should be redirected to the reservation page

    When the user fills the form with valid values
        And the user clicks on the button "Book"
    Then the user should be redirected to the reservation status page


Scenario: Book a trip reservation with an invalid number of seat value
    Given the user is at BusTrip page
    When the user chooses "Aveiro" as the departure city
        And the user chooses "Porto" as the destination city
        And the user chooses 2024-04-13T08:00 as the start date
        And the user chooses 2024-04-13T16:00 as the end date
        And the user chooses "EUR" as the currency
        And the user clicks on the button "Search"
    Then the user should see a list of available trips

    When the user clicks on the button "Click here to book this trip"
    Then the user should be redirected to the reservation page

    When the user fills the form with an invalid number of seat value
    Then an alert should appear with the message "There are not enough seats available for this trip. Please choose a smaller number of tickets."
        
    When the user clicks on the button "Book"
    Then the user should stay on the same page