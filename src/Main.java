import viewAndController.ApplicationWindow;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ApplicationWindow frame = new ApplicationWindow();
            frame.setVisible(true);
        });
    }
}