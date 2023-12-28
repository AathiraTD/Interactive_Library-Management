package bcu.cmp5332.librarysystem.gui;

import javax.swing.*;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.model.Book.Status;

import java.util.List;

public class BooksListView {
    private JTable table;
    private JFrame frame;
    private final Library library;

    public BooksListView(JFrame frame, Library library) {
        this.frame = frame;
        this.library = library;
    }

    public void updateView(List<Book> books) {
        String[] columns = new String[]{"ID", "Title", "Author", "Publication Year", "Status", "Patron Info"};
        Object[][] data = new Object[books.size()][columns.length];

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            data[i][0] = book.getId();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getPublicationYear();
            data[i][4] = book.getStatus().toString();  // Assuming getStatus() returns an enum or similar
            if (book.getStatus() == Status.LOANED_OUT) {
            	data[i][5] = "Member id:" + getPatronId(book);
            } else {
            	data[i][5] = "";
            }
        }

        table = new JTable(data, columns);
        
        setCustomRenderersAndEditors(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(scrollPane);
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
        table.getColumnModel().getColumn(patronInfoColumnIndex).setCellEditor(new ButtonEditor(new JTextField(), library));
    }
    
    /**
     * Generates an integer representing the ID of the patron borrowing the book.
     * 
     * @param book borrowed by the patron.
     * @return An integer containing the ID of the patron.
     */
    private int getPatronId(Book book) {
    	Loan loan = book.getLoan();
    	Patron patron = loan.getPatron();
    	int patronId = patron.getId();
    	return patronId;
    }
}
