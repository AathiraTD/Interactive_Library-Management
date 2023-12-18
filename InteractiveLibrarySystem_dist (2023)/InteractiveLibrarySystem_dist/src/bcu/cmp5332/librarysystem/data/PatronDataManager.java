package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.*;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Manages loading and storing patron data from/to a text file.
 */
public class PatronDataManager implements DataManager {

    private static final String RESOURCE = "./resources/data/patrons.txt";
    private static final String SEPARATOR = "::"; // Defines the separator used in the file
    
    /**
     * Loads patron data from a file and adds them to the library.
     *
     * @param library The library to which the patrons will be added.
     * @throws IOException If an IO error occurs.
     * @throws LibraryException If a parsing or data consistency error occurs.
     */
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        File file = new File(RESOURCE);
        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0; // Add line number tracking
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();
                String[] properties = line.split("::");
                try {
                    int id = Integer.parseInt(properties[0]);
                    String name = properties[1];
                    String phoneNumber = properties[2];
                    boolean isDeleted = parseIsDeleted(properties, id);
                    // Additional parsing for other fields
                    Patron patron = new Patron(id, name, phoneNumber,isDeleted);
                    // Additional logic for adding patron to library
                    linkBorrowedBooks(patron, properties, library);
                    library.addPatron(patron);
                } catch (NumberFormatException e) {
                    throw new LibraryException("Number format error in file " + RESOURCE 
                        + " at line " + lineNumber + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            throw new LibraryException("The file " + RESOURCE + " was not found.");
        }
    }


    /**
     * Links borrowed books to the patron based on the data from the file.
     *
     * @param patron The patron to link books to.
     * @param parts The data parts from the file line.
     * @param library The library to retrieve book data from.
     * @throws LibraryException If a book is not found in the library.
     */
    private void linkBorrowedBooks(Patron patron, String[] properties, Library library) throws LibraryException {
        if (properties.length > 3) {
            String[] borrowedBookIds = properties[3].split(",");
            for (String bookIdStr : borrowedBookIds) {
                if (!bookIdStr.isEmpty()) {
                    int bookId = Integer.parseInt(bookIdStr.trim());
                    Book book = library.getBookByID(bookId);
                    if (book != null) {
                        patron.borrowBook(book);
                    } else {
                        throw new LibraryException("Book ID " + bookId + " not found for patron " + patron.getId());
                    }
                }
            }
        }
    }
    
    private boolean parseIsDeleted(String[] properties, int Id) {
        // Assuming 'isDeleted' is the fifth element (index 4) in the properties array
        if (properties.length > 4 && !properties[4].isEmpty()) {
            return "true".equals(properties[4].trim().toLowerCase());
        }
        // Default to false if 'isDeleted' is not present or is empty
        return false;
    }

    /**
     * Stores patron data to a file from the library.
     *
     * @param library The library whose patron data will be stored.
     * @throws IOException If an IO error occurs.
     */
    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Patron patron : library.getPatrons()) {
                out.print(patron.getId() + SEPARATOR);
                out.print(patron.getName() + SEPARATOR);
                out.print(patron.getPhoneNumber() + SEPARATOR);
                
                // Handling borrowed book IDs
                String borrowedBookIds = patron.getBorrowedBooks().stream()
                                               .map(Book::getId)
                                               .map(String::valueOf)
                                               .collect(Collectors.joining(","));
                if (borrowedBookIds.isEmpty()) {
                    out.print(""); // Write 'none' or a similar placeholder for no borrowed books
                } else {
                    out.print(borrowedBookIds);
                }
                
                // Writing the isDeleted status
                out.println(SEPARATOR + patron.isDeleted());
            }
        } catch (IOException ex) {
            throw new IOException("Failed to write to " + RESOURCE, ex);
        }
    }

}
