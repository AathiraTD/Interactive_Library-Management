package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;

public class ReturnBook implements Command {

    private final int patronId;
    private final int bookId;

    public ReturnBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        Book book = library.getBookByID(bookId);

        if (patron == null || book == null) {
            throw new LibraryException("Book or Patron not found.");
        }

        if (patron.getBorrowedBooks().contains(book)) {
            patron.returnBook(book); // Remove the book from the patron's borrowed list

            // Find the corresponding loan and mark it as closed
            Loan loan = library.findLoan(bookId, patronId);
            if (loan != null) {
                loan.setReturnDate(currentDate); // Set the return date
                loan.setStatus("closed"); // Mark the loan as closed
                book.clearTemporaryLoanId();
                book.returnToLibrary();
            }

            System.out.println("Book #" + book.getId() + " returned by Patron #" + patron.getId());
            // Persist changes to data files (books.txt, patrons.txt, loans.txt) as needed
        } else {
            throw new LibraryException("This book is not currently borrowed by the patron.");
        }
    }
}
