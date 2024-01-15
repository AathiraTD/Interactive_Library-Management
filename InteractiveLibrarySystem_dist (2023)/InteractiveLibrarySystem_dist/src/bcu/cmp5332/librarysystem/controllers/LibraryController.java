package bcu.cmp5332.librarysystem.controllers;

import bcu.cmp5332.librarysystem.commands.AddBook;
import bcu.cmp5332.librarysystem.commands.AddPatron;
import bcu.cmp5332.librarysystem.commands.HideBooks;
import bcu.cmp5332.librarysystem.commands.HidePatrons;
import bcu.cmp5332.librarysystem.commands.RenewBook;
import bcu.cmp5332.librarysystem.commands.ReturnBook;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.gui.MainWindow;
import bcu.cmp5332.librarysystem.utils.GuiMessageDisplayer;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import java.time.LocalDate;
import java.util.List;

/**

Controller class that handles the execution of various library operations.

This includes adding books and patrons, renewing and returning books, among others.

It acts as an intermediary between the GUI and the underlying command classes.
*/

public class LibraryController implements Controller {

    private final Library library;

    /**

    Constructs a LibraryController with a reference to the Library and the main GUI window.
    @param library The library which this controller will manipulate.
    @param mainWindow The main window of the application's GUI.
    */
    
    public LibraryController(Library library, MainWindow mainWindow) {
        this.library = library;
    }
    
    /**

    Executes a specific action based on the provided command string.
    This can include actions like adding a book or a patron, based on the command.
    @param actionCommand The command that dictates which action to perform.
    */

    @Override
    public void executeAction(String actionCommand) {
        switch (actionCommand) {
            case "addBook":
                // Implement the logic to open AddBookWindow or handle adding a book
                break;
            // Add other cases like "addPatron", "returnBook", "renewBook", etc.
            default:
                throw new IllegalArgumentException("Unknown command: " + actionCommand);
        }
    }
    
    /**

    Adds a book to the library.
    @param title The title of the book to add.
    @param author The author of the book.
    @param publicationYear The publication year of the book.
    @param publisher The publisher of the book.
    @throws LibraryException If there is an error in adding the book.
    */

    // Create and execute the AddBook command
    public void addBook(String title, String author, String publicationYear, String publisher) throws LibraryException {
        try {
            AddBook addBookCommand = new AddBook(title, author, publicationYear, publisher);
            addBookCommand.execute(library, LocalDate.now(), new GuiMessageDisplayer());
        } catch (LibraryException e) {
        	throw new LibraryException("Error adding book: " + e.getMessage());
        }
    }
    
    /**
     * Adds a patron to the library.
     *
     * @param name    The name of the patron.
     * @param phone   The phone number of the patron.
     * @param email   The email address of the patron.
     * @param bookIds The IDs of books associated with the patron.
     * @throws LibraryException If there is an error in adding the patron.
     */
    
    // Create and execute the AddPatron command
    public void addPatron(String name, String phone, String email, List<Integer> bookIds) throws LibraryException {
    	try {
    		AddPatron addPatronCommand = new AddPatron(name, phone, email, bookIds);
    		addPatronCommand.execute(library, LocalDate.now(), new GuiMessageDisplayer());
    	} catch (LibraryException e) {
        	throw new LibraryException("Error adding patron: " + e.getMessage());
        }
    }
    
    /**
     * Hides books from the library system by their IDs.
     *
     * @param bookIds The list of book IDs to be hidden.
     * @throws LibraryException If there is an error in hiding the books.
     */
    
  // Create and execute the HideBooks command to hide books by their IDs
    public void hideBooks(List<Integer> bookIds) throws LibraryException {
        try {
            HideBooks hideBooksCommand = new HideBooks(bookIds);
            hideBooksCommand.execute(library, LocalDate.now(), new GuiMessageDisplayer());
        } catch (LibraryException e) {
            throw new LibraryException("Error hiding books: " + e.getMessage());
        }
    }
    
    /**
     * Deletes patrons with the specified patron IDs.
     *
     * @param patronIds The list of patron IDs to be deleted.
     * @throws LibraryException If there are issues with deleting patrons.
     */
    public void deletePatron(List<Integer> patronIds) throws LibraryException {
        try {
            HidePatrons hidePatronsCommand = new HidePatrons(patronIds);
            MessageDisplayer guiDisplayer = new GuiMessageDisplayer();
            hidePatronsCommand.execute(library, LocalDate.now(), guiDisplayer);
        } catch (LibraryException e) {
            throw new LibraryException("Error deleting patron(s): " + e.getMessage());
        }
    }

    /**
     * Renews a book for a patron.
     *
     * @param patronId The ID of the patron who wants to renew the book.
     * @param bookId   The ID of the book to be renewed.
     * @throws LibraryException If there are issues with renewing the book.
     */
    public void renewBook(int patronId, int bookId) throws LibraryException {
        try {
            RenewBook renewBookCommand = new RenewBook(patronId, bookId);
            MessageDisplayer guiDisplayer = new GuiMessageDisplayer();
            renewBookCommand.execute(library, LocalDate.now(), guiDisplayer);
        } catch (LibraryException e) {
            throw new LibraryException("Error renewing the book: " + e.getMessage());
        }
    }
    
    /**
     * Returns a book to the library.
     *
     * @param patronId The ID of the patron returning the book.
     * @param bookId   The ID of the book to be returned.
     * @throws LibraryException If there are issues with returning the book.
     */
    public void returnBook(int patronId, int bookId) throws LibraryException {
        try {
            ReturnBook returnBookCommand = new ReturnBook(patronId, bookId);
            MessageDisplayer guiDisplayer = new GuiMessageDisplayer();
            returnBookCommand.execute(library, LocalDate.now(), guiDisplayer);
        } catch (LibraryException e) {
            throw new LibraryException("Error returning the book: " + e.getMessage());
        }
    }

}
