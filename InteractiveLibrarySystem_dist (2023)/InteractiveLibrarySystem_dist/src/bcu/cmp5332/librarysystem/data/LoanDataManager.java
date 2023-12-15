package bcu.cmp5332.librarysystem.data;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Loan;
import bcu.cmp5332.librarysystem.model.Patron;

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
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("::");

                int loanId = Integer.parseInt(parts[0]);
                int patronId = Integer.parseInt(parts[1]);
                int bookId = Integer.parseInt(parts[2]);
                LocalDate dueDate = LocalDate.parse(parts[3], FORMATTER);

                LocalDate returnDate = parts.length > 4 && !parts[4].isEmpty() ? LocalDate.parse(parts[4], FORMATTER) : null;
                String status = parts.length > 5 ? parts[5] : "active";

                Patron patron = library.getPatronByID(patronId);
                Book book = library.getBookByID(bookId);

                if (patron != null && book != null) {
                    Loan loan = new Loan(book, patron, dueDate);
                    loan.setLoanId(loanId);
                    loan.setReturnDate(returnDate);
                    loan.setStatus(status);
                    library.addLoan(loan);
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
