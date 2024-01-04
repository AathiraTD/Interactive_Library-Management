package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;

/**
 * Represents the central model in the Library Management System.
 * This class is responsible for managing the entities within the library system, including books, patrons, and loans.
 * It serves as a central point for various operations related to library management.
 * 
 * Key functionalities include:
 * - Managing a collection of books and patrons using maps for efficient retrieval.
 * - Providing methods to add, retrieve, and manage books and patrons.
 * - Handling loans, which link patrons to the books they have borrowed.
 * - Offering utility methods to find specific loans and link books to loans based on unique identifiers.
 * 
 * The class follows the principles of encapsulation and data hiding, ensuring that the internal representation
 * of data structures (like maps for books and patrons) is not exposed outside the class. It provides a clean
 * interface to interact with library data, thus making it easier to maintain and modify the system in the future.
 * 
 * Usage of this class typically involves creating an instance of Library and then using its methods to 
 * perform various operations related to library management.
 */
public class Library {
    
    private final int loanPeriod = 14; // Standard loan period for all books
    private final Map<Integer, Patron> patrons = new TreeMap<>(); // Maps patron IDs to their objects
    private final Map<Integer, Book> books = new TreeMap<>(); // Maps book IDs to their objects
    private int maxPatronId = 0; // Tracks the highest patron ID for assigning new IDs
    private final Map<Integer, Loan> loanMap = new HashMap<>(); // Map for efficient loan retrieval

    /**
     * Gets the standard loan period for books.
     * @return The standard loan period in days.
     */
    public int getLoanPeriod() {
        return loanPeriod;
    }

    /**
     * Gets an unmodifiable list of all books in the library.
     * @return An unmodifiable list of books.
     */
    public List<Book> getBooks() {
        // Wrapping the book collection in an unmodifiable list to prevent external modification
        return Collections.unmodifiableList(new ArrayList<>(books.values()));
    }
    
    /**
     * Gets an unmodifiable list of all patrons in the library.
     * @return An unmodifiable list of patrons.
     */
    public List<Patron> getPatrons() {
        // Wrapping the patron collection in an unmodifiable list to prevent external modification
        return Collections.unmodifiableList(new ArrayList<>(patrons.values()));
    }

    /**
     * Retrieves a book by its ID or throws an exception if not found.
     * @param id The ID of the book to retrieve.
     * @return The book object.
     * @throws LibraryException If the book is not found.
     */
    public Book getBookById(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no book with ID " + id);
        }
        return books.get(id);
    }

    /**
     * Retrieves a patron by their ID or throws an exception if not found.
     * @param id The ID of the patron to retrieve.
     * @return The patron object.
     * @throws LibraryException If the patron is not found.
     */
    public Patron getPatronById(int id) throws LibraryException {
        if (!patrons.containsKey(id)) {
            throw new LibraryException("There is no patron with ID " + id);
        }
        return patrons.get(id);
    }

    /**
     * Adds a book to the library.
     * @param book The book to add.
     * @return A message indicating success or error.
     */
    public String addBook(Book book) {
        if (books.containsKey(book.getId())) {
            return "Error: Duplicate book ID.";
        }
        books.put(book.getId(), book);
        return "Book added successfully.";
    }

    /**
     * Adds a patron to the library.
     * @param patron The patron to add.
     * @return A message indicating success or error.
     */
    public String addPatron(Patron patron) {
        if (patrons.containsKey(patron.getId())) {
            return "Error: Duplicate patron ID.";
        }
        patrons.put(patron.getId(), patron);
        if (patron.getId() > maxPatronId) {
            maxPatronId = patron.getId();
        }
        return "Patron added successfully.";
    }

    /**
     * Adds a loan to the library.
     * @param loan The loan to add.
     * @param bookId The ID of the book associated with the loan.
     * @throws LibraryException If there are issues linking the book to the loan.
     */
    public void addLoan(Loan loan, Book book) throws LibraryException {
        linkBookToLoan(loan, book);
        loanMap.put(loan.getLoanId(), loan);
    }

    /**
     * Returns a list of all loans in the library.
     * @return A list of all loans.
     */
    public List<Loan> getAllLoans() {
        return new ArrayList<>(loanMap.values());
    }

    /**
     * Finds a specific loan by book and patron IDs.
     * @param bookId The ID of the book.
     * @param patronId The ID of the patron.
     * @return The loan if found, otherwise null.
     */
    public Loan findLoan(int bookId, int patronId) {
        return loanMap.values().stream()
            .filter(loan -> loan.getBook().getId() == bookId && loan.getPatron().getId() == patronId && loan.isActive())
            .findFirst()
            .orElse(null);
    }

    /**
     * Finds a loan by its ID.
     * @param loanId The ID of the loan.
     * @return The loan if found, otherwise null.
     */
    public Loan findLoanById(int loanId) {
        return loanMap.get(loanId);
    }

    /**
     * Links a specific book to its respective loan using a temporary loan ID.
     * @param loan The loan to link.
     * @param bookId The ID of the book to link to its loan.
     * @throws LibraryException If the book with the specified ID is not found.
     */
    public void linkBookToLoan(Loan loan, Book book) throws LibraryException {
        if (book != null && book.getTemporaryLoanId() == loan.getLoanId()) {
            book.setLoan(loan);
            book.clearTemporaryLoanId(); // Ensuring the temporary ID is cleared after linking
        }
    }
}