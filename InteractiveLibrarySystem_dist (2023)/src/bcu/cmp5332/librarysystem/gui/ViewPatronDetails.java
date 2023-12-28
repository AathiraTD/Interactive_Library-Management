package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.model.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class ViewPatronDetails extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    private Patron patron;
    private JTable table;

    //private JButton addBtn = new JButton("Add");
    //private JButton exitBtn = new JButton("Exit");
    
    public ViewPatronDetails(Patron patron) {
    	this.patron = patron;
        
        this.setTitle("Patron Details");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose of the frame when closed
        this.setLayout(new BorderLayout());

        // Headers for the table
        String[] columns = new String[]{"Name", "Phone Number", "Email"};

        // Show books which haven't been deleted
        Object[][] data = new Object[1][4];
        int rowIndex = 0;
            if (!patron.isDeleted()) {
                data[rowIndex][0] = patron.getName();
                data[rowIndex][1] = patron.getPhoneNumber();
                data[rowIndex][2] = patron.getEmail();
                rowIndex++;
            }

        table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        this.add(scrollPane, BorderLayout.CENTER);

        pack(); // Auto-adjust the size of the frame
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
}