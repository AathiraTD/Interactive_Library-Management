package bcu.cmp5332.librarysystem.model;

import java.time.LocalDate;

public class Loan {
    private static int loanCounter = 0; // Static counter to generate unique loan IDs

    private int loanId; // Unique identifier for each loan
    private Book book; // The book involved in the loan
    private Patron patron; // The patron who has borrowed the book
    private LocalDate dueDate; // The due date for returning the book
    private LocalDate returnDate; // The date on which the book was returned
    private String status; // The status of the loan (e.g., "active", "returned")

    // Constructor to initialize a Loan object
    public Loan(Book book, Patron patron, LocalDate dueDate) {
        this.loanId = ++loanCounter; // Assign a unique loan ID
        this.book = book;
        this.patron = patron;
        this.dueDate = dueDate;
        this.returnDate = null; // Initialize with no return date
        this.status = "active"; // Default status is active
    }

    // Getters and setters for loan fields
    
    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int id) {
        this.loanId = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Check if the loan is currently active
    public boolean isActive() {
        return "active".equals(status);
    }

    // Additional methods can be added as needed
}
