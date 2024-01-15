package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.BorrowBook;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.controllers.LibraryController;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.utils.GuiMessageDisplayer;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class IssueBookWindow extends JFrame implements ActionListener {

    /**
	 * This class is responsible for providing a small graphical interface to issue a new book to the library system.
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
    private JTextField bookIdText = new JTextField();
    private JTextField patronIdText = new JTextField();
    private JButton issueBtn = new JButton("Issue");
    private JButton cancelBtn = new JButton("Cancel");
    
    /**
     * Constructs a IssueBookWindow instance for the Library Management System.
     * @param mw The main window instance to work with.
     */

    public IssueBookWindow(MainWindow mw, LibraryController controller) {
        this.mw = mw;
        initialize();
    }
    
    /**
     * Initializes the UI components and configures the layout and event listeners.
     * Creates input fields for the book ID and patron ID.
     * 
     */

    private void initialize() {
        // Set the title and size of the window
        setTitle("Issue Book");
        setSize(300, 200);

        // Create top panel for user input
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2));

        // Add components to top panel
        topPanel.add(new JLabel("Book ID: "));
        topPanel.add(bookIdText);
        topPanel.add(new JLabel("Patron ID: "));
        topPanel.add(patronIdText);

        // Create bottom panel for buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     ")); // Spacer
        bottomPanel.add(issueBtn);
        bottomPanel.add(cancelBtn);

        // Set action listeners for buttons
        issueBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        // Add panels to the frame
        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        // Make the window visible
        setVisible(true);
    }
    
    /**
     * Handles action events triggered by UI components.
     * Calls the issueBook method if issue button is pressed.
     * @param ae The action event that occurred.
     */

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == issueBtn) {
            issueBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false); // Close the window without issuing a book
        }
    }
    
    /**
     * Issues a book to patron using the provided book ID and patron ID.
     */

    private void issueBook() {
        try {
            // Parse book ID and patron ID from text fields
            int bookId = Integer.parseInt(bookIdText.getText());
            int patronId = Integer.parseInt(patronIdText.getText());

            // Create and execute BorrowBook command
            Command borrowBook = new BorrowBook(patronId, bookId);
            MessageDisplayer guiDisplayer = new GuiMessageDisplayer();
            borrowBook.execute(mw.getLibrary(), LocalDate.now(),guiDisplayer);

            // Close the issue book window
            this.setVisible(false);

            // Show success message
            JOptionPane.showMessageDialog(this, 
                "Book successfully issued to patron.", 
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
