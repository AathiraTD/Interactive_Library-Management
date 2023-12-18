package bcu.cmp5332.librarysystem.model;

import bcu.cmp5332.librarysystem.main.LibraryException;
import java.util.*;

public class Patron {
    
    private int id; // Unique identifier for the patron
    private String name; // Name of the patron
    private String phone; // Phone number of the patron
    private List<Book> books; // List of books borrowed by the patron
    private boolean isDeleted = false; // Flag to indicate if the patron is hidden

    
    // Constructor initializes the patron with ID, name, and phone number
    public Patron(int id, String name, String phoneNumber, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.phone = phoneNumber;
        this.books = new ArrayList<>();
        this.isDeleted = isDeleted;
    }
    
    public Patron(int id, String name, String phoneNumber) {
    	this(id, name, phoneNumber, false); // Call the other constructor with isDeleted set to false
    }
    
    // Getters and setters for patron's properties
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone = phoneNumber;
    }
    
    // Method to hide the patron
    public void hidePatron() {
        this.isDeleted = true;
    }

    // Method to check if the patron is hidden
    public boolean isDeleted() {
        return isDeleted;
    }

    
    // Returns a short description of the patron
    public String getDetailsShort() {
        return "Patron #" + id + " - " + name;
    }
    
    // Returns a list of books currently borrowed by the patron
    public List<Book> getBorrowedBooks() {
        return new ArrayList<>(books); // Return a copy for encapsulation
    }
    
    // Adds a book to the patron's list of borrowed books
    public void borrowBook(Book book) throws LibraryException {
        if (book == null) {
            throw new LibraryException("Cannot borrow a null book.");
        }
        else {
        	books.add(book);
        }
    }

    // Method to renew the borrowing period of a book (implementation pending)
    public void renewBook(Book book) throws LibraryException {
        // TODO: implementation here
    }

    // Removes a book from the patron's list of borrowed books
    public void returnBook(Book book) throws LibraryException {
        if (book == null || !books.contains(book)) {
            throw new LibraryException("Cannot return a book that was not borrowed.");
        }
        books.remove(book);
    }
}
