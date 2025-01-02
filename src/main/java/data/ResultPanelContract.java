package data;

import domain.Controller;

/**
 * Contract for the Result Panel that defines the interactions between the View, Presenter, and Model.
 */
public interface ResultPanelContract {

    interface View {
        void setPresenter(Presenter presenter);

        Presenter getPresenter();

        void displayResults(String[][] results, String[] columnNames);

        void showError(String message);

        void notifyScriptExecution(String successMessage);
    }

    interface Presenter {
        void onRunScript(String scriptPath);

        void onCreateAndRunScript(String scriptCode);

        void updateResultsTable(String tsvData);

        void setController(Controller controller);
    }

    interface Model {
        String[][] parseTSV(String tsvData);

        void executeScriptFromFile(String scriptPath, Controller controller);

        void executeAdHocScript(String scriptCode, Controller controller);
    }
}