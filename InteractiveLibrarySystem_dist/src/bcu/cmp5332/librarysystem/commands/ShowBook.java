package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Book;
import bcu.cmp5332.librarysystem.model.Library;

import java.time.LocalDate;

public class ShowBook implements Command {

    private final int bookId;

    public ShowBook(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Book book = library.getBookByID(bookId);
        if (book != null && !book.isDeleted()) {
            System.out.println(book.getDetailsShort());
        } else {
            System.out.println("Book #" + bookId + " not found.");
        }
    }
}
