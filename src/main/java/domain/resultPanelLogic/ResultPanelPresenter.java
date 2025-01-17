package domain.resultPanelLogic;

import data.ResultPanelContract;
import domain.Controller;

/**
 * The ResultPanelPresenter class implements the presenter logic for the result panel.
 * It acts as a mediator between the view (UI) and the model (business logic or data processing),
 * handling user interactions and updating the view accordingly.
 */
public class ResultPanelPresenter implements ResultPanelContract.Presenter {

    /**
     * The view interface for updating the UI components of the result panel.
     */
    private final ResultPanelContract.View view;

    /**
     * The model interface for executing scripts and processing data used in the result panel.
     */
    private final ResultPanelContract.Model model;

    /**
     * The controller instance for managing interactions with models via the result panel.
     * (Transmitted via ModelPanel)
     */
    private Controller controller; //Transmitted via SelectionPanelPresenter

    /**
     * Constructor for initializing the ResultPanelPresenter with the provided view and model.
     *
     * @param view  the view interface for the result panel
     * @param model the model interface for data processing and script execution
     */
    public ResultPanelPresenter(ResultPanelContract.View view, ResultPanelContract.Model model) {
        this.view = view;
        this.model = model;
        this.view.setPresenter(this);
    }

    /**
     * Sets the current controller. This configures communication with the selected model.
     *
     * @param controller the Controller instance to interact with the models
     */
    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Executes a script located at the specified file path using the current controller.
     * Updates the results table and displays appropriate messages upon success or failure.
     *
     * @param scriptPath the file path to the script to execute
     */
    @Override
    public void onRunScript(String scriptPath) {
        try {
            model.executeScriptFromFile(scriptPath, controller);
            view.notifyScriptExecution("Script executed successfully!");
            updateResultsTable(controller.getResultsAsTsv());
            controller.resetScriptVariables();
        } catch (Exception e) {
            view.showError("Failed to execute script: " + e.getMessage());
        }
    }

    /**
     * Executes an ad-hoc script provided as a string using the current controller.
     * Updates the results table and displays appropriate messages upon success or failure.
     *
     * @param scriptCode the code of the script to execute
     */
    @Override
    public void onCreateAndRunScript(String scriptCode) {
        try {
            model.executeAdHocScript(scriptCode, controller);
            view.notifyScriptExecution("Ad-hoc script executed successfully!");
            updateResultsTable(controller.getResultsAsTsv());
            controller.resetScriptVariables();
        } catch (Exception e) {
            view.showError("Failed to execute ad-hoc script: " + e.getMessage());
        }
    }

    /**
     * Updates the results table in the view using data in TSV (Tab-Separated Values) format.
     *
     * @param tsvData the TSV data to process and display in the results table
     */
    @Override
    public void updateResultsTable(String tsvData) {
        String[][] data = model.parseTSV(tsvData);
        if (data.length > 0) {
            String[] columnNames = data[0]; // Column names in the first row
            String[][] rows = new String[data.length - 1][];
            System.arraycopy(data, 1, rows, 0, data.length - 1);

            view.displayResults(rows, columnNames);
        }
    }
}