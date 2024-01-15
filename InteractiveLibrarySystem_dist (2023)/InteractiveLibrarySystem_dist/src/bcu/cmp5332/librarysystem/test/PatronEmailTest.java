package bcu.cmp5332.librarysystem.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import bcu.cmp5332.librarysystem.commands.*;
import bcu.cmp5332.librarysystem.main.*;
import bcu.cmp5332.librarysystem.data.*;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.utils.CliMessageDisplayer;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

class PatronEmailTest {

	@TempDir
    Path tempDir;

    private Library library;
    private MessageDisplayer cliDisplayer;

    @BeforeEach
    void setUp() throws IOException, LibraryException {
        Path tempFile = tempDir.resolve("tempPatrons.txt");
        System.setProperty("patrondata.filepath", tempFile.toString());

        // Initialize library and CLI displayer
        library = LibraryData.load();
        cliDisplayer = new CliMessageDisplayer();
    }

    @AfterEach
    void tearDown() {
        System.clearProperty("patrondata.filepath");
    }

//    @Test
//    void addPatronTest() throws LibraryException, IOException {
//        List<Integer> bookIds = Collections.emptyList();
//        Command add = new AddPatron("William Smith", "07462074815", "william.smith@mail.bcu.ac.uk", bookIds);
//        add.execute(library, LocalDate.now(), cliDisplayer);
//
//        // Assuming the last added patron is the one we want to test
//        Patron addedPatron = library.getPatrons().get(library.getPatrons().size() - 1);
//
//        assertEquals("William Smith", addedPatron.getName());
//        assertEquals("07462074815", addedPatron.getPhoneNumber());
//        assertEquals("william.smith@mail.bcu.ac.uk", addedPatron.getEmail());
//        assertEquals(bookIds, addedPatron.getBorrowedBooks());
//    }


    @Test
    void storePatronTest() throws LibraryException, IOException {
        Library library = MockLibraryData.load();
        
        List<Integer> bookIds = Collections.<Integer>emptyList();
        Command add = new AddPatron("William Smith", "7462074815", "william.smith@mail.bcu.ac.uk", bookIds);
        MessageDisplayer cliDisplayer = new CliMessageDisplayer();
        add.execute(library, LocalDate.now(), cliDisplayer);

        MockLibraryData.store(library);
        
        Library newLibrary = MockLibraryData.load();
        int maxId = newLibrary.getPatrons().stream().mapToInt(Patron::getId).max().orElse(0);
        
        Patron patron = newLibrary.getPatronById(maxId); // Get the last patron
        assertEquals(maxId, patron.getId());
        assertEquals("William Smith", patron.getName());
        assertEquals("7462074815", patron.getPhoneNumber());
        assertEquals("william.smith@mail.bcu.ac.uk", patron.getEmail());
        assertEquals(bookIds, patron.getBorrowedBooks());
        }
   }
