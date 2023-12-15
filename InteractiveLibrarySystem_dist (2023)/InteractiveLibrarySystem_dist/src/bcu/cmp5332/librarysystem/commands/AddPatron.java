package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddPatron implements Command {

    private final String name;
    private final String phoneNumber;
    private final List<Integer> bookIds;
    private List<Integer> toRemove;

    public AddPatron(String name, String phoneNumber, List<Integer> bookIds) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.bookIds = bookIds;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        int maxId = 0;
        toRemove = new ArrayList<>();
        if (!library.getPatrons().isEmpty()) {
            maxId = library.getPatrons().stream().mapToInt(Patron::getId).max().orElse(0);
        }
        Patron patron = new Patron(++maxId, name, phoneNumber);
        library.addPatron(patron);

        for (Integer bookId : bookIds) {
            Book book = library.getBookByID(bookId);
            if (book != null && !book.isOnLoan()) {            	
                patron.borrowBook(book); // Assuming a default 2-week loan period

                Loan loan = new Loan(book, patron, currentDate.plusWeeks(2));
                library.addLoan(loan); // Add the loan to the library
                book.setLoan(loan);
            } else if (book != null && book.isOnLoan()) {
                toRemove.add(bookId); 
                System.out.println("Book ID: " + bookId + " is currently on loan");
                // Add to the list for removal
                // Handle the case where the book is already on loan
            }
            else {
            }
        }
        
        // Remove the IDs after iteration
        bookIds.removeAll(toRemove);
        System.out.println("Patron #" + patron.getId() + " added with " + bookIds.size() + " borrowed books.");
    }
}