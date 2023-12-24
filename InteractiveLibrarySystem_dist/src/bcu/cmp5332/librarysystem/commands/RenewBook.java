package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

public class RenewBook implements Command {

    private final int patronId;
    private final int bookId;

    public RenewBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        Book book = library.getBookByID(bookId);

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
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} 
                System.out.println("Book #" + book.getId() + " renewed by Patron #" + patron.getId() + ". New due date: " + newDueDate);
            } else {
                throw new LibraryException("Loan record not found for this book and patron.");
            }
        } else {
            throw new LibraryException("This book is either not on loan or not borrowed by this patron.");
        }
    }
}
