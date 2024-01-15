package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.controllers.LibraryController;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddPatronWindow extends JFrame implements ActionListener {

    /**
	 * This class is responsible for providing a small graphical interface for adding a new patron to the library system.
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
    private JTextField nameText = new JTextField();
    private JTextField phoneText = new JTextField();
    private JTextField emailText = new JTextField();
    private JTextField bookText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");
    
    private LibraryController controller;
    
    /**
     * Constructs a AddPatronWindow instance for the Library Management System.
     * @param mw The main window instance to work with.
     */

    public AddPatronWindow(MainWindow mw, LibraryController controller) {
        this.mw = mw;
        this.controller = controller;
        initialize();
    }

    /**
     * Initializes the UI components and configures the layout and event listeners.
     * Creates input fields for the patron name, phone number, email, and borrowed book IDs.
     * 
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        } 

        setTitle("Add a New Patron");

        setSize(300, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2)); //5, 2
        topPanel.add(new JLabel("Name : "));
        topPanel.add(nameText);
        topPanel.add(new JLabel("Phone Number : "));
        topPanel.add(phoneText);
        topPanel.add(new JLabel("Email : "));
        topPanel.add(emailText);
        topPanel.add(new JLabel("Book IDs (comma separated) for books borrowed : "));
        topPanel.add(bookText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(addBtn);
        bottomPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }
    
    /**
     * Handles action events triggered by UI components.
     * Calls the addPatron method if add button is pressed.
     * @param ae The action event that occurred.
     */

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addPatron();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }
    
    /**
     * Parses the book IDs input.
     * @param line The comma separated book IDs input.
     */

    private static List<Integer> parseBookIds(String line) {
        List<Integer> bookIds = new ArrayList<>();
        for (String part : line.split(",")) {
            try {
                bookIds.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException e) {
                // Log the error or notify the user about the invalid book ID
            }
        }
        return bookIds;
    }
    
    /**
     * Adds a patron using the provided name, phone number, email, and borrowed book IDs.
     */
    private void addPatron() {
        String name = nameText.getText();
        String phone = phoneText.getText();
        String email = emailText.getText();
        String bookIdsLine = bookText.getText();

        try {
            if (name == null || phone == null || email == null || bookIdsLine == null) {
                throw new LibraryException("Inputs cannot be empty.");
            }
            List<Integer> bookIds = parseBookIds(bookIdsLine);

            // Delegate the action to the controller
            controller.addPatron(name, phone, email, bookIds);
            
         // Refresh the main window display
            mw.displayPatrons();

            // Display success message
            JOptionPane.showMessageDialog(this, "Patron successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);

            this.setVisible(false); // Close the window
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}