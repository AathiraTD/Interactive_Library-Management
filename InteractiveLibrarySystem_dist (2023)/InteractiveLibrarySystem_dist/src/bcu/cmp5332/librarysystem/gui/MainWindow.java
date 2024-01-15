package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.commands.*;
import bcu.cmp5332.librarysystem.controllers.LibraryController;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.utils.GuiMessageDisplayer;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The MainWindow class represents the primary user interface of the Library Management System.
 * It adheres to the Model-View-Controller (MVC) pattern, acting as the View component.
 * This class is responsible for rendering the GUI elements and delegating user actions to the controller.
 */
public class MainWindow extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    // UI Components
    private JMenuBar menuBar;
    private JMenu adminMenu, booksMenu, membersMenu;
    private JMenuItem adminViewLoan,adminExit, booksView, booksAdd, booksDel, booksIssue, booksReturn, booksRenew;
    private JMenuItem memView, memAdd, memDel;
    private JButton viewPatron;
    private MessageDisplayer guiDisplayer = new GuiMessageDisplayer();

    // Controller and model references
    private final LibraryController libraryController;
    private final Library library;

    /**
     * Constructs a MainWindow instance for the Library Management System.
     * Initializes the library controller and sets up the UI components.
     * @param library The library instance to work with.
     * @throws LibraryException If an issue arises during initialization.
     */
    public MainWindow(Library library) throws LibraryException {
        this.library = library;
        this.libraryController = new LibraryController(library, this);
        //Initialize the window and data
        initialize();
    }

    /**
     * Initializes the UI components and configures the layout and event listeners.
     * Sets the look and feel, creates the menu bar, and configures window settings.
     * @throws LibraryException If an issue arises in UI setup.
     */
    private void initialize() throws LibraryException {
        setLookAndFeel();
        setTitle("Library Management System");
        initializeMenuBar();
        configureWindowSettings();
    }

    /**
     * Sets the look and feel of the application to the system default.
     * Ensures the UI is consistent across different platforms.
     * @throws LibraryException If the look and feel setting fails.
     */
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            JOptionPane.showMessageDialog(this, "Unable to set the system look and feel. The application may not look as intended.", "UI Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public Library getLibrary() {
    	return library;
    }

    /**
     * Initializes and adds menus to the menu bar.
     * Creates menus for admin, books, and members, adding respective menu items.
     */
    private void initializeMenuBar() {
        menuBar = new JMenuBar();
        addAdminMenu();
        addBooksMenu();
        addMembersMenu();
        setJMenuBar(menuBar);
    }

    /**
     * Adds the admin menu and its items to the menu bar.
     * Includes options like exiting the application.
     */
    private void addAdminMenu() {
        adminMenu = new JMenu("Admin");
        
        adminViewLoan = new JMenuItem("Loan History");
        adminExit = new JMenuItem("Exit");
        
        adminViewLoan.addActionListener(this);
        adminExit.addActionListener(this);
        
        adminMenu.add(adminViewLoan);
        adminMenu.add(adminExit);
        menuBar.add(adminMenu);
    }

    /**
     * Adds the books menu and its items to the menu bar.
     * Includes options for viewing, adding, deleting, issuing, returning, and renewing books.
     */
    private void addBooksMenu() {
        booksMenu = new JMenu("Books");
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

        // Adding action listeners to each book menu item
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }
        
        menuBar.add(booksMenu);
    }

    /**
     * Adds the members menu and its items to the menu bar.
     * Includes options for viewing, adding, and deleting members.
     */
    private void addMembersMenu() {
        membersMenu = new JMenu("Members");
        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);
        
        // Adding action listeners to each patron menu item
        for (int i = 0; i < membersMenu.getItemCount(); i++) {
        	membersMenu.getItem(i).addActionListener(this);
        }
        
        menuBar.add(membersMenu);
        viewPatron = new JButton();
        viewPatron.addActionListener(this);
        
    }

    /**
     * Configures window settings such as size, visibility, and default close operation.
     * This ensures the window is appropriately sized and behaves correctly on closing.
     */
    private void configureWindowSettings() {
        setSize(1100, 500); // Set the window size
        setVisible(true); // Make the window visible
        setAutoRequestFocus(true); // Window requests focus automatically
        toFront(); // Bring the window to the front
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Set the default close operation
    }

    /**
     * The main method to run the GUI version of the Library Management System.
     * Initializes the system with the library data and links books to loans.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
        	//Loading the data - required if running GUI directly
            Library library = LibraryData.load();
            new MainWindow(library);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to load library data from file.", "Initialization Error", JOptionPane.ERROR_MESSAGE);
        } catch (LibraryException e) {
            JOptionPane.showMessageDialog(null, "An error occurred in initializing the Library System.", "Initialization Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Handles action events triggered by UI components.
     * Delegates actions to specific methods or classes based on the source of the event.
     * @param ae The action event that occurred.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            // Exit the application
            if (ae.getSource() == adminExit) {
                System.exit(0);
            }
            //Display Loan History
            else if (ae.getSource() == adminViewLoan) {
               displayLoans();
            }
            
            // Display the list of books
            else if (ae.getSource() == booksView) {
                displayBooks();
            }
            // Open the window to add a new book
            else if (ae.getSource() == booksAdd) {
                openAddBookWindow();
            }
            // Open the window to delete a book
            else if (ae.getSource() == booksDel) {
                openDeleteBookWindow();
            }
            // Open the window to issue a book
            else if (ae.getSource() == booksIssue) {
                openIssueBookWindow();
            }
            // Open the window to return a book
            else if (ae.getSource() == booksReturn) {
                openReturnBookWindow();
            }
            // Open the window to renew a book
            else if (ae.getSource() == booksRenew) {
                openRenewBookWindow();
            }
            // Display the list of patrons
            else if (ae.getSource() == memView) {
                displayPatrons();
            }
            // Open the window to add a new patron
            else if (ae.getSource() == memAdd) {
                openAddPatronWindow();
            }
            // Open the window to delete a patron
            else if (ae.getSource() == memDel) {
                openDeletePatronWindow();
            }
            // Add additional else if blocks for other actions
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage(), "Action Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the window to add a new book.
     * This method creates and displays the Add Book window. It handles any exceptions that occur during the window opening.
     */
    private void openAddBookWindow() {
        try {
            new AddBookWindow(this, libraryController).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening Add Book window: " + e.getMessage(), "Window Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the window to delete a book.
     * This method creates and displays the Delete Book window. It handles any exceptions that occur during the window opening.
     */
    private void openDeleteBookWindow() {
        try {
            new DeleteBookWindow(this, libraryController).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening Delete Book window: " + e.getMessage(), "Window Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the window to issue a book.
     * This method creates and displays the Issue Book window. It handles any exceptions that occur during the window opening.
     */
    private void openIssueBookWindow() {
        try {
            new IssueBookWindow(this, libraryController).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening Issue Book window: " + e.getMessage(), "Window Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the window to return a book.
     * This method creates and displays the Return Book window. It handles any exceptions that occur during the window opening.
     */
    private void openReturnBookWindow() {
        try {
            new ReturnBookWindow(this, libraryController).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening Return Book window: " + e.getMessage(), "Window Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the window to renew a book.
     * This method creates and displays the Renew Book window. It handles any exceptions that occur during the window opening.
     */
    private void openRenewBookWindow() {
        try {
            new RenewBookWindow(this, libraryController).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening Renew Book window: " + e.getMessage(), "Window Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the window to add a new patron.
     * This method creates and displays the Add Patron window. It handles any exceptions that occur during the window opening.
     */
    private void openAddPatronWindow() {
        try {
            new AddPatronWindow(this, libraryController).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening Add Patron window: " + e.getMessage(), "Window Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Opens the window to delete a patron.
     * This method creates and displays the Delete Patron window. It handles any exceptions that occur during the window opening.
     */
    private void openDeletePatronWindow() {
        try {
            new DeletePatronWindow(this, libraryController).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error opening Delete Patron window: " + e.getMessage(), "Window Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Displays the history of loans in the library.
     * Creates and shows a view for all loans (open and closed) in the library.
     */
    public void displayLoans() {
    	LoanHistoryView loansHistoryView = new LoanHistoryView(this, library);
        Command listLoansCommand = new ListLoans(loansHistoryView,guiDisplayer);
        executeCommand(listLoansCommand);
    }

    
    /**
     * Displays the list of books in the library.
     * Creates and shows a view for the book list, executing the ListBooks command.
     */
    public void displayBooks() {
        BooksListView booksListView = new BooksListView(this, library);
        Command listBooksCommand = new ListBooks(booksListView, guiDisplayer);
        executeCommand(listBooksCommand);
    }

    /**
     * Displays the list of patrons in the library.
     * Creates and shows a view for the patrons list, executing the ListPatrons command.
     */
    public void displayPatrons() {
        PatronsListView patronsListView = new PatronsListView(this, library);
        Command listPatronsCommand = new ListPatrons(patronsListView, guiDisplayer);
        executeCommand(listPatronsCommand);
    }

    /**
     * Executes a given command within the library system.
     * This method serves as a central point for executing various commands that interact with the library model.
     * It handles the execution of commands and any exceptions that arise during the process.
     *
     * The LibraryException is used to indicate issues within the command execution, such as invalid operations
     * or issues with the library data. If a command fails, the method displays an error message to the user,
     * and the exception details are logged for debugging purposes.
     *
     * @param command The command to execute.
     */
    private void executeCommand(Command command) {
        try {
            command.execute(library, LocalDate.now(), guiDisplayer);
        } catch (LibraryException e) {
            JOptionPane.showMessageDialog(this, "Error executing command: " + e.getMessage(), "Execution Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Consider replacing this with a logger if available
        }
    }

}
