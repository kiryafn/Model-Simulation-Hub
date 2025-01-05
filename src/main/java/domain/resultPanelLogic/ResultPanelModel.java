package domain.resultPanelLogic;

import data.ResultPanelContract;
import domain.Controller;

/**
 * The {@code ResultPanelModel} class provides the core logic for handling data
 * and executing scripts based on the requirements of a result panel.
 * It implements the {@link ResultPanelContract.Model} interface to ensure compatibility
 * with other necessary modules.
 */
public class ResultPanelModel implements ResultPanelContract.Model {

    /**
     * Parses a string containing tab-separated values (TSV) data into a
     * two-dimensional string array. Each row is split by a newline character
     * and individual fields are split by tab characters.
     *
     * @param tsvData The input string in TSV format.
     * @return A two-dimensional string array representing the parsed data.
     */
    @Override
    public String[][] parseTSV(String tsvData) {
        // Parsing a TSV string into a two-dimensional array
        String[] rows = tsvData.split("\n");
        String[][] result = new String[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].split("\t");
        }
        return result;
    }

    /**
     * Executes a script stored in a file. The provided {@link Controller} object
     * handles the actual execution of the file's contents.
     *
     * @param scriptPath The file system path to the script file.
     * @param controller The {@link Controller} object used to execute the script.
     */
    @Override
    public void executeScriptFromFile(String scriptPath, Controller controller) {
        controller.runScriptFromFile(scriptPath);
    }

    /**
     * Executes an ad-hoc script provided as a string. The provided {@link Controller}
     * object interprets and runs the script's logic.
     *
     * @param scriptCode The script code to be executed.
     * @param controller The {@link Controller} object used to interpret and run the script.
     */
    @Override
    public void executeAdHocScript(String scriptCode, Controller controller) {
        controller.runScript(scriptCode);
    }
}