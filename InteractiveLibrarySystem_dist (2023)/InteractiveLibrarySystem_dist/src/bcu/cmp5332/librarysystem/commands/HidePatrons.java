package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * The HidePatrons class represents a command to hide (soft-delete) a list of patrons from the library system.
 * This command marks the specified patrons as hidden, preventing further interactions with them.
 */
public class HidePatrons implements Command {

    private final List<Integer> patronIds; // List of patron IDs to be hidden

    /**
     * Constructs a HidePatrons command with a list of patron IDs to be hidden.
     *
     * @param patronIds List of integers representing the IDs of patrons to be hidden.
     */
    public HidePatrons(List<Integer> patronIds) {
        this.patronIds = patronIds;
    }

    /**
     * Executes the hide patron command. Each patron specified by ID is marked as hidden in the library system.
     *
     * @param library       The library instance on which the command is executed.
     * @param currentDate   The current date.
     * @param messageDisplayer The message displayer for showing success and error messages.
     * @throws LibraryException if there are issues with hiding the patrons.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        for (int patronId : patronIds) {
            Patron patron = library.getPatronByID(patronId); // Retrieve patron by ID
            if (patron == null) {
                // Display an error message and continue to the next patron
            	throw new LibraryException("Patron with ID " + patronId + " not found.");
            }

            // Check if the patron has any books on loan
            if (!patron.getBorrowedBooks().isEmpty()) {
                // Display an error message and continue to the next patron
            	throw new LibraryException("Cannot hide patron with ID " + patronId + " as they currently have books on loan.");            
            }

            if (!patron.isDeleted()) {
                // Hide the patron if they are not already hidden
                patron.hidePatron();
                try {
                    // Persist the changes in the library data
                    LibraryData.store(library);
                    // Display a success message
                    messageDisplayer.displayMessage("Patron with ID " + patronId + " has been hidden.");
                } catch (IOException e) {
                    // Handle IOExceptions during data storage
                    throw new LibraryException("Error saving the library data: " + e.getMessage());
                }
            } else {
                // Indicate if the patron is already hidden
                messageDisplayer.displayMessage("Patron with ID " + patronId + " is already hidden.");
            }
        }
    }
}
