package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.controllers.LibraryController;
import bcu.cmp5332.librarysystem.main.LibraryException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DeleteBookWindow extends JFrame implements ActionListener {

    /**
	 * This class is responsible for providing a small graphical interface for deleting a book from the library system.
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
    private JTextField bookIdText = new JTextField();
    private JButton deleteBtn = new JButton("Delete");
    private JButton cancelBtn = new JButton("Cancel");
    private JCheckBox check = new JCheckBox("Are you sure?");
    private boolean isChecked = false;
    
    private LibraryController controller;
    
    /**
     * Constructs a DeleteBookWindow instance for the Library Management System.
     * @param mw The main window instance to work with.
     */

    public DeleteBookWindow(MainWindow mw, LibraryController controller) {
        this.mw = mw;
        this.controller = controller;
        initialize();
    }
    
    /**
     * Initializes the UI components and configures the layout and event listeners.
     * Creates an input field for the book ID and a checkbox to confirm deletion.
     * 
     */

    private void initialize() {
        // Set the title and size of the window
        setTitle("Delete Book");
        setSize(300, 150);

        // Create top panel for user input
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2));

        // Add components to top panel
        topPanel.add(new JLabel("     ")); // Spacer
        topPanel.add(new JLabel("     "));
        topPanel.add(new JLabel("Book ID: "));
        topPanel.add(bookIdText);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(1, 12));
        middlePanel.add(new JLabel("     "));
        middlePanel.add(check);

        // Create bottom panel for buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     ")); // Spacer
        bottomPanel.add(deleteBtn);
        bottomPanel.add(cancelBtn);

        // Set action listeners for buttons
        deleteBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        check.addItemListener(e -> {
            isChecked = e.getStateChange() == 1;
        });

        // Add panels to the frame
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(middlePanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        // Make the window visible
        setVisible(true);
    }
    
    /**
     * Handles action events triggered by UI components.
     * Calls the deleteBook method if delete button is pressed.
     * @param ae The action event that occurred.
     */

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteBtn) {
            if (isChecked) {
                deleteBook();
            }
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false); // Close the window without returning a book
        }
    }

    private void deleteBook() {
        try {
            // Parse book ID from text field
            int bookId = Integer.parseInt(bookIdText.getText());

            // Create a list with a single book ID
            List<Integer> bookIds = new ArrayList<>();
            bookIds.add(bookId);

            // Trigger the hideBooks method in the LibraryController
            controller.hideBooks(bookIds);
            
         // Refresh the main window display
            mw.displayBooks();

            // Close the delete book window
            this.setVisible(false);

            // Show success message
            JOptionPane.showMessageDialog(this,
                    "Book successfully deleted.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            // Show error message for invalid number format
            JOptionPane.showMessageDialog(this,
                    "Invalid ID format. Please enter a valid number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (LibraryException ex) {
            // Show error message for library-specific exceptions
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
