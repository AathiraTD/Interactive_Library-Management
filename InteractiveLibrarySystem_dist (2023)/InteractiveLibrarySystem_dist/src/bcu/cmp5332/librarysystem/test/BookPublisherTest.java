package bcu.cmp5332.librarysystem.test;

import bcu.cmp5332.librarysystem.commands.*;
import bcu.cmp5332.librarysystem.main.*;
import bcu.cmp5332.librarysystem.data.*;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.utils.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains unit tests for the BookPublisher program. It tests the functionality of saving and loading books in the library.
 * 
 * <p>These tests ensure that books can be added to the library, stored in a file, and then loaded back with their properties intact.</p>
 * 
 * @author [Aathira]
 * @version 1.0
 * @since [15/01/2024]
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookPublisherTest {

    @TempDir
    Path tempDir;

    private Library library;


    /**
     * This test method checks the functionality of saving and loading a book in the library.
     * It adds a book, stores it, and then loads it back to verify its properties.
     *
     * @throws LibraryException if there is an error adding the book.
     * @throws IOException      if an I/O error occurs during testing.
     */
    @Test
    void saveAndLoadBookTest() throws LibraryException, IOException {
    	 // Load library data from files
	    Library library = MockLibraryData.load();
        Command addBook = new AddBook("1984", "George Orwell", "1949", "Secker & Warburg");
        addBook.execute(library, LocalDate.now(), new CliMessageDisplayer());

        MockLibraryData.store(library);

        Library reloadedLibrary = MockLibraryData.load();
        List<Book> book = reloadedLibrary.getBooks().stream()
                .collect(Collectors.toList());

        assertNotNull(book, "Stored book should not be null");
        
      // Get the last book in the list
        Book storedBook = book.get(book.size() - 1);

        assertAll("stored book properties",
            () -> {
                System.out.println("Checking title for stored book...");
                assertEquals("1984", storedBook.getTitle());
            },
            () -> {
                System.out.println("Checking author for stored book...");
                assertEquals("George Orwell", storedBook.getAuthor());
            },
            () -> {
                System.out.println("Checking publication year for stored book...");
                assertEquals("1949", storedBook.getPublicationYear());
            },
            () -> {
                System.out.println("Checking publisher for stored book...");
                assertEquals("Secker & Warburg", storedBook.getPublisher());
            }
        );
    }

}
