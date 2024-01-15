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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookPublisherTest {

    @TempDir
    Path tempDir;

    private Library library;

    @BeforeEach
    void setUp() throws IOException, LibraryException {
        Path tempFile = tempDir.resolve("tempBooks.txt");
        System.setProperty("bookdata.filepath", tempFile.toString());
        library = MockLibraryData.load();
    }

    @AfterAll
    static void tearDown() {
        System.clearProperty("bookdata.filepath");
    }


//    @Test
//    void addBookTest() throws LibraryException, IOException {
//        Command addBook = new AddBook("The Great Gatsby", "F. Scott Fitzgerald", "1925", "Charles Scribner's Sons");
//        addBook.execute(library, LocalDate.now(), new CliMessageDisplayer());
//
//        int booksCount = library.getBooks().size();
//        System.out.println("Number of books after adding: " + booksCount);
//        assertTrue(booksCount > 0, "Book should be added to the library");
//
//        LibraryData.store(library);
//
//        Library reloadedLibrary = LibraryData.load();
//        Book addedBook = reloadedLibrary.getBooks().get(reloadedLibrary.getBooks().size() - 1);
//
//        assertAll("book properties",
//            () -> {
//                System.out.println("Checking title...");
//                assertEquals("The Great Gatsby", addedBook.getTitle());
//            },
//            () -> {
//                System.out.println("Checking author...");
//                assertEquals("F. Scott Fitzgerald", addedBook.getAuthor());
//            },
//            () -> {
//                System.out.println("Checking publication year...");
//                assertEquals("1925", addedBook.getPublicationYear());
//            },
//            () -> {
//                System.out.println("Checking publisher...");
//                assertEquals("Charles Scribner's Sons", addedBook.getPublisher());
//            }
//        );
//    }


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
