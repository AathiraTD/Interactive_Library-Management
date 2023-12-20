package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;

import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddPatron implements Command {

    private final String name;
    private final String phoneNumber;
    private final String email;
    private final List<Integer> bookIds;

    public AddPatron(String name, String phoneNumber, String email, List<Integer> bookIds) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bookIds = bookIds;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // Creating a new Patron
        int maxId = library.getPatrons().stream().mapToInt(Patron::getId).max().orElse(0);
        Patron newPatron = new Patron(++maxId, name, phoneNumber, email);
        library.addPatron(newPatron);

        // List to keep track of unavailable book IDs
        List<Integer> unavailableBooks = new ArrayList<>();

        // Borrowing books for the patron
        for (Integer bookId : bookIds) {
            try {
                Command borrowCommand = new BorrowBook(newPatron.getId(), bookId);
                borrowCommand.execute(library, currentDate);
            } catch (LibraryException ex) {
                System.out.println("Error borrowing book ID " + bookId + ": " + ex.getMessage());
                unavailableBooks.add(bookId);
                // Log or handle the error as necessary
            }
        }
        
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

        // Reporting success and any issues with unavailable books
        System.out.println("Patron #" + newPatron.getId() + " added with " + (bookIds.size() - unavailableBooks.size()) + " borrowed books.");
        if (bookIds.size() !=0 && !unavailableBooks.isEmpty()) {
            System.out.println("Some books could not be borrowed (already on loan or not found): " + unavailableBooks);
        }
    }
}
