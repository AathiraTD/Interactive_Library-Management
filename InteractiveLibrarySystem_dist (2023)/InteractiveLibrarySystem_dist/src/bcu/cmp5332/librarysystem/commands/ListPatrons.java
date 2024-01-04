package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.gui.PatronsListView;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.SwingUtilities;

/**
 * The ListPatrons class represents a command to list all patrons in the library.
 * It can be used in both CLI and GUI modes.
 */
public class ListPatrons implements Command {

    private PatronsListView view; // This can be null for CLI usage
    /**
     * Constructor for ListPatrons command.
     *
     * @param view               The PatronsListView for GUI mode (can be null for CLI).
     * @param messageDisplayer   The message displayer for showing messages.
     */
    public ListPatrons(PatronsListView view, MessageDisplayer messageDisplayer) {
        this.view = view;
    }

    /**
     * Executes the list patrons command. It lists all patrons in the library,
     * filtering out deleted patrons, and displays them in the GUI view or CLI console.
     *
     * @param library       The library instance on which the command is executed.
     * @param currentDate   The current date.
     * @throws LibraryException if there are issues with listing the patrons.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        List<Patron> patrons = library.getPatrons().stream()
                                      .filter(patron -> !patron.isDeleted())
                                      .collect(Collectors.toList());

        if (view != null) {
            // GUI: Update the PatronsListView with the list of patrons
            SwingUtilities.invokeLater(() -> {
                view.updateView(patrons);
            });
        } else {
            // CLI: Print the patron details to the console
            patrons.forEach(patron -> messageDisplayer.displayMessage(patron.getDetailsShort()));
            messageDisplayer.displayMessage(patrons.size() + " patron(s) available.");
        }
    }
}
