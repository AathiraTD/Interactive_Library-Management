package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;
import bcu.cmp5332.librarysystem.utils.Validator;
import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Command to add a new book to the library system.
 */
public class AddBook implements Command {

    private final String title;
    private final String author;
    private final String publicationYear;
    private final String publisher;

    /**
     * Constructs an AddBook command with book details.
     * Validates the inputs to ensure they are not blank.
     *
     * @param title            The title of the book.
     * @param author           The author of the book.
     * @param publicationYear  The publication year of the book.
     * @param publisher        The publisher of the book.
     * @throws LibraryException if any input parameter is invalid.
     */
    public AddBook(String title, String author, String publicationYear, String publisher) throws LibraryException {
        // Validate input parameters using the Validator class
        String validationMessage = Validator.validateBookDetails(title, author, publicationYear, publisher);
        if (validationMessage != null) {
            // If validation fails, throw an exception with the validation message
            throw new LibraryException(validationMessage);
        }
        // Assigning validated values to the fields
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }
    
    /**
     * Executes the command to add a new book.
     * Assigns a unique ID to the new book and updates the library data.
     *
     * @param library           The library to which the book is to be added.
     * @param currentDate       The current date (can be removed if not required).
     * @param messageDisplayer  The mechanism for displaying messages to the user.
     * @throws LibraryException if there is an error during the process.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        // Generating a unique ID for the new book
        int maxId = library.getBooks().isEmpty() ? 0 : library.getBooks().stream().mapToInt(Book::getId).max().getAsInt();
        
        // Creating a new book instance with the generated ID
        Book book = new Book(++maxId, title, author, publicationYear, publisher);
        // Adding the new book to the library
        library.addBook(book);
        try {
            // Saving the updated library data
            LibraryData.store(library);
            // Displaying a success message
            messageDisplayer.displayMessage(book.getDetailsShort()+ " added.");
        } catch (IOException e) {
            // Handling IO exceptions and converting them to LibraryException
            throw new LibraryException("Error saving the library data: " + e.getMessage());
        } 
    }
}
