package bcu.cmp5332.librarysystem.data;

import java.io.IOException;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;

/**
 * The DataManager interface defines methods for loading and storing data for a library system.
 * Classes implementing this interface are responsible for reading data from and writing data to external sources.
 */
public interface DataManager {

    /**
     * The data separator used in data storage and retrieval.
     */
    public static final String SEPARATOR = "::";

    /**
     * Loads data into the library from an external source.
     *
     * @param library The library object to load data into.
     * @throws IOException      if there is an error reading data from the external source.
     * @throws LibraryException if there is an error in the data format or structure.
     */
    public void loadData(Library library) throws IOException, LibraryException;

    /**
     * Stores data from the library to an external source.
     *
     * @param library The library object to store data from.
     * @throws IOException if there is an error writing data to the external source.
     */
    public void storeData(Library library) throws IOException;
}
