package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.gui.BooksListView;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.SwingUtilities;

/**
 * The ListBooks class represents a command to list all available books in the library.
 * It can be used in both CLI and GUI modes.
 */
public class ListBooks implements Command {

    private BooksListView view; // This can be null for CLI usage
    /**
     * Constructor for ListBooks command.
     *
     * @param view               The BooksListView for GUI mode (can be null for CLI).
     * @param messageDisplayer   The message displayer for showing messages.
     */
    public ListBooks(BooksListView view, MessageDisplayer messageDisplayer) {
        this.view = view;
    }

    /**
     * Executes the list books command. It lists all available books in the library,
     * filtering out deleted books, and displays them in the GUI view or CLI console.
     *
     * @param library       The library instance on which the command is executed.
     * @param currentDate   The current date.
     * @throws LibraryException if there are issues with listing the books.
     */
    @Override
    public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer) throws LibraryException {
        List<Book> books = library.getBooks().stream()
                              .filter(book -> !book.isDeleted())
                              .collect(Collectors.toList());

        if (view != null) {
            // GUI: Update the BooksListView with the list of books
            SwingUtilities.invokeLater(() -> {
                view.updateView(books);
            });
        } else {
            // CLI: Print the book details to the console
            for (Book book : books) {
                messageDisplayer.displayMessage(book.getDetailsShort());
            }
            messageDisplayer.displayMessage(books.size() + " book(s) available.");
        }
    }
}
