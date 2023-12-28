package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The ReturnBook class represents a command to return a book by a patron.
 * It allows a patron to return a book that they have borrowed, marking the loan as closed.
 */
public class ReturnBook implements Command {

    private final int patronId; // ID of the patron who is returning the book
    private final int bookId;   // ID of the book to be returned

    /**
     * Constructs a ReturnBook command.
     *
     * @param patronId ID of the patron returning the book.
     * @param bookId   ID of the book to be returned.
     */
    public ReturnBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    /**
     * Executes the return book command.
     * It allows a patron to return a book if they have borrowed it, marking the loan as closed.
     *
     * @param library         The library where the book, patron, and loan data is managed.
     * @param currentDate     The current date.
     * @param messageDisplayer The message displayer for displaying messages.
     * @throws LibraryException if any validation fails during the return process.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        Book book = library.getBookByID(bookId);

        // Check if the book and patron are valid
        if (patron == null || book == null) {
            messageDisplayer.displayMessage("Book or Patron not found.");
            return;
        }

        // Check if the patron has borrowed the book
        if (patron.getBorrowedBooks().contains(book)) {
            patron.returnBook(book); // Remove the book from the patron's borrowed list

            // Find the corresponding loan and mark it as closed
            Loan loan = library.findLoan(bookId, patronId);
            if (loan != null) {
                loan.setReturnDate(currentDate); // Set the return date
                loan.setStatus("closed");        // Mark the loan as closed

                // Clear temporary loan ID and return the book to the library's collection
                book.clearTemporaryLoanId();
                book.returnToLibrary();

                try {
                    LibraryData.store(library); // Persist changes to data files (books.txt, patrons.txt, loans.txt)
                } catch (IOException e) {
                    // Handle the IOException (e.g., log the error)
                    e.printStackTrace();
                }

                // Display a success message
                messageDisplayer.displayMessage("Book #" + book.getId() + " returned by Patron #" + patron.getId());
            }
        } else {
            messageDisplayer.displayMessage("This book is not currently borrowed by the patron.");
        }
    }
}
