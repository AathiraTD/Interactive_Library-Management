package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.List;

/**
 * Command to hide (soft-delete) a list of patrons from the library system.
 */
public class HidePatrons implements Command {

    private final List<Integer> patronIds; // List of patron IDs to be hidden

    /**
     * Constructor for HidePatrons command.
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
     * @throws LibraryException if a patron ID does not correspond to an existing patron.
     */
    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        for (int patronId : patronIds) {
            Patron patron = library.getPatronByID(patronId); // Retrieve patron by ID
            if (patron == null) {
                // Throw exception if patron is not found
                throw new LibraryException("Patron with ID " + patronId + " not found.");
            }
            if (!patron.isDeleted()) {
                // Hide the patron if they are not already hidden
                patron.hidePatron();
                System.out.println("Patron with ID " + patronId + " has been hidden.");
            } else {
                // Indicate if the patron is already hidden
                System.out.println("Patron with ID " + patronId + " is already hidden.");
            }
        }
    }
}
