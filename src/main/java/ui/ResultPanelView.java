package ui;

import data.Pathes;
import data.ResultPanelContract;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import data.Palette;

/**
 * The ResultPanelView class is a graphical component for displaying results and executing scripts in a UI.
 * It implements the ResultPanelContract.View interface to support MVP-based architecture for managing logic.
 */
public class ResultPanelView extends JPanel implements ResultPanelContract.View {

    /**
     * Table for displaying the results in a tabular format.
     */
    private JTable resultsTable;

    /**
     * Button for loading and running a script from an external file.
     */
    private JButton runScriptButton;

    /**
     * Button for creating and executing an ad-hoc script.
     */
    private JButton createScriptButton;

    /**
     * Presenter responsible for handling the user interactions and business logic.
     */
    private ResultPanelContract.Presenter presenter;

    /**
     * Constructs a new ResultPanelView instance and initializes its components.
     */
    public ResultPanelView() {
        init();
        initResultTable();
        initButtonsPanel();
    }

    /**
     * Initializes the primary layout and border for the panel.
     */
    public void init() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    /**
     * Configures and initializes the table for displaying results.
     * Sets its layout and adds a scrollable view.
     */
    public void initResultTable() {
        resultsTable = new JTable();
        resultsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        resultsTable.setEnabled(false);
        
        JTableHeader header = resultsTable.getTableHeader();
        header.setBackground(Palette.Header.getColor());
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        resultsTable.setTableHeader(header);

        resultsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row % 2 == 0) 
                    component.setBackground(Palette.EvenRow.getColor());
                else
                    component.setBackground(Palette.OddRow.getColor());

                // Set text alignment
                if (component instanceof JLabel) {
                    JLabel label = (JLabel) component;
                    if (column == 0) {
                        label.setHorizontalAlignment(SwingConstants.LEFT); // First column - left alignment
                    } else {
                        label.setHorizontalAlignment(SwingConstants.RIGHT); // The rest of the columns are aligned to the right
                    }
                }
                
                if (isSelected) 
                    component.setBackground(Palette.Selected.getColor());

                return component;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(resultsTable);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates and configures the button panel containing buttons for script-related functionalities.
     * Adds appropriate action listeners to handle user input.
     */
    public void initButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        //select script button
        runScriptButton = new JButton("Run Script from File");
        buttonPanel.add(runScriptButton);

        runScriptButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(Pathes.ToScripts.getPath()));
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                String selectedScript = fileChooser.getSelectedFile().getAbsolutePath();

                // Validate the selected file and invoke the presenter's script execution method
                if (selectedScript != null && !selectedScript.isEmpty()) {
                    presenter.onRunScript(selectedScript);
                }
            }
        });

        //create script button
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

    /**
     * Attaches the presenter to this view.
     *
     * @param presenter The presenter responsible for handling user interactions and logic.
     */
    @Override
    public void setPresenter(ResultPanelContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Retrieves the presenter attached to this view.
     *
     * @return The presenter for managing business logic.
     */
    @Override
    public ResultPanelContract.Presenter getPresenter() {
        return presenter;
    }

    /**
     * Updates the results table with new data.
     *
     * @param results     The 2D array containing the data to be displayed.
     * @param columnNames The array of column headers corresponding to the data.
     */
    @Override
    public void displayResults(String[][] results, String[] columnNames) {
        DefaultTableModel tableModel = new DefaultTableModel(results, columnNames);
        resultsTable.setModel(tableModel);
    }

    /**
     * Displays an error message dialog to the user.
     *
     * @param message The error message to be displayed.
     */
    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Notifies the user about the successful execution of a script.
     *
     * @param successMessage The success message to be displayed.
     */
    @Override
    public void notifyScriptExecution(String successMessage) {
        JOptionPane.showMessageDialog(this, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}