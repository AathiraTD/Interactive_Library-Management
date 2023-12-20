package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.model.Book.Status;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

public class MainWindow extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu membersMenu;

    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;	
    private JMenuItem booksIssue;
    private JMenuItem booksReturn;
    private JMenuItem booksRenew;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;
    
    //private JButton viewPatron;

    private Library library;

    public MainWindow(Library library) {

        initialize();
        this.library = library;
    } 
    
    public Library getLibrary() {
        return library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksReturn = new JMenuItem("Return");
        booksRenew = new JMenuItem("Renew");
        
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksIssue);
        booksMenu.add(booksReturn);
        booksMenu.add(booksRenew);
        
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Members");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);

        setSize(1100, 500); //width was 800

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
    public static void main(String[] args) throws IOException, LibraryException {
        Library library = LibraryData.load();
        new MainWindow(library);			
    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();
            
        } else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);
            
        } else if (ae.getSource() == booksDel) {
            
            
        } else if (ae.getSource() == booksIssue) {
           new IssueBookWindow(this);
           
        } else if (ae.getSource() == booksReturn) {
           new ReturnBookWindow(this);
            
        } else if (ae.getSource() == booksRenew) {
            new RenewBookWindow(this);
            
         } else if (ae.getSource() == memView) {
            displayPatrons();
            
        } else if (ae.getSource() == memAdd) {
            new AddPatronWindow(this);
            
        } else if (ae.getSource() == memDel) {
            
            
        }
    }

    public void displayBooks() {
        List<Book> booksList = library.getBooks();
        // headers for the table
        String[] columns = new String[]{"Title", "Author", "Pub Date", "Status"};

        Object[][] data = new Object[booksList.size()][6];
        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);
            data[i][0] = book.getTitle();
            data[i][1] = book.getAuthor();
            data[i][2] = book.getPublicationYear();
            data[i][3] = book.getStatus();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
    
    public void displayPatrons() {
        List<Patron> patronsList = library.getPatrons();
        // headers for the table
        String[] columns = new String[]{"Name", "Phone number", "Email", "Loaned Books"};

        Object[][] data = new Object[patronsList.size()][6];
        for (int i = 0; i < patronsList.size(); i++) {
            Patron patron = patronsList.get(i);
            data[i][0] = patron.getName();
            data[i][1] = patron.getPhoneNumber();
            data[i][2] = patron.getEmail();
//            if (!patron.getBorrowedBooks().isEmpty()) {    //lists names of borrowed books
//            	List<Book> borrowedList = patron.getBorrowedBooks();
//            	//List<String> namesList = Collections.emptyList();
//            	String names = "";
//            	for (int x = 0; x < borrowedList.size(); x++) {
//            		Book book = borrowedList.get(x);
//            		if (x == borrowedList.size() - 1) {
//            			names += book.getTitle();
//            		} else {
//            			names += book.getTitle() + ", ";
//            		}
//            	}
//            	data[i][3] = names;
//            } else {
//            	data[i][3] = "None";
//            }
            if (!patron.getBorrowedBooks().isEmpty()) {
            	List<Book> borrowedList = patron.getBorrowedBooks(); //lists number of borrowed books
            	data[i][3] = borrowedList.size() + " book(s)";
            } else {
            	data[i][3] = "None";
            }
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
    
}
