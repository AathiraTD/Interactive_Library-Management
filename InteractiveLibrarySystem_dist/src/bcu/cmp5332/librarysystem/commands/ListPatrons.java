package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.model.Patron;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ListPatrons implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = library.getPatrons().stream()  // Convert the list of patrons to a stream
                					  .filter(patron -> !patron.isDeleted()) // Filter out deleted books
                					  .collect(Collectors.toList()); // Collect the filtered stream back into a list
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
