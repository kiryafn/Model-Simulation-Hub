package domain.result_panel_logic;

import data.ResultPanelContract;
import domain.Controller;

public class ResultPanelPresenter implements ResultPanelContract.Presenter {
    private final ResultPanelContract.View view;
    private final ResultPanelContract.Model model;
    private Controller controller; // Контроллер для работы с моделями (передан через ModelPanel)

    public ResultPanelPresenter(ResultPanelContract.View view, ResultPanelContract.Model model) {
        this.view = view;
        this.model = model;
        this.view.setPresenter(this);
    }

    // Установка текущего контроллера (настраивает связь с выбранной моделью)
    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void onRunScript(String scriptPath) {
        try {
            model.executeScriptFromFile(scriptPath, controller);
            view.notifyScriptExecution("Script executed successfully!");
            updateResultsTable(controller.getResultsAsTsv());
        } catch (Exception e) {
            view.showError("Failed to execute script: " + e.getMessage());
        }
    }

    @Override
    public void onCreateAndRunScript(String scriptCode) {
        try {
            model.executeAdHocScript(scriptCode, controller);
            view.notifyScriptExecution("Ad-hoc script executed successfully!");
            updateResultsTable(controller.getResultsAsTsv());
        } catch (Exception e) {
            view.showError("Failed to execute ad-hoc script: " + e.getMessage());
        }
    }

    @Override
    public void updateResultsTable(String tsvData) {
        String[][] data = model.parseTSV(tsvData);
        if (data.length > 0) {
            String[] columnNames = data[0]; // Имена колонок в первой строке
            String[][] rows = new String[data.length - 1][];
            System.arraycopy(data, 1, rows, 0, data.length - 1);

            view.displayResults(rows, columnNames);
        }
    }
}