package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Book.Status;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

/**
 * The HideBooks class represents a command to hide one or more books in the library.
 * This command marks the specified books as hidden, making them unavailable for borrowing.
 */
public class HideBooks implements Command {
    private List<Integer> bookIds = null;

    /**
     * Constructs a HideBooks command with a list of book IDs to be hidden.
     *
     * @param bookIds The list of book IDs to be hidden.
     */
    public HideBooks(List<Integer> bookIds) {
        this.bookIds = bookIds;
    }

    /**
     * Executes the hiding process for the specified books.
     *
     * @param library      The library where the books are hidden.
     * @param currentDate  The current date.
     * @param messageDisplayer The message displayer for showing success and error messages.
     * @throws LibraryException If there are issues with hiding the books.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        for (int bookId : bookIds) {
            Book book = library.getBookByID(bookId);

            // Check if the book exists in the library
            if (book == null) {
                // Display an error message and continue to the next book
            	throw new LibraryException("Book with ID " + bookId + " not found.");
            }

            // Check if the book is already hidden
            if (book.isDeleted()) {
                // Display an error message and continue to the next book
            	throw new LibraryException("Book with ID " + bookId + " is already hidden.");
            }

            // Check if the book is currently on loan
            if (book.getStatus() == Status.LOANED_OUT) {
                // Display an error message and continue to the next book
            	throw new LibraryException("Book with ID " + bookId + " is currently on loan.");
            }

            // Hide the book
            book.hideBook();

            try {
                // Persist the changes in the library data
                LibraryData.store(library);
            } catch (IOException e) {
                // Handle IOExceptions during data storage
            	throw new LibraryException("Error saving the library data: " + e.getMessage());
            }

            // Display a success message
            messageDisplayer.displayMessage("Book with ID " + bookId + " has been successfully hidden.");
        }
    }
}
