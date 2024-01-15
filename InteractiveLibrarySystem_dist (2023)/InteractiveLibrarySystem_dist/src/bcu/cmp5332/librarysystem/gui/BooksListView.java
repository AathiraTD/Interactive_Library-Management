package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class is responsible for displaying the books in a graphical interface.
 * It creates a table view for book information, including a custom button in the 'Patron Info' column.
 */
public class BooksListView {
    private JFrame frame;
    private final Library library;

    /**
     * Constructor for BooksListView.
     *
     * @param frame   The JFrame in which the books list will be displayed.
     * @param library The library instance to access book data.
     */
    public BooksListView(JFrame frame, Library library) {
        this.frame = frame;
        this.library = library;
    }

    /**
     * Updates the view with a list of books.
     *
     * @param books List of Book objects to display.
     */
    public void updateView(List<Book> books) {
        // Updated to include 'ID' as the first column in the table
        String[] columns = {"ID", "Title", "Author", "Publication Year", "Status", "Patron Info"};
        Object[][] data = new Object[books.size()][columns.length];

        // Populate data for the table with book information
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            Loan loan = book.getLoan();
            Patron patron = null;
            String patronInfo = null;

            if (loan != null) {
                patron = loan.getPatron();
                if (patron != null && !patron.isDeleted()) {
                    patronInfo = "View Patrons: " +   "" + " (ID: " + patron.getId() + ")";
                }
            }

            // Adding book ID as the first element in each row
            data[i][0] = book.getId();
            // The rest of the book's information is added to the subsequent columns
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getPublicationYear();
            data[i][4] = book.getStatus().toString();  // Assuming getStatus() returns an enum or similar
            // Adjusting the index for the 'Patron Info' column to 5
            data[i][5] = patronInfo;
        }

        // Create the table with the updated data
        JTable table = new JTable(data, columns);
        setCustomRenderersAndEditors(table);

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
