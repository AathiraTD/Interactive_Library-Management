package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.gui.MainWindow;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.time.LocalDate;

public class LoadGUI implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        new MainWindow(library);
        try {
			LibraryData.store(library);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
    
}
 