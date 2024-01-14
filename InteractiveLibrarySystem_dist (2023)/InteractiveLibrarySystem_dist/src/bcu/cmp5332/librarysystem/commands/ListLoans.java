package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.gui.LoanHistoryView;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.SwingUtilities;

/**
 * The ListLoans class represents a command to list all active loans in the library.
 * It can be used in both CLI and GUI modes.
 */
public class ListLoans implements Command {

    private LoanHistoryView view; // This can be null for CLI usage

    /**
     * Constructor for ListLoans command.
     *
     * @param view               The LoansListView for GUI mode (can be null for CLI).
     */
    public ListLoans(LoanHistoryView view, MessageDisplayer messageDisplayer) {
        this.view = view;
    }

    /**
     * Executes the list loans command. It lists all active loans in the library,
     * and displays them in the GUI view or CLI console.
     *
     * @param library       The library instance on which the command is executed.
     * @param currentDate   The current date.
     * @throws LibraryException if there are issues with listing the loans.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        List<Loan> loans = library.getAllLoans().stream()
                              .collect(Collectors.toList());

        if (view != null) {
            // GUI: Update the LoansListView with the list of loans
            SwingUtilities.invokeLater(() -> {
                view.updateView(loans);
            });
        } else {
            // CLI: Print the loan details to the console
            for (Loan loan : loans) {
                messageDisplayer.displayMessage("Loan ID: " + loan.getLoanId() +
                	", Book ID: " + loan.getBook().getId() +	
                    ", Book: " + loan.getBook().getTitle() +
                    ", Patron ID: " + loan.getPatron().getId() +
                    ", Patron: " + loan.getPatron().getName() +
                    ", Due Date: " + loan.getDueDate());
            }
            messageDisplayer.displayMessage(loans.size() + " active loan(s).");
        }
    }
}
