package bcu.cmp5332.librarysystem.test;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.DataParserValidator;
import bcu.cmp5332.librarysystem.data.DataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Manages loading and storing book data from/to a text file.
 */
public class MockBookDataManager implements DataManager {
    
    private static final String RESOURCE = "./resources/data/test/books.txt";
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
    	File file = new File(RESOURCE);
    	if (!file.exists()) {
    	    file.createNewFile(); // Create a new file if it doesn't exist
    	}
    	
    	// Check if the file is empty
        if (file.length() == 0) {
            // The file is empty, so no data to load
            return;
        }
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }
//                // Validate the line using the instance of DataParserValidator
//                if (!DataParserValidator.validateBookData(line)) {
//                    throw new LibraryException("Invalid data format in books.txt: " + line);
//                }
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = DataParserValidator.parseInteger(properties[0], "book ID");
                    String title = properties[1];
                    String author = properties[2];
                    String publicationYear = properties[3];
                    String publisher = properties[4];

                    int loanId = (properties.length > 5) ? DataParserValidator.parseInteger(properties[5], "loan ID") : -1;
                    boolean isDeleted = (properties.length > 6) ? Boolean.parseBoolean(properties[6]) : false;
                    Book book = new Book(id, title, author, publicationYear, publisher, isDeleted);
                    book.setTemporaryLoanId(loanId); // Temporarily store loan ID for later processing
                    library.addBook(book);
                } catch (NumberFormatException e) {
                    throw new LibraryException("Invalid number format in books.txt: " + line);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading books.txt: " + e.getMessage());
        }
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
                out.print(book.getPublisher() + SEPARATOR);
                int loanId = (book.getLoan() != null) ? book.getLoan().getLoanId() : -1;
                out.print(loanId + SEPARATOR); // Write the loan ID
                out.println(book.isDeleted()); // Write the isDeleted flag
            }
        } catch (IOException ex) {
            throw new IOException("Failed to write to " + RESOURCE, ex);
        }
    }

}