package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.model.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ViewBookDetails extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow mw;
	private List<Book> booksList;

    //private JButton addBtn = new JButton("Add");
    private JButton exitBtn = new JButton("Exit");
    

    public ViewBookDetails(MainWindow mw, List<Book> booksList) {
        this.mw = mw;
        this.booksList = booksList;
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
        
        setTitle("Book Details");

        setSize(600, 200);
        // headers for the table
        String[] columns = new String[]{"Title", "Author", "Pub Date", "Status"};

      //Shows books which haven't been deleted
        Object[][] data = new Object[booksList.size()][6];
        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);
            if(!book.isDeleted()) {
	            data[i][0] = book.getTitle();
	            data[i][1] = book.getAuthor();
	            data[i][2] = book.getPublicationYear();
	            data[i][3] = book.getStatus();
            }
        }

        //viewPatron = new JButton();
        //viewPatron.addActionListener(this);
        JTable table = new JTable(data, columns);
        setLocationRelativeTo(mw);
        
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == exitBtn) {
        	this.setVisible(false);
        }
    }

//    private static List<Integer> parseBookIds(String line) {
//        List<Integer> bookIds = new ArrayList<>();
//        for (String part : line.split(",")) {
//            try {
//                bookIds.add(Integer.parseInt(part.trim()));
//            } catch (NumberFormatException e) {
//                // Log the error or notify the user about the invalid book ID
//            }
//        }
//        return bookIds;
//    }
    
//    private void addPatron() {
//        try {
//            String name = nameText.getText();
//            String phone = phoneText.getText();
//            String email = emailText.getText();
//            String bookIdsLine = bookText.getText();
//            if (name == null || phone == null || email == null || bookIdsLine == null) {
//            	throw new LibraryException("Inputs cannot be empty.");
//            }
//            List<Integer> bookIds = parseBookIds(bookIdsLine);
//            // create and execute the AddBook Command
//            Command addPatron = new AddPatron(name, phone, email, bookIds);
//            addPatron.execute(mw.getLibrary(), LocalDate.now());
//            // refresh the view with the list of books
//            mw.displayBooks();
//            // hide (close) the AddBookWindow
//            this.setVisible(false);
//        } catch (LibraryException ex) {
//            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }

}