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
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("::");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String phoneNumber = parts[2];

                Patron patron = new Patron(id, name, phoneNumber);
                linkBorrowedBooks(patron, parts, library);
                library.addPatron(patron);
            }
        } catch (FileNotFoundException e) {
            throw new LibraryException("The file " + RESOURCE + " was not found.");
        } catch (NumberFormatException e) {
            throw new LibraryException("Number format error in file " + RESOURCE);
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
    private void linkBorrowedBooks(Patron patron, String[] parts, Library library) throws LibraryException {
        if (parts.length > 3) {
            String[] borrowedBookIds = parts[3].split(",");
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
                String borrowedBookIds = patron.getBorrowedBooks().stream()
                                                .map(Book::getId)
                                                .map(String::valueOf)
                                                .collect(Collectors.joining(","));
                out.println(patron.getId() + "::" + patron.getName() + "::" + patron.getPhoneNumber() + "::" + borrowedBookIds);
            }
        } catch (IOException ex) {
            throw new IOException("Failed to write to " + RESOURCE, ex);
        }
    }
}
