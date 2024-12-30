// src/main/java/ModelPanel.java
package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ModelPanel extends JPanel {
    private JList<String> modelList;
    private JList<String> dataList;
    private JButton runModelButton;

    public ModelPanel(String modelsPath) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel title = new JLabel("Select model and data");
        add(title, BorderLayout.NORTH);

        modelList = new JList<>(loadFilesIntoModel(modelsPath));
    }



    private DefaultListModel<String> loadFilesIntoModel(String dpath) {
        DefaultListModel<String> modelListModel = new DefaultListModel<>();
        File directory = new File(dpath);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        modelListModel.addElement(file.getName());
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Directory not found: " + dpath, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return modelListModel;
    }
}
