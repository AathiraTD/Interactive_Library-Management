package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;

public class Library {
    
    private final int loanPeriod = 7; // Standard loan period for all books
    private final Map<Integer, Patron> patrons = new TreeMap<>(); // Maps patron IDs to their objects
    private final Map<Integer, Book> books = new TreeMap<>(); // Maps book IDs to their objects
    private int maxPatronId = 0; // Tracks the highest patron ID for assigning new IDs
    private final Map<Integer, Loan> loanMap = new HashMap<>(); // Map for efficient loan retrieval

    // Returns the standard loan period for books
    public int getLoanPeriod() {
        return loanPeriod;
    }

    // Returns an unmodifiable list of all books in the library
    public List<Book> getBooks() {
        return Collections.unmodifiableList(new ArrayList<>(books.values()));
    }
    
    // Returns an unmodifiable list of all patrons in the library
    public List<Patron> getPatrons() {
        return Collections.unmodifiableList(new ArrayList<>(patrons.values()));
    }

    // Retrieves a book by its ID or throws an exception if not found
    public Book getBookByID(int id) throws LibraryException {
        if (!books.containsKey(id)) {
            throw new LibraryException("There is no such book with that ID.");
        }                
        return books.get(id);
    }

    // Retrieves a patron by their ID or throws an exception if not found
    public Patron getPatronByID(int id) throws LibraryException {
        if (!patrons.containsKey(id)) {
            throw new LibraryException("There is no such patron with that ID.");
        }
        return patrons.get(id);
    }

    // Adds a book to the library, returns a message indicating success or error
    public String addBook(Book book) {
        if (books.containsKey(book.getId())) {
            return "Error: Duplicate book ID.";
        }
        books.put(book.getId(), book);
        return "Book added successfully.";
    }

    // Adds a patron to the library, returns a message indicating success or error
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
    
    // Adds a loan to the library, storing it in the loanMap for quick retrieval
    public void addLoan(Loan loan) {
        loanMap.put(loan.getLoanId(), loan);
    }

    // Returns a copy of the list of all loans for safety
    public List<Loan> getAllLoans() {
        return new ArrayList<>(loanMap.values());
    }
    
    // Finds a specific loan based on book and patron IDs, returns null if not found
    public Loan findLoan(int bookId, int patronId) {
        return loanMap.values().stream()
            .filter(loan -> loan.getBook().getId() == bookId && loan.getPatron().getId() == patronId && loan.isActive())
            .findFirst()
            .orElse(null);
    }
    
    // Finds a loan by its unique ID, returns null if no matching loan is found
    public Loan findLoanById(int loanId) {
        return loanMap.get(loanId);
    }

    // Links books to their respective loans using temporary loan IDs
    public void linkBooksToLoans() {
        for (Book book : getBooks()) {
            int tempLoanId = book.getTemporaryLoanId();
            if (tempLoanId != -1) {
                Loan loan = findLoanById(tempLoanId);
                if (loan != null) {
                    book.setLoan(loan);
                }
            }
            book.clearTemporaryLoanId(); // Ensures clean state after linking
        }
    }
}
