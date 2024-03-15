Feature: as a user I can search books

Background: 
    Given some books are available on the library
        |   Title     |     Author          |    Published |
        | Good Book   | John Doe            | 22-10-1990   |
        | Harry Potter| Shall Not Be Named  | 10-05-2001   |
        | Green Mile  | Louis Hamilton      | 08-08-2008   |

Scenario: Search books by title
    When the customer searches for "Good Book"
    Then the the book "Good book" should be found
