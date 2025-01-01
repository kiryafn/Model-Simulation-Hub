// src/main/java/ModelPanel.java
package ui;

import domain.Controller;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ModelPanel extends JPanel {
    private JList<String> modelList;
    private JList<String> dataList;
    private JButton runModelButton;

    public ModelPanel(String modelsPath, String dataPath) {
       init();
       initModelList(modelsPath);
       initDataList(dataPath);
       initRunModelButton();
    }

    //creating JList for selecting existing models
    private void initModelList(String modelsPath){
        modelList = loadFilesIntoList(modelsPath);
        modelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(modelList), BorderLayout.WEST);
    }

    private void initDataList(String dataPath){
        dataList = loadFilesIntoList(dataPath);
        dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(dataList), BorderLayout.EAST);
    }

    private void initRunModelButton(){
        runModelButton = new JButton("Run Model");
        add(runModelButton, BorderLayout.SOUTH);

        runModelButton.addActionListener(e -> {
            if (modelList.getSelectedValue() != null && dataList.getSelectedValue() != null) {
                Controller controller = new Controller("models." + modelList.getSelectedValue().replace(".java", ""));
                controller.readDataFrom("src/main/java/data/" + dataList.getSelectedValue());
                controller.runModel();
                //updateView();
            }
        });
    }

    public void init(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel title = new JLabel("Select model and data");
        add(title, BorderLayout.NORTH);
    }

    private static JList<String> loadFilesIntoList(String dpath) {
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
        }

        return new JList<String>(modelListModel);
    }


}
