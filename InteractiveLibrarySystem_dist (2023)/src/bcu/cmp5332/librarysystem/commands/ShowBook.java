package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import java.time.LocalDate;

/**
 * The ShowBook class represents a command to display detailed information about a book.
 * It allows showing book details to the user.
 */
public class ShowBook implements Command {

    private final int bookId;

    /**
     * Constructs a ShowBook command.
     *
     * @param bookId ID of the book to be displayed.
     */
    public ShowBook(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Executes the show book command.
     * It displays detailed information about a book if it exists.
     *
     * @param library         The library where the book data is managed.
     * @param currentDate     The current date.
     * @param messageDisplayer The message displayer for displaying messages.
     * @throws LibraryException if any validation fails during the execution.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        Book book = library.getBookById(bookId);
        if (book != null) {
            messageDisplayer.displayMessage(book.getDetailsShort());
        } else {
            messageDisplayer.displayMessage("Book #" + bookId + " not found.");
        }
    }
}
