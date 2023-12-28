package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;
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
     *
     * @param title            The title of the book.
     * @param author           The author of the book.
     * @param publicationYear  The year of publication of the book.
     * @param publisher        The publisher of the book.
     */
    public AddBook(String title, String author, String publicationYear, String publisher) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }
    
    /**
     * Executes the command to add a new book.
     *
     * @param library           The library to which the book is to be added.
     * @param currentDate       The current date (not used in this command).
     * @param messageDisplayer  The mechanism for displaying messages to the user.
     * @throws LibraryException if there is an error during the process.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        // Find the maximum ID of existing books to ensure unique ID for new book
        int maxId = library.getBooks().stream().mapToInt(Book::getId).max().orElse(0);

        // Check if any input is blank, throw an exception if so
        if (title.isBlank() || author.isBlank() || publicationYear.isBlank() || publisher.isBlank()) {
            throw new LibraryException("Inputs cannot be empty.");
        } else {
            // Create a new book instance with a unique ID
            Book book = new Book(++maxId, title, author, publicationYear, publisher);
            // Add the new book to the library
            library.addBook(book);
            try {
                // Save the updated library data
                LibraryData.store(library);
                // Display a success message
                messageDisplayer.displayMessage("Book #" + book.getId() + " added.");
            } catch (IOException e) {
                // Throw a LibraryException if there is an error saving the library data
                throw new LibraryException("Error saving the library data: " + e.getMessage());
            } 
        }
    }
}
