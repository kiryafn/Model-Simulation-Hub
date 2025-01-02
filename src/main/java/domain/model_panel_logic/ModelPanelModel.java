package domain.model_panel_logic;

import data.ModelPanelContract;
import domain.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link ModelPanelContract.Model} interface.
 * This class provides functionality for retrieving model and data files
 * from specified directories and creating a {@link Controller} instance.
 */
public class ModelPanelModel implements ModelPanelContract.Model {

    /**
     * Retrieves the list of model files from the specified directory.
     *
     * @param modelsPath The path to the directory containing model files.
     * @return An array of file names found in the specified directory.
     */
    @Override
    public String[] getModelFiles(String modelsPath) {
        return getFilesFromDirectory(modelsPath);
    }

    /**
     * Retrieves the list of data files from the specified directory.
     *
     * @param dataPath The path to the directory containing data files.
     * @return An array of file names found in the specified directory.
     */
    @Override
    public String[] getDataFiles(String dataPath) {
        return getFilesFromDirectory(dataPath);
    }

    /**
     * Creates a new {@link Controller} instance for a given model name.
     */
    @Override
    public Controller createController(String modelName) {
        return new Controller(modelName);
    }

    /**
     * Retrieves the file names from a specified directory.
     *
     * @param path The path to the directory.
     * @return An array of file names found in the directory, or an empty array if the directory does not exist or is invalid.
     */
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
        //Create new array of Strings of size 0 and it automatically resizes it to fit all the data
        return files.toArray(new String[0]);
    }
}