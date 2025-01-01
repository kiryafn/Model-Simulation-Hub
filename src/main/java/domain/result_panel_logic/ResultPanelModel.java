package domain.result_panel_logic;

import data.ResultPanelContract;
import domain.Controller;

public class ResultPanelModel implements ResultPanelContract.Model {

    @Override
    public String[][] parseTSV(String tsvData) {
        // Разбираем TSV строку в двумерный массив
        String[] rows = tsvData.split("\n");
        String[][] result = new String[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].split("\t");
        }
        return result;
    }

    @Override
    public void executeScriptFromFile(String scriptPath, Controller controller) {
        controller.runScriptFromFile(scriptPath);
    }

    @Override
    public void executeAdHocScript(String scriptCode, Controller controller) {
        controller.runScript(scriptCode);
    }
}