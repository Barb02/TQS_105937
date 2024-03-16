package com.pt.ua;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
 
public class Library {
	private final List<Book> store = new ArrayList<>();
 
	public void addBook(final Book book) {
		store.add(book);
	}
 
	public List<Book> findBooksByDate(final Date from, final Date to) {
		Calendar end = Calendar.getInstance();
		end.setTime(to);
		end.roll(Calendar.YEAR, 1);
 
		return store.stream().filter(book -> {
			return from.before(book.getPublished()) && end.getTime().after(book.getPublished());
		}).sorted(Comparator.comparing(Book::getPublished).reversed()).collect(Collectors.toList());
	}

	public List<Book> findBooksByAuthor(String author) {
        return store.stream().filter(book -> book.getAuthor().equalsIgnoreCase(author)).collect(Collectors.toList());
	}


	public List<Book> findBooksByTitle(String search) {
		return Stream.concat(
			store.stream().filter(book -> book.getTitle().toLowerCase().startsWith(search.toLowerCase())),
			store.stream().filter(book -> book.getTitle().toLowerCase().contains(search.toLowerCase()))
		)
		.distinct()
		.sorted(Comparator.comparing(Book::getTitle)) 
		.collect(Collectors.toList());
	}
	
}
