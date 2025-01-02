package ui;

import domain.model_panel_logic.ModelPanelModel;
import domain.model_panel_logic.ModelPanelPresenter;
import domain.result_panel_logic.ResultPanelModel;
import domain.result_panel_logic.ResultPanelPresenter;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final ModelPanelView modelPanelView;
    private final ResultPanelView resultPanelView;

    public Window(String modelsPath, String dataPath) {
        setTitle("Modelling framework sample");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create panels
        modelPanelView = new ModelPanelView();
        resultPanelView = new ResultPanelView();

        ModelPanelPresenter modelPanelPresenter = new ModelPanelPresenter(modelPanelView, new ModelPanelModel());

        // Connect ResultPanel MVP components
        ResultPanelPresenter resultPanelPresenter = new ResultPanelPresenter(resultPanelView, new ResultPanelModel());

        // Connect panels (when model runs, pass Controller to ResultPanelPresenter)
        modelPanelPresenter.setResultPanelPresenter(resultPanelPresenter);

        // Load data
        modelPanelView.getPresenter().loadModelData(modelsPath, dataPath);

        // Add panels to the frame
        add(modelPanelView, BorderLayout.WEST);
        add(resultPanelView, BorderLayout.CENTER);

        setVisible(true);
    }
}