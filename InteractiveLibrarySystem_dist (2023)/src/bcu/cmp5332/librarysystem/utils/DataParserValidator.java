package bcu.cmp5332.librarysystem.utils;

import bcu.cmp5332.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The DataParserValidator class provides methods for parsing and validating data.
 */
public class DataParserValidator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String SEPARATOR = "::"; // Defines the separator used in the file

    /**
     * Parses an integer value from a string.
     *
     * @param value     The string value to parse.
     * @param fieldName The name of the field being parsed (for error messages).
     * @return The parsed integer value.
     * @throws LibraryException If the value cannot be parsed as an integer.
     */
    public static int parseInteger(String value, String fieldName) throws LibraryException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new LibraryException("Invalid format for " + fieldName + ": " + value);
        }
    }

    /**
     * Parses a LocalDate from a string with a specified format.
     *
     * @param value     The string value to parse.
     * @param fieldName The name of the field being parsed (for error messages).
     * @return The parsed LocalDate.
     * @throws LibraryException If the value cannot be parsed as a valid LocalDate.
     */
    public static LocalDate parseDate(String value, String fieldName) throws LibraryException {
        try {
            return LocalDate.parse(value, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new LibraryException("Invalid format for " + fieldName + ": " + value);
        }
    }

    /**
     * Validates the fields in a line of the books.txt file.
     *
     * @param line The line from the books.txt file to validate.
     * @return True if all fields are valid, false otherwise.
     */
    public static boolean validateBookData(String line) {
        // Split the line into fields using the separator
        String[] fields = line.split(SEPARATOR);
        
        // Check if there are exactly 7 fields (bookID, title, author, publicationYear, publisher, loanId, isDeleted)
        if (fields.length != 7) {
            return false;
        }
        
        try {
            // Validate title (string) and ensure it's not empty
            String title = fields[1].trim();
            if (title.isEmpty()) {
                return false;
            }
            
            // Validate author (string) and ensure it's not empty
            String author = fields[2].trim();
            if (author.isEmpty()) {
                return false;
            }
            
            // Validate publisher (string) and ensure it's not empty
            String publisher = fields[4].trim();
            if (publisher.isEmpty()) {
                return false;
            }
            
            // Validate isDeleted (string) and ensure it's either "true" or "false"
            String isDeleted = fields[6].trim().toLowerCase();
            if (!isDeleted.equals("true") && !isDeleted.equals("false")) {
                return false;
            }
            
            // All fields are valid
            return true;
        } catch (NumberFormatException e) {
            // Parsing error for one of the integer fields
            return false;
        }
    }
    
    /**
     * Validates a line of patron data.
     *
     * @param dataLine The line of data to validate.
     * @return True if the data is valid, false otherwise.
     */
    public static boolean validatePatronData(String dataLine) {
        // Split the data line into fields using '::' as the separator
        String[] fields = dataLine.split("::");
        
        // Check if the data line has exactly 6 fields
        if (fields.length != 6) {
            return false;
        }
        
        // Check the data types for the mandatory fields
        if (!isValidInteger(fields[0]) || !isValidString(fields[1]) || !isValidPhoneNumber(fields[2]) ||
            !isValidEmail(fields[3]) || !isValidString(fields[5])) {
            return false;
        }
        
        // Check if the 4th field is empty or a valid string
        if (!fields[4].isEmpty() && !isValidString(fields[4])) {
            return false;
        }
        
        // Check if the 6th field is either "true" or "false"
        if (!fields[5].equalsIgnoreCase("true") && !fields[5].equalsIgnoreCase("false")) {
            return false;
        }
        
        // All checks passed, the data is valid
        return true;
    }

    /**
     * Validates if a string is a valid email address.
     *
     * @param email The email string to validate.
     * @return True if the email is valid, false otherwise.
     */
    private static boolean isValidEmail(String email) {
        // Regular expression for email validation
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    /**
     * Validates if a string is a valid phone number (10 digits).
     *
     * @param phoneNumber The phone number string to validate.
     * @return True if the phone number is valid, false otherwise.
     */
    private static boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression for 10-digit phone number validation
        String phoneRegex = "^[0-9]{10}$";
        return phoneNumber.matches(phoneRegex);
    }

    /**
     * Validates if a string is a valid integer.
     *
     * @param str The string to validate.
     * @return True if the string is a valid integer, false otherwise.
     */
    private static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if a string is a valid non-empty string.
     *
     * @param str The string to validate.
     * @return True if the string is valid and non-empty, false otherwise.
     */
    private static boolean isValidString(String str) {
        return !str.isEmpty();
    }

    /**
     * Validates a loan data string to ensure it has the correct format.
     *
     * @param line The loan data string to validate.
     * @return true if the loan data is valid, false otherwise.
     */
    public static boolean isValidLoanData(String line) {
        // Split the input string into parts using the '::' separator
        String[] parts = line.split("::");

        // Check if there are at least 3 parts (Id, Patron ID, Loan Data)
        if (parts.length < 3) {
            return false;
        }

        // Validate Id (must be an integer)
        if (!isValidInteger(parts[0])) {
            return false;
        }

        // Validate Patron ID (must be an integer)
        if (!isValidInteger(parts[1])) {
            return false;
        }

        // Validate Book ID (must be an integer)
        if (!isValidInteger(parts[2])) {
            return false;
        }

        // Validate Loan Data (cannot be empty)
        if (parts[3].isEmpty()) {
            return false;
        }

        // Validate Due Date (optional, but must be a valid date if present)
        if (parts.length > 4 && !parts[4].isEmpty()) {
            try {
                LocalDate.parse(parts[4]);
            } catch (DateTimeParseException e) {
                return false;
            }
        }

        // Validate Loan Status (optional, but cannot be empty if present)
        if (parts.length > 5 && parts[5].isEmpty()) {
            return false;
        }

        return true;
    }
}
