package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class is responsible for displaying the patrons in a graphical interface.
 * It creates a table view for patron information, including a custom button in the 'Books Info' column.
 */
public class PatronsListView {
    private JFrame frame;
    private final Library library;

    /**
     * Constructor for PatronsListView.
     * 
     * @param frame The JFrame in which the patrons list will be displayed.
     * @param library The library instance to access patron data.
     */
    public PatronsListView(JFrame frame, Library library) {
        this.frame = frame;
        this.library = library;
    }

    /**
     * Updates the view with a list of patrons.
     * 
     * @param patrons List of Patron objects to display.
     */
    public void updateView(List<Patron> patrons) {
        // Updated to include 'ID' as the first column in the table
        String[] columns = {"ID", "Name", "Phone number", "Email", "Loaned Books", "Books Info"};
        Object[][] data = new Object[patrons.size()][columns.length];

        // Populate data for the table with patron information
        for (int i = 0; i < patrons.size(); i++) {
            Patron patron = patrons.get(i);
            List<Book> borrowedList = patron.getBorrowedBooks();
            String bookIds = getBookIds(borrowedList);

            // Adding patron ID as the first element in each row
            data[i][0] = patron.getId();
            // The rest of the patron's information is added to the subsequent columns
            data[i][1] = patron.getName();
            data[i][2] = patron.getPhoneNumber();
            data[i][3] = patron.getEmail();
            data[i][4] = borrowedList.size() + " book(s)";
            // Adjusting the index for the 'Books Info' column to 5
            data[i][5] = !borrowedList.isEmpty() ? "View Books: " + bookIds : "";
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
        int booksInfoColumnIndex = 5; // Index of the 'Books Info' column
        table.getColumnModel().getColumn(booksInfoColumnIndex).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(booksInfoColumnIndex).setCellEditor(new ButtonEditor(new JTextField(), library, true));
    }

    /**
     * Generates a string representing the IDs of the books borrowed by a patron. This must go in library class
     * 
     * @param books List of books borrowed by the patron.
     * @return A string containing the concatenated IDs of the borrowed books.
     */
    private String getBookIds(List<Book> books) {
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < books.size(); i++) {
            ids.append(books.get(i).getId());
            if (i < books.size() - 1) {
                ids.append(":");
            }
        }
        return ids.toString();
    }
}
