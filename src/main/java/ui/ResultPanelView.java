package ui;

import data.ResultPanelContract;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResultPanelView extends JPanel implements ResultPanelContract.View {

    private JTable resultsTable;
    private JButton runScriptButton;
    private JButton createScriptButton;
    private ResultPanelContract.Presenter presenter;

    public ResultPanelView() {
        init();
        initResultTable();
        initButtonsPanel();
    }

    public void init() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public void initResultTable() {
        resultsTable = new JTable();
        resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        resultsTable.setDefaultRenderer(Object.class, rightRenderer);

        JScrollPane tableScrollPane = new JScrollPane(resultsTable);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    public void initButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        runScriptButton = new JButton("Run Script from File");
        buttonPanel.add(runScriptButton);

        runScriptButton.addActionListener(e -> {
            String scriptPath = JOptionPane.showInputDialog(this,
                    "Enter script path:");
            if (scriptPath != null && !scriptPath.isEmpty()) {
                presenter.onRunScript(scriptPath);
            }
        });

        createScriptButton = new JButton("Create and Run Ad Hoc Script");
        buttonPanel.add(createScriptButton);

        createScriptButton.addActionListener(e -> {
            String scriptCode = JOptionPane.showInputDialog(this,
                    "Enter script code:");
            if (scriptCode != null && !scriptCode.isEmpty()) {
                presenter.onCreateAndRunScript(scriptCode);
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void setPresenter(ResultPanelContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ResultPanelContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void displayResults(String[][] results, String[] columnNames) {
        DefaultTableModel tableModel = new DefaultTableModel(results, columnNames);
        resultsTable.setModel(tableModel);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void notifyScriptExecution(String successMessage) {
        JOptionPane.showMessageDialog(this, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}