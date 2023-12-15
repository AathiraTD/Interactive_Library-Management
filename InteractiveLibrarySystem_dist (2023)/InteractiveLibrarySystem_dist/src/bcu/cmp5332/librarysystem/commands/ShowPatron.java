package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.model.Patron;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;

import java.time.LocalDate;

public class ShowPatron implements Command {

    private final int patronId;

    public ShowPatron(int patronId) {
        this.patronId = patronId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        if (patron != null) {
            System.out.println(patron.getDetailsShort());
        } else {
            System.out.println("Patron #" + patronId + " not found.");
        }
    }
}
