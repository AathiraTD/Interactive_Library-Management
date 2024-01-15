package bcu.cmp5332.librarysystem.test;

import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.data.DataManager;
import bcu.cmp5332.librarysystem.main.LibraryException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The LibraryData class is responsible for loading and storing library data using various DataManagers.
 * It initializes a list of DataManagers and provides methods to load and store library data.
 */
public class MockLibraryData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>();
    
    // runs only once when the object gets loaded into memory
    static {
        // Add DataManagers for different types of data
        dataManagers.add(new MockBookDataManager());
        
        /* Uncomment the two lines below when the implementation of their 
        loadData() and storeData() methods is complete */
        dataManagers.add(new MockPatronDataManager());
        dataManagers.add(new MockLoanDataManager());
    }
    
    /**
     * Loads library data from external sources using registered DataManagers.
     *
     * @return The Library object containing loaded data.
     * @throws LibraryException if there is an error loading the data.
     * @throws IOException      if there is an I/O error during data loading.
     */
    public static Library load() throws LibraryException, IOException {

        Library library = new Library();
        for (DataManager dm : dataManagers) {
            dm.loadData(library);
        }
        return library;
    }

    /**
     * Stores library data to external sources using registered DataManagers.
     *
     * @param library The Library object containing the data to be stored.
     * @throws IOException if there is an error storing the data.
     */
    public static void store(Library library) throws IOException {

        for (DataManager dm : dataManagers) {
            dm.storeData(library);
        }
    }
}
