package bcu.cmp5332.librarysystem.commands;

import bcu.cmp5332.librarysystem.main.LibraryException;
import bcu.cmp5332.librarysystem.model.Library;
import bcu.cmp5332.librarysystem.utils.MessageDisplayer;

import java.time.LocalDate;

/**
 * Command to display a help message to the command line.
 */
public class Help implements Command {

	@Override
	public void execute(Library library, LocalDate currentDate, MessageDisplayer messageDisplayer)
			throws LibraryException {
				System.out.println(Command.HELP_MESSAGE);
		// TODO Auto-generated method stub
		
	}
}
 