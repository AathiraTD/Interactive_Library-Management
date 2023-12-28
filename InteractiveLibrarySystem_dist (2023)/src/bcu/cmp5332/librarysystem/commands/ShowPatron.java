package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import java.time.LocalDate;

/**
 * The ShowPatron class represents a command to display detailed information about a patron.
 * It allows showing patron details to the user.
 */
public class ShowPatron implements Command {

    private final int patronId;

    /**
     * Constructs a ShowPatron command.
     *
     * @param patronId ID of the patron to be displayed.
     */
    public ShowPatron(int patronId) {
        this.patronId = patronId;
    }

    /**
     * Executes the show patron command.
     * It displays detailed information about a patron if they exist.
     *
     * @param library         The library where the patron data is managed.
     * @param currentDate     The current date.
     * @param messageDisplayer The message displayer for displaying messages.
     * @throws LibraryException if any validation fails during the execution.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        if (patron != null) {
            messageDisplayer.displayMessage(patron.getDetailsShort());
        } else {
            messageDisplayer.displayMessage("Patron #" + patronId + " not found.");
        }
    }
}
