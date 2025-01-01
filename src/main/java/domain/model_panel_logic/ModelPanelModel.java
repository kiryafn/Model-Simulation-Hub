package domain.model_panel_logic;

import data.ModelPanelContract;
import domain.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModelPanelModel implements ModelPanelContract.Model {

    @Override
    public String[] getModelFiles(String modelsPath) {
        return getFilesFromDirectory(modelsPath);
    }

    @Override
    public String[] getDataFiles(String dataPath) {
        return getFilesFromDirectory(dataPath);
    }

    @Override
    public Controller createController(String modelName) {
        return new Controller(modelName);
    }

    private String[] getFilesFromDirectory(String path) {
        List<String> files = new ArrayList<>();
        File directory = new File(path);

        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    files.add(file.getName());
                }
            }
        }
        return files.toArray(new String[0]);
    }
}