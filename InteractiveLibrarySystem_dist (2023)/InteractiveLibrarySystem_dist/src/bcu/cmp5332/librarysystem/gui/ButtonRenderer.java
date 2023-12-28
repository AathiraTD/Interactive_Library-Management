package bcu.cmp5332.librarysystem.gui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Custom table cell renderer that displays a button in a table cell.
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ButtonRenderer() {
        setOpaque(true);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        // Set the text of the button based on the value
        setText((value == null) ? "" : value.toString());
        return this;
    }
}
