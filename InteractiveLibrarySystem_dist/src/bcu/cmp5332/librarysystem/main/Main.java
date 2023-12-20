package bcu.cmp5332.librarysystem.main;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.model.Library;

import java.io.*;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) throws IOException, LibraryException {
        
        // Load library data from files
        Library library = LibraryData.load();

        // Link books to their corresponding loans
        library.linkBooksToLoans();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Library system");
        System.out.println("Enter 'help' to see a list of available commands.");
        
        // Command processing loop
        while (true) {
            System.out.print("> ");
            String line = br.readLine();
            if (line.equals("exit")) {
                break; // Exit loop if 'exit' command is entered
            }

            try {
                // Parse and execute the command
                Command command = CommandParser.parse(line);
                command.execute(library, LocalDate.now());

                // Persist changes to the library system after every command
                LibraryData.store(library); 
            } catch (LibraryException | IOException ex) {
                // Handle exceptions and rollback changes
                System.out.println("Error: " + ex.getMessage() + ". Rolling back changes.");
                library = LibraryData.load(); // Reload data to rollback changes
                library.linkBooksToLoans();
            }
        }

        // Store any final changes before exiting
        LibraryData.store(library);
        System.exit(0);
    }
}
