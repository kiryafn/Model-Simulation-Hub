import data.Paths;
import ui.Window;

import javax.swing.*;

/**
 * The Main class serves as the entry point for the Modelling Framework application.
 * It initializes the application by invoking the main graphical user interface (GUI).
 *
 * The application's GUI is launched on the Event Dispatch Thread (EDT) using
 * SwingUtilities.invokeLater to ensure thread safety for Swing components.
 *
 * This class creates an instance of the Window class, which sets up the main
 * application window with the necessary components to enable model selection,
 * data selection, and execution.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new Window(Paths.ToModels.getPath(), Paths.ToData.getPath())
        );
    }
}
