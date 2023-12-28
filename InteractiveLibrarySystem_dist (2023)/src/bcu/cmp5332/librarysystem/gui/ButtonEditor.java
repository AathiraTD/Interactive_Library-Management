package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The ButtonEditor class represents a custom cell editor for a JTable that contains buttons.
 * It is used to display book details when a button in the table is clicked.
 */
public class ButtonEditor extends DefaultCellEditor {

    private JButton button;
    private final Library library;
    private boolean clicked;
    private JTable table;
    private String lbl;

    /**
     * Constructs a ButtonEditor with a JTextField and a reference to the Library.
     *
     * @param textField The JTextField component.
     * @param library   The Library object.
     */
    public ButtonEditor(JTextField textField, Library library) {
        super(textField);
        this.library = library;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clicked = true;
                fireEditingStopped(); // Notify editing stopped
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.table = table; // Store table reference
        button.setText((value == null) ? "" : value.toString()); // Set button text
        lbl =(value==null) ? "":value.toString();
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            String[] properties = lbl.split(":");
            if(properties[0].equals("Member id")) {
            	displayPatronDetailsForRow(row);
            } else if(properties[0].equals("View Books")) {
            	displayBookDetailsForRow(row); // Display book details
            }  else if (properties[0].isBlank()){
            	JOptionPane.showMessageDialog(button, "        No data to view");
            }
            
        }
        clicked = false;
        return button.getText();
    }

    private void displayBookDetailsForRow(int row) {
        try {
            // Retrieve the book's ID and convert it to an Integer
            // Assuming ID is in the first column (0), adjust if needed
            int patronId = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());

            Patron patron = library.getPatronByID(patronId);
            
            if (patron != null) {
                List<Book> booksList = patron.getBorrowedBooks(); // Get list of books which the patron has borrowed.
                if (booksList.size() >0) {
                	new ViewBookDetails(booksList);
                }
                else {
                	throw new LibraryException("There is no book borrowed by the patron");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(button, "Error in parsing book ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(button, "Error retrieving book details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void displayPatronDetailsForRow(int row) {
        try {
            // Retrieve the patron ID and convert it to an Integer
            // Assuming ID is in the first column (0), adjust if needed
            int bookId = Integer.parseInt(table.getModel().getValueAt(row, 0).toString());
            Book book = library.getBookByID(bookId);
            Loan loan = book.getLoan();
            Patron patron = loan.getPatron();
            
            if (patron != null) {
                	new ViewPatronDetails(patron);
            } else {
                	throw new LibraryException("There is no patron borrowing the book");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(button, "Error in parsing patron ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(button, "Error retrieving patron details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
