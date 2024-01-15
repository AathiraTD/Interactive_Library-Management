package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.controllers.LibraryController;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class AddBookWindow extends JFrame implements ActionListener {

    /**
	 * This class is responsible for providing a small graphical interface for adding a new book to the library system.
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
    private JTextField titleText = new JTextField();
    private JTextField authText = new JTextField();
    private JTextField pubDateText = new JTextField();
    private JTextField publText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");
    
    private LibraryController controller;
    
    /**
     * Constructs a AddBookWindow instance for the Library Management System.
     * @param mw The main window instance to work with.
     */

    public AddBookWindow(MainWindow mw, LibraryController controller) {
    	this.mw = mw;
        initialize();
        this.controller = controller;
    }

    /**
     * Initializes the UI components and configures the layout and event listeners.
     * Creates input fields for the book title, author, publishing date, and publisher.
     * 
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        } 

        setTitle("Add a New Book");

        setSize(300, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2)); //5, 2
        topPanel.add(new JLabel("Title : "));
        topPanel.add(titleText);
        topPanel.add(new JLabel("Author : "));
        topPanel.add(authText);
        topPanel.add(new JLabel("Publishing Date : "));
        topPanel.add(pubDateText);
        topPanel.add(new JLabel("Publisher : "));
        topPanel.add(publText);

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
     * Calls the addBook method if add button is pressed.
     * @param ae The action event that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
			addBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }
    
    /**
     * Adds a book using the provided book title, author, publishing date, and publisher.
     */

    private void addBook() {
        String title = titleText.getText();
		String author = authText.getText();
		String publicationYear = pubDateText.getText();
		String publisher = publText.getText();
		
		try {
		// Delegate the action to the controller
		controller.addBook(title, author, publicationYear, publisher);

		// Refresh the main window display
        mw.displayBooks();
        
		// Display success message and close the window
		JOptionPane.showMessageDialog(this, "Book: " + title + " successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);
		this.setVisible(false);
		} catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}