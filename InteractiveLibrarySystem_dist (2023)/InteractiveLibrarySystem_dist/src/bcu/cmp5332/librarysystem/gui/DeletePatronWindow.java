package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.controllers.LibraryController;
import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeletePatronWindow extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
    private JTextField patronIdText = new JTextField();
    //private JTextField patronIdText = new JTextField();
    private JButton deleteBtn = new JButton("Delete");
    private JButton cancelBtn = new JButton("Cancel");
    private JCheckBox check = new JCheckBox("Are you sure?");
    private boolean isChecked = false;
    private LibraryController controller;

    public DeletePatronWindow(MainWindow mw, LibraryController controller) {
        this.mw = mw;
        this.controller = controller;
        initialize();
    }

    private void initialize() {
        // Set the title and size of the window
        setTitle("Delete Patron");
        setSize(300, 150);

        // Create top panel for user input
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 2)); //3, 2

        // Add components to top panel
        topPanel.add(new JLabel("     ")); // Spacer
        topPanel.add(new JLabel("     "));
        topPanel.add(new JLabel("Patron ID: "));
        topPanel.add(patronIdText);
        
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
        
        check.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
            	if (e.getStateChange() == 1) {
            		isChecked = true;
            	} else {
            		isChecked = false;
            	}
             }
          });

        // Add panels to the frame
        this.getContentPane().add(topPanel, BorderLayout.NORTH);
        this.getContentPane().add(middlePanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        // Make the window visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deleteBtn) {
        	if(isChecked == true) {
        		deletePatron();
        	}
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false); // Close the window without returning a book
        }
    }

    /**
     * Deletes a patron using the provided patron ID.
     */
    private void deletePatron() {
        try {
            int patronId = Integer.parseInt(patronIdText.getText());
            List<Integer> patronIds = new ArrayList<>();
            patronIds.add(patronId);

            // Call the controller method to delete the patron
            controller.deletePatron(patronIds);

            // Refresh the main window display
            mw.displayPatrons();

            // Close the window
            setVisible(false);

            // Show success message
            JOptionPane.showMessageDialog(this,
                    "Patron successfully deleted.",
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