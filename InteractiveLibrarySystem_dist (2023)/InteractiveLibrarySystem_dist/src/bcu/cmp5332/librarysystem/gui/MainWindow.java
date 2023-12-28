package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.commands.ListBooks;
import bcu.cmp5332.librarysystem.commands.ListPatrons;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.utils.GuiMessageDisplayer;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The MainWindow class represents the main graphical user interface for the Library Management System.
 * It allows users to perform various operations related to books and patrons.
 */
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

    private JButton viewPatron;

    private MessageDisplayer guiDisplayer = new GuiMessageDisplayer();

    private Library library;

    /**
     * Constructs a MainWindow instance for the Library Management System.
     *
     * @param library The library instance to work with.
     */
    public MainWindow(Library library) {
        this.library = library;
        initialize();
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
            // Handle the exception
        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // Adding booksMenu menu and menu items
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

        // Adding membersMenu menu and menu items
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

        viewPatron = new JButton();
        viewPatron.addActionListener(this);
        new ArrayList<Book>();

        setSize(1100, 500); // Width was 800

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * The main method to run the GUI version of the Library Management System.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Handle the exception
        }

        try {
            Library library = LibraryData.load();
            library.linkBooksToLoans();
            new MainWindow(library);
        } catch (IOException | LibraryException e) {
            // Handle any exceptions
            e.printStackTrace();
        }
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
            new DeleteBookWindow(this);
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
            new DeletePatronWindow(this);
        }
    }


    /**
     * Displays the list of books in the library.
     */
    public void displayBooks() {
        // Initialize BooksListView with the current JFrame context
        BooksListView booksListView = new BooksListView(this);

        // Execute ListBooks command with the view as a parameter
        Command listBooksCommand = new ListBooks(booksListView, guiDisplayer);

        try {
            listBooksCommand.execute(library, LocalDate.now(), guiDisplayer);
        } catch (LibraryException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

    /**
     * Displays the list of patrons in the library.
     */
    public void displayPatrons() {
        PatronsListView patronsListView = new PatronsListView(this, library);
        Command listPatronsCommand = new ListPatrons(patronsListView, guiDisplayer);

        try {
            listPatronsCommand.execute(library, LocalDate.now(), guiDisplayer);
        } catch (LibraryException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }
}
