package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;

public class Book {
    
    private int id; // Unique identifier for the book
    private String title; // Title of the book
    private String author; // Author of the book
    private String publicationYear; // Year of publication
    private int temporaryLoanId; // Temporary field to store loan ID during initial data loading

    private Loan loan; // Reference to the Loan object if the book is loaned
    
    // Enum for book status
    public enum Status {
        AVAILABLE, LOANED_OUT
    }

    // Constructor to initialize the book object
    public Book(int id, String title, String author, String publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.temporaryLoanId = -1; // Initialize with -1 indicating no loan
    }

    // Getters and setters for book properties
    public int getId() { return id; } 
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPublicationYear() { return publicationYear; }
    public void setPublicationYear(String publicationYear) { this.publicationYear = publicationYear; }
    
    // Short description of the book
    public String getDetailsShort() {
        return "Book #" + id + " - " + title;
    }

    // Long description of the book (to be implemented)
    public String getDetailsLong() {
        // TODO: implementation here
        return null;
    }
    
    // Check if the book is currently on loan
    public boolean isOnLoan() {
        return loan != null && loan.isActive();
    }
    
    // Get the current status of the book
    public Status getStatus() {
        return isOnLoan() ? Status.LOANED_OUT : Status.AVAILABLE;
    }

    // Get the due date of the book (to be implemented)
    public LocalDate getDueDate() {
        // TODO: implementation here
        return null;
    }
    
    // Set the due date of the book (to be implemented)
    public void setDueDate(LocalDate dueDate) throws LibraryException {
        // TODO: implementation here
    }

    // Get and set methods for the loan object
    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }

    // Method to return the book to the library
    public void returnToLibrary() {
        loan = null;
    }

    // Temporary loan ID methods for initial data loading
    public void setTemporaryLoanId(int loanId) { this.temporaryLoanId = loanId; }
    public int getTemporaryLoanId() { return temporaryLoanId; }
    public void clearTemporaryLoanId() { this.temporaryLoanId = -1; }
}
