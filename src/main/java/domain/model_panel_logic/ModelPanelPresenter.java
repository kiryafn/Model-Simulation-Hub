package domain.model_panel_logic;

import data.ModelPanelContract;
import data.ResultPanelContract;
import domain.Controller;

public class ModelPanelPresenter implements ModelPanelContract.Presenter {

    private final ModelPanelContract.View view;
    private final ModelPanelContract.Model model;
    private Controller currentController;
    private String selectedModel;
    private String selectedData;

    private ResultPanelContract.Presenter resultPanelPresenter;

    public ModelPanelPresenter(ModelPanelContract.View view, ModelPanelContract.Model model) {
        this.view = view;
        this.model = model;
        this.view.setPresenter(this);
    }

    @Override
    public void onModelSelected(String modelName) {
        this.selectedModel = modelName;
    }

    @Override
    public void onDataSelected(String dataFileName) {
        this.selectedData = dataFileName;
    }

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
            String output = currentController.getResultsAsTsv(); // Модель возвращает TSV строки

            // Передаем Controller и выводим результаты во вторую панель
            if (resultPanelPresenter != null) {
                resultPanelPresenter.setController(currentController);
                resultPanelPresenter.updateResultsTable(output); // Обновляем таблицу с результатами
            }

            view.showModelRunSuccessfully();
        } catch (Exception e) {
            view.showError("Error running model: " + e.getMessage());
        }
    }

    @Override
    public void loadModelData(String modelsPath, String dataPath) {
        view.showModelsList(model.getModelFiles(modelsPath));
        view.showDataList(model.getDataFiles(dataPath));
    }

    @Override
    public void setResultPanelPresenter(ResultPanelContract.Presenter resultPanelPresenter) {
        this.resultPanelPresenter = resultPanelPresenter;
    }


}