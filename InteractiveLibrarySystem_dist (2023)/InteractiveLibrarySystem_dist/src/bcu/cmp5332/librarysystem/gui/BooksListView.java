package bcu.cmp5332.librarysystem.gui;

import javax.swing.*;
import bcu.cmp5332.librarysystem.model.Book;

import java.util.List;

public class BooksListView {
    private JTable table;
    private JFrame frame;

    public BooksListView(JFrame frame) {
        this.frame = frame;
    }

    public void updateView(List<Book> books) {
        String[] columns = new String[]{"ID", "Title", "Author", "Publication Year", "Status"};
        Object[][] data = new Object[books.size()][5];

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            data[i][0] = book.getId();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getPublicationYear();
            data[i][4] = book.getStatus().toString();  // Assuming getStatus() returns an enum or similar
        }

        table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(scrollPane);
        frame.revalidate();
        frame.repaint();
    }
}
