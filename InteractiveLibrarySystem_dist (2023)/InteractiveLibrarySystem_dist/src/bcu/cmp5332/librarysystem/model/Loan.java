package bcu.cmp5332.librarysystem.model;

import java.time.LocalDate;

/**
 * The Loan class represents a loan of a book to a patron in the library.
 */
public class Loan {
    private static int loanCounter = 0; // Static counter to generate unique loan IDs

    private int loanId; // Unique identifier for each loan
    private Book book; // The book involved in the loan
    private Patron patron; // The patron who has borrowed the book
    private LocalDate dueDate; // The due date for returning the book
    private LocalDate returnDate; // The date on which the book was returned
    private String status; // The status of the loan (e.g., "active", "returned")

    /**
     * Constructor to initialize a Loan object.
     *
     * @param book     The book involved in the loan.
     * @param patron   The patron who has borrowed the book.
     * @param dueDate  The due date for returning the book.
     */
    public Loan(Book book, Patron patron, LocalDate dueDate) {
        this.loanId = ++loanCounter; // Assign a unique loan ID
        this.book = book;
        this.patron = patron;
        this.dueDate = dueDate;
        this.returnDate = null; // Initialize with no return date
        this.status = "active"; // Default status is active
    }

    /**
     * Get the unique identifier of the loan.
     *
     * @return The loan ID.
     */
    public int getLoanId() {
        return loanId;
    }

    /**
     * Set the loan ID.
     *
     * @param id The loan ID to set.
     */
    public void setLoanId(int id) {
        this.loanId = id;
    }

    /**
     * Get the book involved in the loan.
     *
     * @return The Book object associated with the loan.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Set the book involved in the loan.
     *
     * @param book The Book object to set.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Get the patron who borrowed the book.
     *
     * @return The Patron object representing the borrower.
     */
    public Patron getPatron() {
        return patron;
    }

    /**
     * Set the patron who borrowed the book.
     *
     * @param patron The Patron object to set as the borrower.
     */
    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    /**
     * Get the due date for returning the book.
     *
     * @return The due date as a LocalDate.
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Set the due date for returning the book.
     *
     * @param dueDate The due date to set as a LocalDate.
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Get the date on which the book was returned.
     *
     * @return The return date as a LocalDate, or null if not returned.
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Set the date on which the book was returned.
     *
     * @param returnDate The return date to set as a LocalDate.
     */
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Get the status of the loan.
     *
     * @return The loan status as a String (e.g., "active", "returned").
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set the status of the loan.
     *
     * @param status The loan status to set as a String.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Check if the loan is currently active.
     *
     * @return true if the loan is active; otherwise, false.
     */
    public boolean isActive() {
        return "active".equals(status);
    }

    // Additional methods can be added as needed
}
