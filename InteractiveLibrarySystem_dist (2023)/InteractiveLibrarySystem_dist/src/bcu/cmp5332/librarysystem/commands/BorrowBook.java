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
 * The BorrowBook class represents a command to borrow a book by a patron in the library system.
 * This command facilitates the process of loaning a book to a patron and updates the system records accordingly.
 */
public class BorrowBook implements Command {

    private final int patronId; // ID of the patron who is borrowing the book
    private final int bookId; // ID of the book to be borrowed

    // Class-level constant for the maximum number of books a patron can borrow
    private static final int MAX_BORROW_LIMIT = 5;

    /**
     * Constructs a BorrowBook command with specified patron and book IDs.
     *
     * @param patronId The ID of the patron borrowing the book.
     * @param bookId   The ID of the book being borrowed.
     */
    public BorrowBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    /**
     * Executes the borrowing process for a book by a patron.
     *
     * @param library      The library from which the book is borrowed.
     * @param currentDate  The current date, used to set the due date of the loan.
     * @param messageDisplayer The message displayer for showing success and error messages.
     * @throws LibraryException If there are issues with the book or patron eligibility for borrowing.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        // Retrieve the book and patron by their IDs
        Book book = library.getBookByID(bookId);
        Patron patron = library.getPatronByID(patronId);

        // Validate that both the book and patron are eligible for the borrowing process
        validateBookAndPatronForBorrowing(book, patron);

        // Set the due date for the loan (two weeks from the current date)
        LocalDate dueDate = currentDate.plusWeeks(2);

        // Create and assign the loan
        Loan loan = new Loan(book, patron, dueDate);
        book.setLoan(loan); // Associate the loan with the book
        patron.borrowBook(book); // Add the book to the patron's borrowed books list
        library.addLoan(loan); // Add the loan to the library's collection of loans

        // Link the books to their respective loans
        library.linkBooksToLoans();

        try {
            // Persist the changes in the library data
            LibraryData.store(library);
        } catch (IOException e) {
            // Handle IOExceptions during data storage
            throw new LibraryException("Error saving the library data: " + e.getMessage());
        }

        // Display a success message
        messageDisplayer.displayMessage("Book #" + book.getId() + " has been issued to Patron #" + patron.getId() + ", due on " + dueDate);
    }

    /**
     * Validates the eligibility of the book and patron for borrowing.
     * Checks whether the book is available and the patron hasn't reached the borrowing limit.
     *
     * @param book   The book to be borrowed.
     * @param patron The patron borrowing the book.
     * @throws LibraryException If the book or patron is not eligible for borrowing.
     */
    private void validateBookAndPatronForBorrowing(Book book, Patron patron) throws LibraryException {
        // Check the eligibility of the book for loan
        if (book == null || book.isDeleted()) {
            throw new LibraryException("Invalid or hidden book cannot be borrowed.");
        }
        if (book.isOnLoan()) {
            throw new LibraryException("Book is already loaned out.");
        }

        // Check the eligibility of the patron for borrowing
        if (patron == null || patron.isDeleted()) {
            throw new LibraryException("Invalid or hidden patron cannot borrow books.");
        }
        if (patron.getBorrowedBooks().size() >= MAX_BORROW_LIMIT) {
            throw new LibraryException("Patron has already borrowed the maximum allowed number of books.");
        }
    }
}
