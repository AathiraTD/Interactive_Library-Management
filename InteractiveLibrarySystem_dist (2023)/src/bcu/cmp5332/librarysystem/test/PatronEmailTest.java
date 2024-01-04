package bcu.cmp5332.librarysystem.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import bcu.cmp5332.librarysystem.commands.*;
import bcu.cmp5332.librarysystem.main.*;
import bcu.cmp5332.librarysystem.data.*;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.utils.CliMessageDisplayer;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

class PatronEmailTest {

	@Test
	void addPatronTest() throws LibraryException, IOException {
	    // Load library data from files
	    Library library = LibraryData.load();
        // Find current highest patronId
        //int maxId = library.getPatrons().stream().mapToInt(Patron::getId).max().orElse(0);
	    int maxId = 0;
    	if (library.getPatrons().size() > 0) {
    		int lastIndex = library.getPatrons().size() - 1;
            maxId = library.getPatrons().get(lastIndex).getId();
    	}
    	// Add new patron - with email
        List<Integer> bookIds = Collections.<Integer>emptyList();
		Command add = new AddPatron("William Smith", "07462074815", "william.smith@mail.bcu.ac.uk", bookIds);
		MessageDisplayer cliDisplayer = new CliMessageDisplayer();
        add.execute(library, LocalDate.now(),cliDisplayer);
        Patron patron = library.getPatronById(maxId + 1); // New patron
		assertEquals(maxId + 1, patron.getId());
		assertEquals(patron.getName(), "William Smith");
		assertEquals(patron.getPhoneNumber(), "07462074815");
		assertEquals(patron.getEmail(), "william.smith@mail.bcu.ac.uk"); // Check if email is added correctly
		assertEquals(patron.getBorrowedBooks(), bookIds); 
	}

	@Test
	void storePatronTest() throws LibraryException, IOException {
	    // Load library data from files
	    Library library = LibraryData.load();
    	// Add new patron - with email
        List<Integer> bookIds = Collections.<Integer>emptyList();
		Command add = new AddPatron("William Smith", "07462074815", "william.smith@mail.bcu.ac.uk", bookIds);
		MessageDisplayer cliDisplayer = new CliMessageDisplayer();
        add.execute(library, LocalDate.now(),cliDisplayer);
        // Store changes
     	LibraryData.store(library);
		// Load new library data from files
        Library newLibrary = LibraryData.load();
        // Find current highest patronId
        //int maxId = newLibrary.getPatrons().stream().mapToInt(Patron::getId).max().orElse(0);
	    int maxId = 0;
    	if (newLibrary.getPatrons().size() > 0) {
    		int lastIndex = newLibrary.getPatrons().size() - 1;
            maxId = newLibrary.getPatrons().get(lastIndex).getId();
    	}
        Patron patron = newLibrary.getPatronById(maxId); // New patron
		assertEquals(maxId, patron.getId());
		assertEquals(patron.getName(), "William Smith");
		assertEquals(patron.getPhoneNumber(), "07462074815");
		assertEquals(patron.getEmail(), "william.smith@mail.bcu.ac.uk"); // Check if email is added correctly
		assertEquals(patron.getBorrowedBooks(), bookIds);
	}
}