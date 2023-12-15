package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.time.LocalDate;
import java.util.List;

public class ListPatrons implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = library.getPatrons(); // Get the list of patrons
        if (patrons.isEmpty()) {
            System.out.println("No patrons found in the library.");
        } else {
            for (Patron patron : patrons) {
                // Assuming Patron class has a meaningful toString() method
                System.out.println(patron.getDetailsShort());
            }
            System.out.println(patrons.size() + " patron(s)");
        }
    }
}
