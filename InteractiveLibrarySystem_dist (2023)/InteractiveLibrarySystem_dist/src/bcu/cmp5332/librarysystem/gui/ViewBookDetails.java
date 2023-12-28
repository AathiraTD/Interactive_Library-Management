package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.model.Book;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The ViewBookDetails class represents a frame for displaying book details in a table.
 * It is used to show book details to the user.
 */
public class ViewBookDetails extends JFrame {

    private List<Book> booksList;
    private JTable table;

    /**
     * Constructs a ViewBookDetails frame.
     *
     * @param booksList The list of books to display details for.
     */
    public ViewBookDetails(List<Book> booksList) {
        this.booksList = booksList;
        this.setTitle("Book Details");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose of the frame when closed
        this.setLayout(new BorderLayout());

        // Headers for the table
        String[] columns = new String[]{"Title", "Author", "Pub Date", "Status"};

        // Show books which haven't been deleted
        Object[][] data = new Object[booksList.size()][4];
        int rowIndex = 0;
        for (Book book : booksList) {
            if (!book.isDeleted()) {
                data[rowIndex][0] = book.getTitle();
                data[rowIndex][1] = book.getAuthor();
                data[rowIndex][2] = book.getPublicationYear();
                data[rowIndex][3] = book.getStatus();
                rowIndex++;
            }
        }

        table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        this.add(scrollPane, BorderLayout.CENTER);

        pack(); // Auto-adjust the size of the frame
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
}
