package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.time.LocalDate;

/**
 * The Book class represents a book in the library.
 */
public class Book {

    private int id; // Unique identifier for the book
    private String title; // Title of the book
    private String author; // Author of the book
    private String publicationYear; // Year of publication
    private String publisher;
    private int temporaryLoanId; // Temporary field to store loan ID during initial data loading
    private boolean isDeleted = false; // Default value is false

    private Loan loan; // Reference to the Loan object if the book is loaned

    // Enum for book status
    public enum Status {
        AVAILABLE, LOANED_OUT
    }

    /**
     * Constructor to initialize the book object.
     *
     * @param id              The unique identifier for the book.
     * @param title           The title of the book.
     * @param author          The author of the book.
     * @param publicationYear The year of publication.
     * @param publisher       The publisher of the book.
     * @param isDeleted       Flag to indicate if the book is hidden.
     */
    public Book(int id, String title, String author, String publicationYear, String publisher, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.temporaryLoanId = -1; // Initialize with -1 indicating no loan
        this.isDeleted = isDeleted;
    }

    /**
     * Constructor without isDeleted (original constructor).
     *
     * @param id              The unique identifier for the book.
     * @param title           The title of the book.
     * @param author          The author of the book.
     * @param publicationYear The year of publication.
     * @param publisher       The publisher of the book.
     */
    public Book(int id, String title, String author, String publicationYear, String publisher) {
        this(id, title, author, publicationYear, publisher, false); // Call the other constructor with isDeleted set to false
    }

    // Getters and setters for book properties

    /**
     * Gets the unique identifier for the book.
     *
     * @return The book's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the book.
     *
     * @param id The book's ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title of the book.
     *
     * @return The book's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     *
     * @param title The book's title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     *
     * @return The book's author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author The book's author to set.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the year of publication of the book.
     *
     * @return The book's publication year.
     */
    public String getPublicationYear() {
        return publicationYear;
    }

    /**
     * Sets the year of publication of the book.
     *
     * @param publicationYear The book's publication year to set.
     */
    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
     * Gets the publisher of the book.
     *
     * @return The book's publisher.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher of the book.
     *
     * @param publisher The book's publisher to set.
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Hides the book.
     * This sets the flag to indicate that the book is hidden.
     */
    public void hideBook() {
        this.isDeleted = true;
    }

    /**
     * Checks if the book is hidden.
     *
     * @return True if the book is hidden; false otherwise.
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Gets a short description of the book.
     *
     * @return A short description of the book.
     */
    public String getDetailsShort() {
        return "Book #" + id + " - " + title;
    }

    /**
     * Gets a long description of the book.
     * Note: This method is to be implemented.
     *
     * @return A long description of the book.
     */
    public String getDetailsLong() {
        // TODO: implementation here
        return null;
    }

    /**
     * Checks if the book is currently on loan.
     *
     * @return True if the book is on loan; false otherwise.
     */
    public boolean isOnLoan() {
        return loan != null && loan.isActive();
    }

    /**
     * Gets the current status of the book.
     *
     * @return The status of the book (AVAILABLE or LOANED_OUT).
     */
    public Status getStatus() {
        return isOnLoan() ? Status.LOANED_OUT : Status.AVAILABLE;
    }

    /**
     * Gets the due date of the book.
     * Note: This method is to be implemented.
     *
     * @return The due date of the book.
     */
    public LocalDate getDueDate() {
        // TODO: implementation here
        return null;
    }

    /**
     * Sets the due date of the book.
     * Note: This method is to be implemented.
     *
     * @param dueDate The due date to set.
     * @throws LibraryException If a library-specific error occurs.
     */
    public void setDueDate(LocalDate dueDate) throws LibraryException {
        // TODO: implementation here
    }

    /**
     * Gets the loan associated with the book.
     *
     * @return The loan associated with the book.
     */
    public Loan getLoan() {
        return loan;
    }

    /**
     * Sets the loan associated with the book.
     *
     * @param loan The loan to set.
     */
    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    /**
     * Returns the book to the library.
     * This clears the associated loan.
     */
    public void returnToLibrary() {
        loan = null;
    }

    /**
     * Sets the temporary loan ID during initial data loading.
     *
     * @param loanId The temporary loan ID to set.
     */
    public void setTemporaryLoanId(int loanId) {
        this.temporaryLoanId = loanId;
    }

    /**
     * Gets the temporary loan ID.
     *
     * @return The temporary loan ID.
     */
    public int getTemporaryLoanId() {
        return temporaryLoanId;
    }

    /**
     * Clears the temporary loan ID.
     */
    public void clearTemporaryLoanId() {
        this.temporaryLoanId = -1;
    }
}
