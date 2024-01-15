package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class is responsible for displaying the loan history in a graphical interface.
 * It creates a table view for loan information
 */
public class LoanHistoryView {
    private JFrame frame;
    private Library library = new Library();

    /**
     * Constructor for LoanListView.
     *
     * @param frame   The JFrame in which the loan history will be displayed.
     * @param library The library instance to access loan data.
     * @return 
     */
    public LoanHistoryView(JFrame frame, Library library) {
        this.frame = frame;
        this.library = library;
    }

    /**
     * Updates the view with a list of books.
     *
     * @param books List of Book objects to display.
     */
    public void updateView(List<Loan> loans) {
        String[] columns = {"Loan ID", "Book ID", "Book Title", "Patron ID","Patron Name", "Due Date", "Return Date", "Status"};
        Object[][] data = new Object[loans.size()][columns.length];

        // Populate data for the table with loan information
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);

            data[i][0] = loan.getLoanId();
            data[i][1] = loan.getBook().getId();
            data[i][2] = loan.getBook().getTitle();
            data[i][3] = loan.getPatron().getId();
            data[i][4] = loan.getPatron().getName();
            data[i][5] = loan.getDueDate().toString();
            data[i][6] = loan.getReturnDate() == null ? "" : loan.getReturnDate().toString();
            data[i][7] = loan.getStatus().toUpperCase();
        }

        // Create the table with the updated data
        JTable table = new JTable(data, columns);

        // Add the table to a scroll pane and update the frame
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }


    /**
     * Sets custom renderers and editors for the table.
     *
     * @param table The table to which the custom renderer and editor are applied.
     */
    private void setCustomRenderersAndEditors(JTable table) {
        int patronInfoColumnIndex = 5; // Index of the 'Patron Info' column
        table.getColumnModel().getColumn(patronInfoColumnIndex).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(patronInfoColumnIndex).setCellEditor(new ButtonEditor(new JTextField(), library,false));
    }
}
