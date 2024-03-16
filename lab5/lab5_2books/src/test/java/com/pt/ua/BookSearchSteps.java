package com.pt.ua;
 
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.DataTableType;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

 
public class BookSearchSteps {
	Library library = new Library();
	List<Book> result = new ArrayList<>();
	SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	@ParameterType("[0-9]{2}-[0-9]{2}-[0-9]{4}")
	public Date iso8601Date(String dateString) throws ParseException {
		return formatter.parse(dateString);
	}

	@DataTableType
	public Book bookEntry(Map<String, String> tableEntry) throws ParseException{
		return new Book(
				tableEntry.get("Title"),
				tableEntry.get("Author"),
				formatter.parse( tableEntry.get("Published") ) );
	}

 
	@Given("some books are available on the library")
	public void addBooks(List<Book> books) {
		for (Book book : books) {
			library.addBook(book);
		}
	}
 
	@When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
	public void setSearchByDateParameters(Date from, Date to) {
		result = library.findBooksByDate(from, to);
	}

	@When("the customer searches for books written by {string}")
	public void setSearchByAuthorParameters(String author) {
		result = library.findBooksByAuthor(author);
	}

	@When("the costumer searches for books with the title starting with or containing {string}")
	public void setSearchByTitleParameters(String title) {
		result = library.findBooksByTitle(title);
	}
 
	@Then("{int} books should be found")
	public void verifyAmountOfBooksFound(int booksFound) {
		assertEquals(result.size(), booksFound);
	}
 
	@Then("book {int} should have the title {string}")
	public void verifyBookAtPosition(int position, String title) {
		assertEquals(result.get(position - 1).getTitle(), title);
	}

	@Then("the book {string} should be found")
	public void verifyBookFound(String title){
		assertEquals(result.get(0).getTitle(), title);
	}

}
