package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ListBooks implements Command {

	@Override
	public void execute(Library library, LocalDate currentDate) throws LibraryException {
	    List<Book> books = library.getBooks().stream()  // Convert the list of books to a stream
	                              .filter(book -> !book.isDeleted()) // Filter out deleted books
	                              .collect(Collectors.toList()); // Collect the filtered stream back into a list

	    for (Book book : books) {
	        System.out.println(book.getDetailsShort());
	    }
	    System.out.println(books.size() + " book(s) available");
	}
}
 