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
import javax.swing.UIManager;

public class ViewPatronDetails extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
    private JLabel nameText = new JLabel();
    private JLabel phoneText = new JLabel();
    private JLabel emailText = new JLabel();

    //private JButton addBtn = new JButton("Add");
    private JButton exitBtn = new JButton("Exit");
    
    public ViewPatronDetails(MainWindow mw, Patron patron) {
        this.mw = mw;
        this.nameText = new JLabel(patron.getName());
        this.phoneText = new JLabel(patron.getPhoneNumber());
        this.emailText = new JLabel(patron.getEmail());
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }
        
        mw.displayPatrons();
        
        setTitle("Patron Details");

        setSize(400, 200);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(6, 2)); //5, 2
        topPanel.add(new JLabel("     "));
        topPanel.add(new JLabel("     "));
        topPanel.add(new JLabel("Name : "));
        topPanel.add(nameText);
        topPanel.add(new JLabel("Phone Number : "));
        topPanel.add(phoneText);
        topPanel.add(new JLabel("Email : "));
        topPanel.add(emailText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(exitBtn);
        bottomPanel.add(new JLabel("     "));

        exitBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == exitBtn) {
        	this.setVisible(false);
        }
    }
}