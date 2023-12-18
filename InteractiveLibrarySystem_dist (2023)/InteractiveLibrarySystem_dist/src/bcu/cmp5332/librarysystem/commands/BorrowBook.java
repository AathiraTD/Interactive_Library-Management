package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;

/**
 * Command for borrowing a book by a patron.
 */
public class BorrowBook implements Command {

    private final int patronId; // ID of the patron who is borrowing the book
    private final int bookId; // ID of the book to be borrowed
 // Class-level constant for maximum number of books a patron can borrow
    private static final int MAX_BORROW_LIMIT = 5;

    /**
     * Constructs a BorrowBook command.
     *
     * @param patronId ID of the patron borrowing the book.
     * @param bookId ID of the book being borrowed.
     */
    public BorrowBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    /**
     * Executes the borrowing process.
     *
     * @param library The library where the book is borrowed from.
     * @param currentDate The current date.
     * @throws LibraryException If any validation fails during the borrowing process.
     */
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // Retrieve the book and patron using their IDs
        Book book = library.getBookByID(bookId);
        Patron patron = library.getPatronByID(patronId);

        // Perform validation checks
        validateBookAndPatronForBorrowing(book, patron);

        // Set the due date for the loan
        LocalDate dueDate = currentDate.plusWeeks(2); // Two weeks from the current date

        // Create the loan object
        Loan loan = new Loan(book, patron, dueDate);

        // Update the book and patron records
        book.setLoan(loan); // Associate the loan with the book
        patron.borrowBook(book); // Add the book to the patron's borrowed books list
        library.addLoan(loan); // Add the loan to the library's collection of loans

        // Link the books to their loans after updating
        library.linkBooksToLoans();

        System.out.println("Book #" + book.getId() + " has been issued to Patron #" + patron.getId() + " due on " + dueDate);
    }

    /**
     * Validates the book and patron before proceeding with the borrowing.
     *
     * @param book The book to be borrowed.
     * @param patron The patron borrowing the book.
     * @throws LibraryException If the book or patron is not eligible for borrowing.
     */
    private void validateBookAndPatronForBorrowing(Book book, Patron patron) throws LibraryException {
        if (book == null || book.isDeleted()) {
            throw new LibraryException("Invalid or hidden book cannot be borrowed.");
        }
        if (book.isOnLoan()) {
            throw new LibraryException("Book is already loaned out.");
        }
        if (patron == null || patron.isDeleted()) {
            throw new LibraryException("Invalid or hidden patron cannot borrow books.");
        }
        if (patron.getBorrowedBooks().size() >= MAX_BORROW_LIMIT) {
            throw new LibraryException("Patron has already borrowed 5 books and cannot borrow more.");
        }
    }
}
