package bcu.cmp5332.librarysystem.main;

/**
 * The LibraryException class is a custom exception used within the Library System.
 * It extends the standard Java {@link Exception} class. This exception is thrown
 * to indicate specific conditions or errors that occur within the library system's operations,
 * such as invalid input or operational errors specific to the library context.
 */
public class LibraryException extends Exception {

    /**
     * Unique serial version identifier for this custom exception class.
     * This identifier is used for serialization and deserialization of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new LibraryException with the specified detail message.
     * The detail message is saved for later retrieval by the {@link Throwable#getMessage()} method.
     * 
     * @param message the detail message, which provides more information about the exception.
     */
    public LibraryException(String message) {
        super(message);
    }
}
