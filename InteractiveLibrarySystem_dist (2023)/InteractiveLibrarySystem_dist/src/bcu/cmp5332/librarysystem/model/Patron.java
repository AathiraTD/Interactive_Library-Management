package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;

/**
 * The Patron class represents a patron of the library.
 */
public class Patron {

    private int id; // Unique identifier for the patron
    private String name; // Name of the patron
    private String phone; // Phone number of the patron
    private String email; // Email of the patron
    private List<Book> borrowedbooks; // List of books borrowed by the patron
    private boolean isDeleted = false; // Flag to indicate if the patron is hidden

    /**
     * Constructor initializes the patron with ID, name, and phone number.
     *
     * @param id          The unique identifier for the patron.
     * @param name        The name of the patron.
     * @param phoneNumber The phone number of the patron.
     * @param email       The email of the patron.
     * @param isDeleted   Flag to indicate if the patron is hidden.
     */
    public Patron(int id, String name, String phoneNumber, String email, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.phone = phoneNumber;
        this.email = email;
        this.borrowedbooks = new ArrayList<>();
        this.isDeleted = isDeleted;
    }

    /**
     * Constructor initializes the patron with ID, name, phone number, and email.
     * The isDeleted flag is set to false by default.
     *
     * @param id          The unique identifier for the patron.
     * @param name        The name of the patron.
     * @param phoneNumber The phone number of the patron.
     * @param email       The email of the patron.
     */
    public Patron(int id, String name, String phoneNumber, String email) {
        this(id, name, phoneNumber, email, false); // Call the other constructor with isDeleted set to false
    }

    // Getters and setters for patron's properties

    /**
     * Get the ID of the patron.
     *
     * @return The patron's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the ID of the patron.
     *
     * @param id The patron's ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the name of the patron.
     *
     * @return The patron's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the patron.
     *
     * @param name The patron's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the phone number of the patron.
     *
     * @return The patron's phone number.
     */
    public String getPhoneNumber() {
        return phone;
    }

    /**
     * Set the phone number of the patron.
     *
     * @param phoneNumber The patron's phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phone = phoneNumber;
    }

    /**
     * Get the email of the patron.
     *
     * @return The patron's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the email of the patron.
     *
     * @param email The patron's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method to hide the patron.
     */
    public void hidePatron() {
        this.isDeleted = true;
    }

    /**
     * Check if the patron is hidden.
     *
     * @return True if the patron is hidden, false otherwise.
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Get a short description of the patron.
     *
     * @return A short description of the patron.
     */
    public String getDetailsShort() {
        return "Patron #" + id + " - " + name;
    }

    /**
     * Get a list of books currently borrowed by the patron.
     *
     * @return A list of borrowed books.
     */
    public List<Book> getBorrowedBooks() {
        return new ArrayList<>(borrowedbooks); // Return a copy for encapsulation
    }

    /**
     * Add a book to the patron's list of borrowed books.
     *
     * @param book The book to be borrowed.
     * @throws LibraryException If the book is null.
     */
    public void borrowBook(Book book) throws LibraryException {
        if (book == null) {
            throw new LibraryException("Cannot borrow a null book.");
        } else {
        	borrowedbooks.add(book);
        }
    }

    // Method to renew the borrowing period of a book (implementation pending)

    /**
     * Renew the borrowing period of a book (not implemented yet).
     *
     * @param book The book to renew.
     * @throws LibraryException If the renewal process encounters an error.
     */
    public void renewBook(Book book) throws LibraryException {
        // TODO: implementation here
    }

    /**
     * Remove a book from the patron's list of borrowed books.
     *
     * @param book The book to be returned.
     * @throws LibraryException If the book is null or not borrowed by the patron.
     */
    public void returnBook(Book book) throws LibraryException {
        if (book == null || !borrowedbooks.contains(book)) {
            throw new LibraryException("Cannot return a book that was not borrowed.");
        }
        borrowedbooks.remove(book);
    }
}
