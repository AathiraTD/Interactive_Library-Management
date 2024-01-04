package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.utils.DataParserValidator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Handles loading and storing loan data from/to a text file.
 */
public class LoanDataManager implements DataManager {

    private static final String RESOURCE = "./resources/data/loans.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Loads loan data from a file and adds loans to the library.
     *
     * @param library The library to which the loans will be added.
     * @throws IOException If an IO error occurs.
     * @throws LibraryException If a parsing or data consistency error occurs.
     */
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        try (Scanner scanner = new Scanner(new File(RESOURCE))) {
            int lineNumber = 0; // Track line number for error reporting
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();

                if (line.isEmpty()) {
                    // Skip empty lines
                    continue;
                }

                if (!DataParserValidator.isValidLoanData(line)) {
                    throw new LibraryException("Invalid loan data format in loans.txt at line " + lineNumber);
                }

                String[] parts = line.split("::");
                int loanId = DataParserValidator.parseInteger(parts[0], "LoanId");
                int patronId = DataParserValidator.parseInteger(parts[1],"PatronId");
                int bookId = DataParserValidator.parseInteger(parts[2],"BookId");
                LocalDate dueDate = LocalDate.parse(parts[3], FORMATTER);

                LocalDate returnDate = parts.length > 4 && !parts[4].isEmpty() ? LocalDate.parse(parts[4], FORMATTER) : null;
                String status = parts.length > 5 && !parts[5].isEmpty() ? parts[5] : "active";

                Patron patron = library.getPatronById(patronId);
                Book book = library.getBookById(bookId);

                if (patron != null && book != null) {
                    Loan loan = new Loan(book, patron, dueDate);
                    loan.setLoanId(loanId);
                    loan.setReturnDate(returnDate);
                    loan.setStatus(status);
                    library.addLoan(loan,book);
                }
            }
        }
    }

    /**
     * Stores loan data to a file from the library.
     *
     * @param library The library whose loan data will be stored.
     * @throws IOException If an IO error occurs.
     */
    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Loan loan : library.getAllLoans()) {
                int loanId = loan.getLoanId();
                int patronId = loan.getPatron().getId();
                int bookId = loan.getBook().getId();
                String dueDate = loan.getDueDate().format(FORMATTER);
                String returnDate = loan.getReturnDate() != null ? loan.getReturnDate().format(FORMATTER) : "";
                String status = loan.getStatus();
                writer.println(loanId + "::" + patronId + "::" + bookId + "::" + dueDate + "::" + returnDate + "::" + status);
            }
        } catch (IOException ex) {
            throw new IOException("Failed to write to " + RESOURCE, ex);
        }
    }
}
