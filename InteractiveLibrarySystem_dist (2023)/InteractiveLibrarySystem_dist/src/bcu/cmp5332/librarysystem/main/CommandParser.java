package bcu.cmp5332.librarysystem.main;

import bcu.cmp5332.librarysystem.commands.*;
import bcu.cmp5332.librarysystem.utils.CliMessageDisplayer;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * The CommandParser class is responsible for parsing user input into specific Command objects.
 * It interprets the user's command line input and creates the corresponding Command object
 * to be executed by the application.
 */
public class CommandParser {

    /**
     * Parses a given command line and returns the corresponding Command object.
     *
     * @param line The command line input.
     * @return Command object that represents the user's input.
     * @throws IOException If an input/output operation fails.
     * @throws LibraryException If the command is invalid or an error occurs.
     */
    public static Command parse(String line) throws IOException, LibraryException {
        try {
            // Splitting the input line into parts for processing
            String[] parts = line.split(" ", 3);
            String cmd = parts[0];
            MessageDisplayer cliDisplayer = new CliMessageDisplayer();
            
            // Switch statement to handle different command types
            switch (cmd) {
                case "addbook":
                    return parseAddBook();
                case "addpatron":
                    return parseAddPatron();
                case "loadgui":
                    return new LoadGUI();
                case "listbooks":
                    return new ListBooks(null,cliDisplayer);
                case "listpatrons":
                    return new ListPatrons(null,cliDisplayer);
                case "listloans":
                    return new ListLoans(null,cliDisplayer);
                case "help":
                    return new Help();
                case "showbook":
                    return parseShowBook(parts);
                case "showpatron":
                    return parseShowPatron(parts);
                case "borrow":
                    return parseBorrowBook(parts);
                case "renew":
                    return parseRenewBook(parts);
                case "return":
                    return parseReturnBook(parts);
                case "hidebooks":
                    return parseHideBooks(parts);
                case "hidepatrons":
                    return parseHidePatrons(parts);
                default:
                    throw new LibraryException("Invalid command.");
            }
        } catch (NumberFormatException ex) {
            // Handling number format exceptions for command parsing
            throw new LibraryException("Invalid numeric value in command.");
        }
    }

    // Parses the 'addbook' command
    private static Command parseAddBook() throws IOException, LibraryException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Title: ");
        String title = br.readLine();
        System.out.print("Author: ");
        String author = br.readLine();
        System.out.print("Publication Year: ");
        String publicationYear = br.readLine();
        System.out.print("Publisher: ");
        String publisher = br.readLine();
        return new AddBook(title, author, publicationYear, publisher);
    }

    // Parses the 'addpatron' command
    private static Command parseAddPatron() throws IOException, LibraryException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter patron's name: ");
        String name = br.readLine();
        System.out.print("Enter patron's phone number: ");
        String phoneNumber = br.readLine();
        System.out.print("Enter patron's email: ");
        String email = br.readLine();
        System.out.print("Enter book IDs (comma separated) for books borrowed: ");
        String bookIdsLine = br.readLine();
        List<Integer> bookIds = parseBookIds(bookIdsLine);
        return new AddPatron(name, phoneNumber, email, bookIds);
    }

    // Parses the 'showbook' command
    private static Command parseShowBook(String[] parts) throws LibraryException {
        if (parts.length < 2) {
            throw new LibraryException("No book ID provided for showbook command.");
        }
        int bookId = Integer.parseInt(parts[1]);
        return new ShowBook(bookId);
    }

    // Parses the 'showpatron' command
    private static Command parseShowPatron(String[] parts) throws LibraryException {
        if (parts.length < 2) {
            throw new LibraryException("No patron ID provided for showpatron command.");
        }
        int patronId = Integer.parseInt(parts[1]);
        return new ShowPatron(patronId);
    }

    // Parses the 'borrow' command
    private static Command parseBorrowBook(String[] parts) throws LibraryException {
        if (parts.length < 3) {
            throw new LibraryException("Insufficient arguments for borrow command.");
        }
        int patronId = Integer.parseInt(parts[1]);
        int bookId = Integer.parseInt(parts[2]);
        return new BorrowBook(patronId, bookId);
    }

    // Parses the 'return' command
    private static Command parseRenewBook(String[] parts) throws LibraryException {
        if (parts.length < 3) {
            throw new LibraryException("Insufficient arguments for renew command.");
        }
        int patronId = Integer.parseInt(parts[1]);
        int bookId = Integer.parseInt(parts[2]);
        return new RenewBook(patronId, bookId);
    }

 // Parses the 'renew' command
    private static Command parseReturnBook(String[] parts) throws LibraryException {
        if (parts.length < 3) {
            throw new LibraryException("Insufficient arguments for return command.");
        }
        int patronId = Integer.parseInt(parts[1]);
        int bookId = Integer.parseInt(parts[2]);
        return new ReturnBook(patronId, bookId);
    }
    // Helper method to parse book IDs from a comma-separated string
    private static List<Integer> parseBookIds(String line) {
        List<Integer> bookIds = new ArrayList<>();
        for (String part : line.split(",")) {
            try {
                bookIds.add(Integer.parseInt(part.trim()));
            } catch (NumberFormatException e) {
                // Log the error or notify the user about the invalid book ID
            }
        }
        return bookIds;
    }
    
 // Parses the 'hidebooks' command
    private static Command parseHideBooks(String[] parts) throws LibraryException {
        if (parts.length < 2) {
            throw new LibraryException("No book IDs provided for hidebooks command.");
        }
        String[] bookIdsStr = parts[1].split(",");
        List<Integer> bookIds = new ArrayList<>();
        for (String idStr : bookIdsStr) {
            try {
                bookIds.add(Integer.parseInt(idStr.trim()));
            } catch (NumberFormatException e) {
                throw new LibraryException("Invalid book ID: " + idStr);
            }
        }
        return new HideBooks(bookIds);
    }
    
 // Parses the 'hidepatrons' command
    private static Command parseHidePatrons(String[] parts) throws LibraryException {
        if (parts.length < 2) {
            throw new LibraryException("No patron IDs provided for hidepatrons command.");
        }
        String[] patronIdsStr = parts[1].split(",");
        List<Integer> patronIds = new ArrayList<>();
        for (String idStr : patronIdsStr) {
            try {
                patronIds.add(Integer.parseInt(idStr.trim()));
            } catch (NumberFormatException e) {
                throw new LibraryException("Invalid patron ID: " + idStr);
            }
        }
        return new HidePatrons(patronIds);
    }
}