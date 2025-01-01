package ui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final ModelPanel modelPanel;
    private final ResultPanel resultPanel;

    public Window(String modelsPath, String dataPath) {
        setTitle("Modelling framework sample");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create panels
        modelPanel = new ModelPanel(modelsPath, dataPath);
        resultPanel = new ResultPanel();

        // Add panels to the frame
        add(modelPanel, BorderLayout.WEST);
        add(resultPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}