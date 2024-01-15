package bcu.cmp5332.librarysystem.main;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.commands.Command;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.*;

import java.io.*;
import java.time.LocalDate;

/**
 * The Main class of the Library System application.
 * This class initializes the system, handles user input,
 * and manages the command execution process.
 */
public class Main {

    /**
     * The main method serves as the entry point for the Library System application.
     * It loads the library data, processes user commands, and maintains the state
     * of the library throughout the application lifecycle.
     * 
     * @param args Command line arguments. Not used in this application.
     * @throws IOException If an input/output error occurs.
     * @throws LibraryException If a library-specific error occurs.
     */
    public static void main(String[] args) throws IOException, LibraryException {
        
        // Load library data from files.
        Library library = LibraryData.load();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Display initial instructions to the user.
        System.out.println("Library system");
        System.out.println("Enter 'help' to see a list of available commands.");
        
        // Command processing loop.
        while (true) {
            System.out.print("> ");
            String line = br.readLine();

            // Check for 'exit' command to terminate the application.
            if (line.equals("exit")) {
                break; 
            }

            try {
                // Parse the input line into a command and execute it.
                Command command = CommandParser.parse(line);
                MessageDisplayer cliDisplayer = new CliMessageDisplayer();
                command.execute(library, LocalDate.now(), cliDisplayer);

                // Persist changes to the library system after every successful command.
                LibraryData.store(library); 
            } catch (LibraryException | IOException ex) {
                // Handle exceptions, display error message and rollback changes.
                System.out.println("Error: " + ex.getMessage() + ". Rolling back changes.");
                library = LibraryData.load(); // Reload data to rollback changes.
            }
        }

        // Store any final changes before exiting the application.
        LibraryData.store(library);
        System.exit(0);
    }
}
