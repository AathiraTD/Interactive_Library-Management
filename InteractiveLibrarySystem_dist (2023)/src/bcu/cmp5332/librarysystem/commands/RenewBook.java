package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The RenewBook class represents a command to renew a book by a patron.
 * It allows extending the due date for a book that is currently on loan to a patron.
 */
public class RenewBook implements Command {

    private final int patronId; // ID of the patron who is renewing the book
    private final int bookId; // ID of the book to be renewed

    /**
     * Constructs a RenewBook command.
     *
     * @param patronId ID of the patron renewing the book.
     * @param bookId   ID of the book to be renewed.
     */
    public RenewBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    /**
     * Executes the renew book command.
     * It allows a patron to renew a book if they have borrowed it and it is currently on loan to them.
     *
     * @param library         The library where the book and patron data is managed.
     * @param currentDate     The current date.
     * @param messageDisplayer The message displayer for displaying messages (supports both GUI and CLI).
     * @throws LibraryException if any validation fails during the renewing process.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        Patron patron = library.getPatronById(patronId);
        Book book = library.getBookById(bookId);

        // Check if the book and patron are valid
        if (patron == null || book == null) {
            throw new LibraryException("Book or Patron not found.");
        }

        // Check if the book is currently on loan to this patron
        if (book.isOnLoan() && patron.getBorrowedBooks().contains(book)) {
            // Find the corresponding loan
            Loan loan = library.findLoan(bookId, patronId);
            if (loan != null) {
                // Set the new due date to 2 weeks from the current date
                LocalDate newDueDate = currentDate.plusWeeks(2);
                loan.setDueDate(newDueDate);

                try {
                    LibraryData.store(library);
                } catch (IOException e) {
                    // Handle the IOException
                    e.printStackTrace();
                }

                // Display the message using the MessageDisplayer
                String message = "Book #" + book.getId() + " renewed by Patron #" + patron.getId() + ". New due date: " + newDueDate;
                messageDisplayer.displayMessage(message);
            } else {
                throw new LibraryException("Loan record not found for this book and patron.");
            }
        } else {
            throw new LibraryException("This book is either not on loan or not borrowed by this patron.");
        }
    }
}
