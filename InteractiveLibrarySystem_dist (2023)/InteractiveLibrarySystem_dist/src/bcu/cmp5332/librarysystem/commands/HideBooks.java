package bcu.cmp5332.librarysystem.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;
import bcu.cmp5332.librarysystem.utils.Validator;

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
            Book book = library.getBookById(bookId);

            String validationMessage = Validator.validateBookForHiding(book);

            if (validationMessage != null) {
                throw new LibraryException(validationMessage);
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
