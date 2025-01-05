package domain.selectionPanelLogic;

import data.ModelPanelContract;
import data.ResultPanelContract;
import domain.Controller;

/**
 * Presenter for managing the logic between the ModelPanel view and its underlying data model.
 * It handles user actions, triggers model execution, and updates the ResultPanel with outputs.
 */
public class ModelPanelPresenter implements ModelPanelContract.Presenter {

    /**
     * The view associated with the ModelPanel, responsible for UI components and feedback.
     */
    private final ModelPanelContract.View view;

    /**
     * The model associated with the ModelPanel, responsible for data operations.
     */
    private final ModelPanelContract.Model model;

    /**
     * The controller for the selected model, responsible for handling data and executing the model.
     */
    private Controller currentController;

    /**
     * The name of the currently selected model file.
     */
    private String selectedModel;

    /**
     * The name of the currently selected data file.
     */
    private String selectedData;

    /**
     * The presenter for the ResultPanel, which displays results from executed models.
     */
    private ResultPanelContract.Presenter resultPanelPresenter;

    /**
     * Constructs a ModelPanelPresenter with the specified view and model.
     *
     * @param view  The view interface for handling UI operations.
     * @param model The model interface for data-related operations.
     */
    public ModelPanelPresenter(ModelPanelContract.View view, ModelPanelContract.Model model) {
        this.view = view;
        this.model = model;
        this.view.setPresenter(this);
    }

    /**
     * Handles the event when a model is selected by the user.
     *
     * @param modelName The name of the model file selected by the user.
     */
    @Override
    public void onModelSelected(String modelName) {
        this.selectedModel = modelName;
    }

    /**
     * Handles the event when a data file is selected by the user.
     *
     * @param dataFileName The name of the data file selected by the user.
     */
    @Override
    public void onDataSelected(String dataFileName) {
        this.selectedData = dataFileName;
    }

    /**
     * Executes the selected model with the selected data file when the "Run Model" button is clicked.
     * It validates user inputs, handles errors, and updates the ResultPanel with the model's outputs.
     */
    @Override
    public void onRunModelClicked() {
        if (selectedModel == null || selectedData == null) {
            view.showError("Please select both a model and a data file.");
            return;
        }

        try {
            currentController = model.createController(selectedModel.replace(".java", ""));
            currentController.readDataFrom("./src/main/resources/data/" + selectedData);
            currentController.runModel();
            String output = currentController.getResultsAsTsv(); // Model returns TSV line

            // We pass the Controller and display the results in the second panel
            if (resultPanelPresenter != null) {
                resultPanelPresenter.setController(currentController);
                resultPanelPresenter.updateResultsTable(output); // Update table with results
            }

            view.showModelRunSuccessfully();
        } catch (Exception e) {
            view.showError("Error running model: " + e.getMessage());
        }
    }

    /**
     * Loads available models and data file names from the specified paths into the view.
     *
     * @param modelsPath The directory path to the available model files.
     * @param dataPath   The directory path to the available data files.
     */
    @Override
    public void loadModelData(String modelsPath, String dataPath) {
        view.showModelsList(model.getModelFiles(modelsPath));
        view.showDataList(model.getDataFiles(dataPath));
    }

    /**
     * Sets the ResultPanel presenter to handle updating the result panel after model execution.
     *
     * @param resultPanelPresenter The presenter for the ResultPanel.
     */
    @Override
    public void setResultPanelPresenter(ResultPanelContract.Presenter resultPanelPresenter) {
        this.resultPanelPresenter = resultPanelPresenter;
    }


}