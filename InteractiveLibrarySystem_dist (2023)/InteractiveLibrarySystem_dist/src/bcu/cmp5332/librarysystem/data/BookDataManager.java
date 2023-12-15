package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Manages loading and storing book data from/to a text file.
 */
public class BookDataManager implements DataManager {
    
    private static final String RESOURCE = "./resources/data/books.txt";
    private static final String SEPARATOR = "::"; // Defines the separator used in the file

    /**
     * Loads book data from a file and adds them to the library.
     *
     * @param library The library to which the books will be added.
     * @throws IOException If an IO error occurs.
     * @throws LibraryException If a parsing or data consistency error occurs.
     */
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                int id = Integer.parseInt(properties[0]);
                String title = properties[1];
                String author = properties[2];
                String publicationYear = properties[3];

                int loanId = parseLoanId(properties, id);
                Book book = new Book(id, title, author, publicationYear);
                book.setTemporaryLoanId(loanId); // Temporarily store loan ID for later processing
                library.addBook(book);
            }
        }
    }

    /**
     * Parses the loan ID from book data properties.
     *
     * @param properties The split data line from the file.
     * @param bookId The book ID for error reporting.
     * @return The parsed loan ID or -1 if not present or invalid.
     */
    private int parseLoanId(String[] properties, int bookId) {
        if (properties.length > 4 && !properties[4].isEmpty()) {
            try {
                return Integer.parseInt(properties[4]);
            } catch (NumberFormatException e) {
                System.err.println("Warning: Invalid loan ID format in books.txt for book ID " + bookId);
                return -1;
            }
        }
        return -1;
    }

    /**
     * Stores book data to a file from the library.
     *
     * @param library The library whose book data will be stored.
     * @throws IOException If an IO error occurs.
     */
    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Book book : library.getBooks()) {
                out.print(book.getId() + SEPARATOR);
                out.print(book.getTitle() + SEPARATOR);
                out.print(book.getAuthor() + SEPARATOR);
                out.print(book.getPublicationYear() + SEPARATOR);
                int loanId = (book.getLoan() != null) ? book.getLoan().getLoanId() : -1;
                out.println(loanId); // Write the loan ID or -1 if no loan
            }
        } catch (IOException ex) {
            throw new IOException("Failed to write to " + RESOURCE, ex);
        }
    }
}
