package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;
import bcu.cmp5332.librarysystem.utils.Validator;

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
            Patron patron = library.getPatronById(patronId);

            // Validate the patron's eligibility for hiding
            String validationMessage = Validator.validatePatronForHiding(patron);
            if (validationMessage != null) {
                throw new LibraryException(validationMessage); // Report the validation issue
            }

            patron.hidePatron(); // Hide the patron

            try {
                LibraryData.store(library); // Persist the changes
                messageDisplayer.displayMessage("Patron with ID " + patronId + " has been hidden.");
            } catch (IOException e) {
                throw new LibraryException("Error saving the library data: " + e.getMessage());
            }
        }
    }
}
