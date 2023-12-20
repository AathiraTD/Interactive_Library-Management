package bcu.cmp5332.librarysystem.commands;

import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

public class HideBooks implements Command {
	private List<Integer> bookIds = null;

    // Constructor to initialize the command with a specific book ID
	public HideBooks(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
    	for (int bookId : bookIds) {
    		Book book = library.getBookByID(bookId);

	        // Check if the book exists in the library
	        if (book == null) {
	            throw new LibraryException("Book with ID " + bookId + " not found.");
	        }
	
	        // Check if the book is already hidden
	        if (book.isDeleted()) {
	            throw new LibraryException("Book with ID " + bookId + " is already hidden.");
	        }
	
	        // Hide the book
	        book.hideBook();
	        System.out.println("Book with ID " + bookId + " has been successfully hidden.");
    	}
    }
}
