package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.gui.MainWindow;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

/**
 * The LoadGUI class creates a new main window for the GUI.
 * 
 */
public class LoadGUI implements Command {
	
	/**
     * Executes the command to load the GUI.
     * @param library           The library with which the GUI will use.
     * @param currentDate       The current date (can be removed if not required).
     * @param messageDisplayer  The mechanism for displaying messages to the user.
     * @throws LibraryException if there is an error during the process.
     */

    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        new MainWindow(library);
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
}
 