// src/main/java/ResultsPanel.java
package ui;

import domain.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.table.DefaultTableCellRenderer;

public class ResultPanel extends JPanel {

    private JTable resultsTable;
    private JButton runScriptButton;
    private JButton createScriptButton;

    public ResultPanel() {
        init();
        initResultTable();
        initButtonsPanel();
    }

    public void init(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void initResultTable(){
        resultsTable = new JTable();
        resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //to make horizontal alignment right in table cells
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        resultsTable.setDefaultRenderer(Object.class, rightRenderer);

        //scrollbar for the table
        JScrollPane tableScrollPane = new JScrollPane(resultsTable);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    public void initButtonsPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3));

        runScriptButton = new JButton("Run Script from File");
        buttonPanel.add(runScriptButton);

        createScriptButton = new JButton("Create and Run Ad Hoc Script");
        buttonPanel.add(createScriptButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }



}
