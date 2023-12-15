package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;

public class BorrowBook implements Command {

    private final int patronId;
    private final int bookId;

    public BorrowBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Book book = library.getBookByID(bookId);
        if (book == null) {
            throw new LibraryException("Book with ID " + bookId + " not found.");
        }
        if (book.isOnLoan()) {
            throw new LibraryException("Book is already loaned out.");
        }

        Patron patron = library.getPatronByID(patronId);
        if (patron == null) {
            throw new LibraryException("Patron with ID " + patronId + " not found.");
        }

        LocalDate dueDate = currentDate.plusWeeks(2); // Set due date to two weeks from the current date
        
        Loan loan = new Loan(book, patron, dueDate); // Create the loan object

        book.setLoan(loan); // Set the current loan on the book
        patron.borrowBook(book); // Add the book to the patron's borrowed books
        library.addLoan(loan); // Add the loan to the library's collection of loans
        library.linkBooksToLoans();

        // Optionally create and add a Loan object to track this borrowing

        System.out.println("Book #" + book.getId() + " has been issued to Patron #" + patron.getId() + " due on " + dueDate);
    }
}
