package data;

import domain.Controller;

public interface ModelPanelContract {

    interface View {
        void setPresenter(Presenter presenter);
        Presenter getPresenter();
        void showModelsList(String[] models);
        void showDataList(String[] data);
        void showError(String message);
        void showModelRunSuccessfully();
    }

    interface Presenter {
        void onModelSelected(String modelName);
        void onDataSelected(String dataFileName);
        void onRunModelClicked();
        void loadModelData(String modelsPath, String dataPath);
        void setResultPanelPresenter(ResultPanelContract.Presenter resultPanelPresenter);
    }

    interface Model {
        String[] getModelFiles(String modelsPath);
        String[] getDataFiles(String dataPath);
        Controller createController(String modelName);
    }
}


