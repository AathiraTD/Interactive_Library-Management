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
	 * 
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


    public AddBookWindow(MainWindow mw, LibraryController controller) {
    	this.mw = mw;
        initialize();
        this.controller = controller;
    }

    /**
     * Initialize the contents of the frame.
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
			addBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

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