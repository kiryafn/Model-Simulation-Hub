package domain;

import ui.Window;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        /*String path = "./src/main/resources/";
        Controller c1 = new Controller("EconomyModel")
                .readDataFrom(path + "economy_data.txt")
                .runModel()
                .runScript("ZIND = new double[LL]; for (i = 0; i < LL; i++) { ZIND[i] = EXP[i] / PRC[i]; }");
        String output = c1.getResultsAsTsv();
        System.out.println(output);*/

        SwingUtilities.invokeLater(() -> new Window());

    }
}
