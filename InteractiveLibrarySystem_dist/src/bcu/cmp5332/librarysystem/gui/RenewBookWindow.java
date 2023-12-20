package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.RenewBook;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.main.LibraryException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class RenewBookWindow extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
    private JTextField bookIdText = new JTextField();
    private JTextField patronIdText = new JTextField();
    private JButton renewBtn = new JButton("Renew");
    private JButton cancelBtn = new JButton("Cancel");

    public RenewBookWindow(MainWindow mw) {
        this.mw = mw;
        initialize(); // Initialize the window components
    }

    private void initialize() {
        setTitle("Renew Book"); // Set the title of the window
        setSize(300, 200); // Set the size of the window

        // Create the top panel for input fields
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2));

        // Add components (labels and text fields) to the top panel
        topPanel.add(new JLabel("Book ID: "));
        topPanel.add(bookIdText);
        topPanel.add(new JLabel("Patron ID: "));
        topPanel.add(patronIdText);

        // Create the bottom panel for buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     ")); // Spacer label for alignment
        bottomPanel.add(renewBtn); // Add renew button
        bottomPanel.add(cancelBtn); // Add cancel button

        // Set action listeners for the buttons
        renewBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        // Add the panels to the frame
        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw); // Set the location relative to the main window

        setVisible(true); // Make the window visible
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == renewBtn) {
            renewBook(); // Handle renew book action
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false); // Close the window without renewing the book
        }
    }

    private void renewBook() {
        try {
            // Parse the book ID and patron ID from the text fields
            int bookId = Integer.parseInt(bookIdText.getText());
            int patronId = Integer.parseInt(patronIdText.getText());

            // Create and execute the RenewBook command
            Command renewBook = new RenewBook(patronId, bookId);
            renewBook.execute(mw.getLibrary(), LocalDate.now());

            // Refresh the main window's display
            mw.displayBooks();

            // Close the renew book window
            this.setVisible(false);

            // Show a success message
            JOptionPane.showMessageDialog(this, 
                "Book successfully renewed.", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            // Show an error message if the ID format is invalid
            JOptionPane.showMessageDialog(this, 
                "Invalid ID format. Please enter a valid number.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (LibraryException ex) {
            // Show an error message for other library-specific exceptions
            JOptionPane.showMessageDialog(this, 
                ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
