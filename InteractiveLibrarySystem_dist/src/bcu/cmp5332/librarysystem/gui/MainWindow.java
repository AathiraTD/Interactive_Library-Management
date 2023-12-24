package bcu.cmp5332.librarysystem.gui;

import bcu.cmp5332.librarysystem.data.LibraryData;
import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.*;
import bcu.cmp5332.librarysystem.model.Book.Status;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.*;

public class MainWindow extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu membersMenu;

    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;	
    private JMenuItem booksIssue;
    private JMenuItem booksReturn;
    private JMenuItem booksRenew;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;
    
    private JButton viewPatron;
//    private boolean patronInfo = false;
    private Patron patron;
    private int patronId;
    
//    private boolean bookInfo = false;
    private List<Book> viewBooksList;

    private Library library;

    public MainWindow(Library library) {

        initialize();
        this.library = library;
    } 
    
    public Library getLibrary() {
        return library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksReturn = new JMenuItem("Return");
        booksRenew = new JMenuItem("Renew");
        
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksIssue);
        booksMenu.add(booksReturn);
        booksMenu.add(booksRenew);
        
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Members");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);
        
        viewPatron = new JButton();
        viewPatron.addActionListener(this);
        viewBooksList = new ArrayList<Book>();

        setSize(1100, 500); //width was 800

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        
        
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
    public static void main(String[] args) throws IOException, LibraryException {
        Library library = LibraryData.load();
        new MainWindow(library);			
    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();
            
        } else if (ae.getSource() == booksAdd) {
           new AddBookWindow(this);
            
        } else if (ae.getSource() == booksDel) {
           new DeleteBookWindow(this);
            
        } else if (ae.getSource() == booksIssue) {
           new IssueBookWindow(this);
           
        } else if (ae.getSource() == booksReturn) {
           new ReturnBookWindow(this);
            
        } else if (ae.getSource() == booksRenew) {
            new RenewBookWindow(this);
            
         } else if (ae.getSource() == memView) {
            displayPatrons();
            
        } else if (ae.getSource() == memAdd) {
            new AddPatronWindow(this);
            
        } else if (ae.getSource() == memDel) {
            new DeletePatronWindow(this);
            
        }
    }

    public void displayBooks() {
        List<Book> booksList = library.getBooks();
        // headers for the table
        String[] columns = new String[]{"Title", "Author", "Pub Date", "Status", "Member Info"};
      //Gets list of books which haven't been deleted
        List<Book> existingBooks = new ArrayList<Book>();
        for (int x = 0; x < booksList.size(); x++) {
        	Book book = booksList.get(x);
        	if(!book.isDeleted()) {
        		existingBooks.add(book);
        	}
        }
      //Shows books which haven't been deleted
        Object[][] data = new Object[existingBooks.size()][6];
        for (int i = 0; i < existingBooks.size(); i++) {
            Book book = existingBooks.get(i);
            if(!book.isDeleted()) {
	            data[i][0] = book.getTitle();
	            data[i][1] = book.getAuthor();
	            data[i][2] = book.getPublicationYear();
	            data[i][3] = book.getStatus();
	            if (book.getStatus() == Status.LOANED_OUT) {
	            	Loan loan = book.getLoan();
	            	patron = loan.getPatron();
	            	patronId = patron.getId();
	            	data[i][4] = "Member id:" + patron.getId();
	            } else {
	            	data[i][4] = "";
	            }
            }
        }

        //viewPatron = new JButton();
        //viewPatron.addActionListener(this);
        JTable table = new JTable(data, columns);
        //use custom renderer for view patron column
    	table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());;
    	
    	//use custom editor for view patron column
    	table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField()));
    	
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
    
    public void displayPatrons() {
        List<Patron> patronsList = library.getPatrons();
        // headers for the table
        String[] columns = new String[]{"Name", "Phone number", "Email", "Loaned Books", "Books Info"};
        //Gets list of patrons which haven't been deleted
        List<Patron> existingPatrons = new ArrayList<Patron>();
        for (int x = 0; x < patronsList.size(); x++) {
        	Patron patron = patronsList.get(x);
        	if(!patron.isDeleted()) {
        		existingPatrons.add(patron);
        	}
        }
        //Shows patrons who haven't been deleted
        Object[][] data = new Object[existingPatrons.size()][6];
        for (int i = 0; i < existingPatrons.size(); i++) {
            Patron patron = existingPatrons.get(i);
            if(!patron.isDeleted()) {
	            data[i][0] = patron.getName();
	            data[i][1] = patron.getPhoneNumber();
	            data[i][2] = patron.getEmail();
	            if (!patron.getBorrowedBooks().isEmpty()) {
	            	List<Book> borrowedList = patron.getBorrowedBooks(); //lists number of borrowed books
	            	data[i][3] = borrowedList.size() + " book(s)";
            		String data4 = "";
	            	for(int y = 0; y < borrowedList.size(); y++) {
	            		if (y == borrowedList.size() - 1) {
	            			Book book = borrowedList.get(y);
		            		data4 = data4 + book.getId();
	            		} else {
		            		Book book = borrowedList.get(y);
		            		data4 = data4 + book.getId() + ":";
	            		}
	            	}
	            	data[i][4] = "Book ids:" + data4;
	            } else {
	            	data[i][3] = "None";
	            	data[i][4] = "";
	            }
            }
        }
        
        //viewPatron = new JButton();
        //viewPatron.addActionListener(this);
        JTable table = new JTable(data, columns);
        //use custom renderer for view patron column
    	table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());;
    	
    	//use custom editor for view patron column
    	table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField()));
        
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
    
    class ButtonRenderer extends JButton implements  TableCellRenderer
    {
    	public ButtonRenderer() {
    		//setOpaque(true);
    	}
    	@Override
    	public Component getTableCellRendererComponent(JTable table, Object obj,
    			boolean selected, boolean focused, int row, int col) {
    		
    		//set button text
    		setText((obj==null) ? "":obj.toString());
    				
    		return this;
    	}
    	
    }

    //BUTTON EDITOR CLASS
    class ButtonEditor extends DefaultCellEditor
    {
    	protected JButton btn;
    	 private String lbl;
    	 private Boolean clicked;
    	 
    	 public ButtonEditor(JTextField txt) {
    		super(txt);
    		
    		btn=new JButton();
    		//btn.setOpaque(true);
    		
    		btn.addActionListener(new ActionListener() {
    			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				//new ViewPatronDetails(this, patron);
    				fireEditingStopped();
    			}
    		});
    	}

    	 @Override
    	public Component getTableCellEditorComponent(JTable table, Object obj,
    			boolean selected, int row, int col) {
    		 try {
     			//lbl = data[i][4]
        		 lbl=(obj==null) ? "":obj.toString();
        		 
        		 clicked=true;
        		 return btn;
    		 } catch (ArrayIndexOutOfBoundsException e) {
    			 lbl=(obj==null) ? "":obj.toString();
    			 btn.setText(lbl);
    			 clicked=true;
    			return btn;
    		 }
    	}
    	 
    	//if button is clicked
    	 @Override
    	public Object getCellEditorValue() {

    		if(clicked)
    		{
    			String[] properties = lbl.split(":");
    			
	       		if(properties[0].equals("Member id")) {
	       			patronId = Integer.parseInt(properties[1]);
    				try {
    					patron = library.getPatronByID(patronId);
						MainWindow mw = new MainWindow(library);
						new ViewPatronDetails(mw, patron);
						dispose();  
					} catch (LibraryException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	       		} else if(properties[0].equals("Book ids")) {
	       			for(int i = 1; i < properties.length; i++) {
	       				Book book;
						try {
							book = library.getBookByID(Integer.parseInt(properties[i]));
							viewBooksList.add(book);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (LibraryException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	       			}
    				MainWindow mw = new MainWindow(library);
					new ViewBookDetails(mw, viewBooksList);
					dispose(); 
	       		} else if (properties[0].isBlank()){
    				JOptionPane.showMessageDialog(btn, "        No data to view");
	       		}
    		}
    		clicked=false;
    		return new String(lbl);
    	}
    	
    	 @Override
    	public boolean stopCellEditing() {
    			clicked=false;
    		return super.stopCellEditing();
    	}
    	 
    	 @Override
    	protected void fireEditingStopped() {
    		super.fireEditingStopped();
    	}
    }
}
