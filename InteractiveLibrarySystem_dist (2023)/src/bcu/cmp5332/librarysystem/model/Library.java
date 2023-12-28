package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;

/**
 * The Library class represents the library system.
 * It manages patrons, books, loans, and provides various operations for them.
 */
public class Library {
    
    private final int loanPeriod = 7; // Standard loan period for all books
    private final Map<Integer, Patron> patrons = new TreeMap<>(); // Maps patron IDs to their objects
    private final Map<Integer, Book> books = new TreeMap<>(); // Maps book IDs to their objects
    private int maxPatronId = 0; // Tracks the highest patron ID for assigning new IDs
    private final Map<Integer, Loan> loanMap = new HashMap<>(); // Map for efficient loan retrieval

    /**
     * Gets the standard loan period for books.
     *
     * @return The standard loan period in days.
     */
    public int getLoanPeriod() {
        return loanPeriod;
    }

    /**
     * Gets an unmodifiable list of all books in the library.
     *
     * @return An unmodifiable list of books.
     */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(new ArrayList<>(books.values()));
    }
    
    /**
     * Gets an unmodifiable list of all patrons in the library.
     *
     * @return An unmodifiable list of patrons.
     */
    public List<Patron> getPatrons() {
        return Collections.unmodifiableList(new ArrayList<>(patrons.values()));
    }

    /**
     * Retrieves a book by its ID or throws an exception if not found.
     *
     * @param id The ID of the book to retrieve.
     * @return The book object.
     * @throws LibraryException If the book is not found.
     */
    public Book getBookByID(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with that ID.");
        }                
        return books.get(id);
    }

    /**
     * Retrieves a patron by their ID or throws an exception if not found.
     *
     * @param id The ID of the patron to retrieve.
     * @return The patron object.
     * @throws LibraryException If the patron is not found.
     */
    public Patron getPatronByID(int id) throws LibraryException {
        if (!patrons.containsKey(id)) {
            throw new LibraryException("There is no such patron with that ID.");
        }
        return patrons.get(id);
    }

    /**
     * Adds a book to the library.
     *
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
     *
     * @param patron The patron to add.
     * @return A message indicating success or error.
     */
    public String addPatron(Patron patron) {
        if (patrons.containsKey(patron.getId())) {
            return "Error: Duplicate patron ID.";
        }
        patrons.put(patron.getId(), patron);
        if (patron.getId() > maxPatronId) {
            maxPatronId = patron.getId(); // Update maxPatronId for new patrons
        }
        return "Patron added successfully.";
    }
    
    /**
     * Adds a loan to the library, storing it in the loanMap for quick retrieval.
     *
     * @param loan The loan to add.
     */
    public void addLoan(Loan loan) {
        loanMap.put(loan.getLoanId(), loan);
    }

    /**
     * Returns a copy of the list of all loans for safety.
     *
     * @return A list of all loans.
     */
    public List<Loan> getAllLoans() {
        return new ArrayList<>(loanMap.values());
    }
    
    /**
     * Finds a specific loan based on book and patron IDs.
     *
     * @param bookId   The ID of the book.
     * @param patronId The ID of the patron.
     * @return The loan if found; otherwise, null.
     */
    public Loan findLoan(int bookId, int patronId) {
        return loanMap.values().stream()
            .filter(loan -> loan.getBook().getId() == bookId && loan.getPatron().getId() == patronId && loan.isActive())
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Finds a loan by its unique ID.
     *
     * @param loanId The ID of the loan to find.
     * @return The loan if found; otherwise, null.
     */
    public Loan findLoanById(int loanId) {
        return loanMap.get(loanId);
    }

    /**
     * Links books to their respective loans using temporary loan IDs.
     * This method is used during initial data loading.
     */
    public void linkBooksToLoans() {
        for (Book book : getBooks()) {
            int tempLoanId = book.getTemporaryLoanId();
            if (tempLoanId != -1) {
                Loan loan = findLoanById(tempLoanId);
                if (loan != null) {
                    book.setLoan(loan);
                }
            }
            book.clearTemporaryLoanId(); // Ensures a clean state after linking
        }
    }
}
