package bcu.cmp5332.librarysystem.utils;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Patron;

/**
 * Centralized validator class for the library system.
 */
public class Validator {
	
	// Class-level constant for the maximum number of books a patron can borrow
    private static final int MAX_BORROW_LIMIT = 5;

    /**
     * Validates book details.
     * @param title            The title of the book.
     * @param author           The author of the book.
     * @param publicationYear  The publication year of the book.
     * @param publisher        The publisher of the book.
     * @return A validation message. If all inputs are valid, returns null.
     */
    public static String validateBookDetails(String title, String author, String publicationYear, String publisher) {
        if (title == null || title.isBlank()) {
            return "Book title cannot be blank.";
        }
        if (author == null || author.isBlank()) {
            return "Author's name cannot be blank.";
        }
        if (publicationYear == null || publicationYear.isBlank()) {
            return "Publication year cannot be blank.";
        } else if (!publicationYear.matches("\\d{1,4}")) {
            return "Publication year must be a digit and can have up to 4 digits.";
        }
        if (publisher == null || publisher.isBlank()) {
            return "Publisher's name cannot be blank.";
        }
        return null;
    }

    /**
     * Validates patron details.
     * @param name   The name of the patron.
     * @param email  The email of the patron.
     * @param phone  The phone number of the patron.
     * @return A validation message. If all inputs are valid, returns null.
     */
    public static String validatePatronDetails(String name, String phone, String email) {
        if (name == null || name.isBlank()) {
            return "Patron name cannot be blank.";
        }
        if (email == null || email.isBlank()) {
            return "Email cannot be blank.";
        } else if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            return "Invalid email format. Email should be like 'example@domain.com'.";
        }

        if (phone == null || phone.isBlank()) {
            return "Phone number cannot be blank.";
        } else if (!phone.matches("\\d{10}")) {
            return "Phone number must be exactly 10 digits.";
        }
        return null;
    }

    
    /**
     * Validates the eligibility of the book and patron for borrowing.
     * Checks whether the book is available and the patron hasn't reached the borrowing limit.
     *
     * @param book   The book to be borrowed.
     * @param patron The patron borrowing the book.
     * @throws LibraryException If the book or patron is not eligible for borrowing.
     */
    public static String validateBookAndPatronForBorrowing(Book book, Patron patron) throws LibraryException {
        // Check the eligibility of the book for loan
        if (book == null || book.isDeleted()) {
            return ("Invalid or hidden book cannot be borrowed.");
        }
        if (book.isOnLoan()) {
        	return("Book is already loaned out.");
        }

        // Check the eligibility of the patron for borrowing
        if (patron == null || patron.isDeleted()) {
        	return("Invalid or hidden patron cannot borrow books.");
        }
        if (patron.getBorrowedBooks().size() >= MAX_BORROW_LIMIT) {
        	return("Patron has already borrowed the maximum allowed number of books.");
        }
		return null;
    }
    
    /**
     * Validates if a book is eligible to be hidden in the library.
     * 
     * @param book The book to be validated for hiding.
     * @return A string message indicating why the book cannot be hidden. 
     *         Returns null if the book is eligible for hiding.
     */
    public static String validateBookForHiding(Book book) {
        // Check if the book object is null (book not found in the library)
        if (book == null) {
            return "Book not found."; // Return message if book is not found
        }

        // Check if the book is already marked as deleted (hidden)
        if (book.isDeleted()) {
            return "Book is already hidden."; // Return message if the book is already hidden
        }
        // Check if the book is currently loaned out
        if (book.getStatus() == Book.Status.LOANED_OUT) {
            return "Book is currently on loan."; // Return message if the book is currently on loan
        }

        // Return null if the book passes all the checks and is eligible to be hidden
        return null;
    }
    
    /**
     * Validates if a patron is eligible to be hidden.
     * 
     * @param patron The patron to be validated.
     * @return A string message indicating why the patron cannot be hidden.
     *         Returns null if the patron is eligible for hiding.
     */
    public static String validatePatronForHiding(Patron patron) {
        if (patron == null) {
            return "Patron not found.";
        }
        if (patron.isDeleted()) {
            return "Patron is already hidden.";
        }
        if (!patron.getBorrowedBooks().isEmpty()) {
            return "Patron currently has books on loan.";
        }
        return null;
    }

}
