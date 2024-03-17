@blazedemo_sample
Feature: Book a flight in the website BlazeDemo
    Users can book a flight trough the BlazeDemo website

Background: 
    Given the user is at the website "https://blazedemo.com"

Scenario: Book a flight
    Given the user is at BlazeDemo page
    When the user chooses "Paris" as the departure city
        And the user chooses "Buenos Aires" as the destination city
        And the user clicks on the button "Find Flights"
    Then the user should be redirected to the reserve page

    When the user clicks on the button "Choose This Flight"
    Then the user should be redirected to the purchase page

    When the user fills the form with valid values
        And the user clicks on the button "Purchase Flight"
    Then the user should be redirected to the confirmation page