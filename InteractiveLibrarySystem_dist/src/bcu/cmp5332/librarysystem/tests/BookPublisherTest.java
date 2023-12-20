package bcu.cmp5332.librarysystem.tests;

import bcu.cmp5332.librarysystem.commands.*;
import bcu.cmp5332.librarysystem.main.*;
import bcu.cmp5332.librarysystem.data.*;
import bcu.cmp5332.librarysystem.model.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class BookPublisherTest {

	@Test
	void addBookTest() throws LibraryException, IOException {
	    // Load library data from files
	    Library library = LibraryData.load();
        int maxId = 0;
        // Find current highest bookId
    	if (library.getBooks().size() > 0) {
    		int lastIndex = library.getBooks().size() - 1;
            maxId = library.getBooks().get(lastIndex).getId();
    	}
    	// Add new book - with publisher
		Command add = new AddBook("The Great Gatsby", "F. Scott Fitzgerald", "1925", "Charles Scribner's Sons");
        add.execute(library, LocalDate.now());
        Book book = library.getBookByID(maxId + 1); // New book
		assertEquals(maxId + 1, book.getId());
		assertEquals(book.getTitle(), "The Great Gatsby");
		assertEquals(book.getAuthor(), "F. Scott Fitzgerald");
		assertEquals(book.getPublicationYear(), "1925");
		assertEquals(book.getPublisher(), "Charles Scribner's Sons"); // Check if publisher is added correctly
	}

	@Test
	void storeBookTest() throws LibraryException, IOException {
		// Load library data from files
	    Library library = LibraryData.load();
	    // Add new book - with publisher
	  	Command add = new AddBook("The Great Gatsby", "F. Scott Fitzgerald", "1925", "Charles Scribner's Sons");
	    add.execute(library, LocalDate.now());
	    // Store changes
		LibraryData.store(library);
		// Load new library data from files
        Library newLibrary = LibraryData.load();
        int maxId = 0;
        // Find current highest bookId
    	if (newLibrary.getBooks().size() > 0) {
    		int lastIndex = newLibrary.getBooks().size() - 1;
            maxId = newLibrary.getBooks().get(lastIndex).getId();
    	}
    	Book book = newLibrary.getBookByID(maxId); // New book
        assertEquals(maxId, book.getId());
		assertEquals(book.getTitle(), "The Great Gatsby");
		assertEquals(book.getAuthor(), "F. Scott Fitzgerald");
		assertEquals(book.getPublicationYear(), "1925");
		assertEquals(book.getPublisher(), "Charles Scribner's Sons"); // Check if publisher was saved correctly
	}
}
