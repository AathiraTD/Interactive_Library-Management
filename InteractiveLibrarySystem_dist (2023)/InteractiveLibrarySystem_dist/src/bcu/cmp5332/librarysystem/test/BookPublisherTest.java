package bcu.cmp5332.librarysystem.test;

import bcu.cmp5332.librarysystem.commands.*;
import bcu.cmp5332.librarysystem.main.*;
import bcu.cmp5332.librarysystem.data.*;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.utils.CliMessageDisplayer;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

class BookPublisherTest {

    @TempDir
    Path tempDir;

    private Library library;
    private MessageDisplayer cliDisplayer;

    @BeforeEach
    void setUp() throws IOException, LibraryException {
        Path tempFile = tempDir.resolve("tempBooks.txt");
        System.setProperty("bookdata.filepath", tempFile.toString());

        // Initialize library and CLI displayer
        library = LibraryData.load();
        cliDisplayer = new CliMessageDisplayer();
    }

    @AfterEach
    void tearDown() {
        System.clearProperty("bookdata.filepath");
    }

    @Test
    void addBookTest() throws LibraryException, IOException {
        Command add = new AddBook("The Great Gatsby", "F. Scott Fitzgerald", "1925", "Charles Scribner's Sons");
        add.execute(library, LocalDate.now(), cliDisplayer);

        // Assuming the last added book is the one we want to test
        Book addedBook = library.getBooks().get(library.getBooks().size() - 1);

        assertEquals("The Great Gatsby", addedBook.getTitle());
        assertEquals("F. Scott Fitzgerald", addedBook.getAuthor());
        assertEquals("1925", addedBook.getPublicationYear());
        assertEquals("Charles Scribner's Sons", addedBook.getPublisher());
        //assertTrue(library.getBooks().stream().anyMatch(book -> book.getTitle().equals("The Great Gatsby")));

    }

    @Test
    void storeBookTest() throws LibraryException, IOException {
        // Step 1: Add a book to the Library instance
        Command add = new AddBook("The Great Gatsby", "F. Scott Fitzgerald", "1925", "Charles Scribner's Sons");
        add.execute(library, LocalDate.now(), cliDisplayer);

        // Assert the book has been added to the library
        assertTrue(library.getBooks().stream().anyMatch(book -> book.getTitle().equals("The Great Gatsby")), "Book should be added");

        // Step 2: Store the state of the Library to the temporary file
        LibraryData.store(library);

        // Step 3: Reload the Library from the same temporary file
        Library reloadedLibrary = LibraryData.load();

        // Step 4: Assert that the reloaded Library contains the added book
        Book storedBook = reloadedLibrary.getBooks().stream()
            .filter(book -> book.getTitle().equals("The Great Gatsby"))
            .findFirst()
            .orElse(null);

          
                assertNotNull(storedBook, "The added book should be present in the reloaded library");
                assertEquals("The Great Gatsby", storedBook.getTitle());
                assertEquals("F. Scott Fitzgerald", storedBook.getAuthor());
                assertEquals("1925", storedBook.getPublicationYear());
                assertEquals("Charles Scribner's Sons", storedBook.getPublisher());
            }
    }
