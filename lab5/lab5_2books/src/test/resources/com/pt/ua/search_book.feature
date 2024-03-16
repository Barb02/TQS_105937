@book_sample
Feature: as a user I can search books

Background: 
    Given some books are available on the library
        |   Title     |     Author          |  Published   |
        | Good Book   | John Doe            | 22-10-1990   |
        | Harry Potter| Shall Not Be Named  | 10-05-2001   |
        | Green Mile  | Louis Hamilton      | 08-08-2003   |
        | Green Book  | Rick Riordan        | 08-08-2010   |


Scenario: Search books by publication year
    When the customer searches for books published between 01-01-2000 and 31-12-2003
    Then 2 books should be found
        And book 1 should have the title 'Green Mile'
        And book 2 should have the title 'Harry Potter'

Scenario: Search books by author
    When the customer searches for books written by 'Louis Hamilton'
    Then the book 'Green Mile' should be found

Scenario: Search books by title
    When the costumer searches for books with the title starting with or containing 'Book'
    Then 2 books should be found
        And book 1 should have the title 'Good Book'
        And book 2 should have the title 'Green Book'

Scenario: Search books by title 2
    When the costumer searches for books with the title starting with or containing 'Gr'
    Then 2 books should be found
        And book 1 should have the title 'Green Book'
        And book 2 should have the title 'Green Mile'