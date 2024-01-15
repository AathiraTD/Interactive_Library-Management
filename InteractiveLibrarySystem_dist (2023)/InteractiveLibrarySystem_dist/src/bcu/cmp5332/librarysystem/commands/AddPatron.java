package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;
import bcu.cmp5332.librarysystem.utils.Validator;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Command for adding a new patron to the library system.
 */
public class AddPatron implements Command {

    private final String name;
    private final String phoneNumber;
    private final String email;
    private final List<Integer> bookIds;

    /**
     * Constructs an AddPatron command with patron details and a list of book IDs.
     * Validates the input details of the patron.
     *
     * @param name         The name of the patron.
     * @param phoneNumber  The phone number of the patron.
     * @param email        The email address of the patron.
     * @param bookIds      A list of book IDs to be associated with the patron.
     * @throws LibraryException If any input parameter is invalid.
     */
    public AddPatron(String name, String phoneNumber, String email, List<Integer> bookIds) throws LibraryException {
        
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.bookIds = bookIds == null ? new ArrayList<>() : new ArrayList<>(bookIds);
    }

    /**
     * Executes the AddPatron command.
     *
     * @param library            The library to which the patron will be added.
     * @param currentDate        The current date (not used in this command).
     * @param messageDisplayer   The mechanism for displaying messages to the user.
     * @throws LibraryException  If an error occurs during the addition of the patron.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        // Validating user input
    	String validationMessage = Validator.validatePatronDetails(name, phoneNumber, email);
        if (validationMessage != null) {
        	//messageDisplayer.displayMessage(validationMessage);
            throw new LibraryException(validationMessage);
        }
    	// Generate a unique ID for the new patron
        int maxId = library.getPatrons().stream().mapToInt(Patron::getId).max().orElse(0);

        // Create and add a new patron to the library
        Patron newPatron = new Patron(++maxId, name, phoneNumber, email);
        library.addPatron(newPatron);

        List<Integer> unavailableBooks = new ArrayList<>();
        for (Integer bookId : bookIds) {
            try {
                Command borrowCommand = new BorrowBook(newPatron.getId(), bookId);
                borrowCommand.execute(library, currentDate, messageDisplayer);
            } catch (LibraryException ex) {
                // Collecting IDs of books that could not be borrowed
                unavailableBooks.add(bookId);
            }
        }

        try {
            // Save the updated library data
            LibraryData.store(library);
        } catch (IOException e) {
            throw new LibraryException("Error saving the library data: " + e.getMessage());
        } 

        // Display success message
        messageDisplayer.displayMessage("Patron #" + newPatron.getId() + " added with " + 
                                        (bookIds.size() - unavailableBooks.size()) + " borrowed books.");

        // Inform about books that could not be borrowed
        if (!unavailableBooks.isEmpty()) {
            messageDisplayer.displayMessage("Some books could not be borrowed: " + unavailableBooks);
        }
    }
}
