package ui;

import domain.selectionPanelLogic.ModelPanelModel;
import domain.selectionPanelLogic.ModelPanelPresenter;
import domain.resultPanelLogic.ResultPanelModel;
import domain.resultPanelLogic.ResultPanelPresenter;

import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
 * The main application window for the Modelling Framework sample.
 * This class sets up the application's graphical user interface (GUI),
 * including the Model and Result panels using the MVP pattern.
 *
 * <p>It initializes and connects the ModelPanel and ResultPanel components
 * and configures their interaction through presenters.</p>
 */
public class Window extends JFrame {
    /**
     * The view for the panel that handles model-related logic and user interactions.
     */
    private final SelectionPanelView selectionPanelView;

    /**
     * The view for the panel that displays the results of the model execution.
     */
    private final ResultPanelView resultPanelView;

    /**
     * Constructs the main application window and initializes the GUI components.
     *
     * @param modelsPath The file path to the model data.
     * @param dataPath   The file path to the associated data.
     */
    public Window(String modelsPath, String dataPath) {
        setTitle("Modelling Framework");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create panels
        selectionPanelView = new SelectionPanelView();
        resultPanelView = new ResultPanelView();

        ModelPanelPresenter modelPanelPresenter = new ModelPanelPresenter(selectionPanelView, new ModelPanelModel());

        // Connect ResultPanel MVP components
        ResultPanelPresenter resultPanelPresenter = new ResultPanelPresenter(resultPanelView, new ResultPanelModel());

        // Connect panels (when model runs, pass Controller to ResultPanelPresenter)
        modelPanelPresenter.setResultPanelPresenter(resultPanelPresenter);

        // Load data
        selectionPanelView.getPresenter().loadModelData(modelsPath, dataPath);

        // Add panels to the frame
        add(selectionPanelView, BorderLayout.WEST);
        add(resultPanelView, BorderLayout.CENTER);

        setVisible(true);
    }
}