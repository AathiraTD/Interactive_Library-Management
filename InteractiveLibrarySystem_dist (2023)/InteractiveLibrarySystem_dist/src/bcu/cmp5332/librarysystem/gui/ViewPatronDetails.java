package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.model.Patron;

import javax.swing.*;
import java.awt.*;

/**
 * The ViewPatronDetails class represents a frame for displaying patron details in a table.
 * It is used to show patron details to the user.
 */
public class ViewPatronDetails extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;

    /**
     * Constructs a ViewPatronDetails frame.
     *
     * @param patron The patron whose details are to be displayed.
     */
    public ViewPatronDetails(Patron patron) {
        this.setTitle("Patron Details");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose of the frame when closed
        this.setLayout(new BorderLayout());

        // Headers for the table
        String[] columns = new String[]{"Name", "Phone Number", "Email"};

        // Data for displaying patron's details
        Object[][] data = new Object[1][3]; // 1 row, 3 columns
        data[0][0] = patron.getName();
        data[0][1] = patron.getPhoneNumber();
        data[0][2] = patron.getEmail();

        table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);

        this.add(scrollPane, BorderLayout.CENTER);

        pack(); // Auto-adjust the size of the frame
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
}
