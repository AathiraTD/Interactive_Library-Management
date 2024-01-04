/**
 * 
 */
package bcu.cmp5332.librarysystem.utils;

/**
 * 
 */
import javax.swing.JOptionPane;

public class GuiMessageDisplayer implements MessageDisplayer {
    @Override
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }
}
