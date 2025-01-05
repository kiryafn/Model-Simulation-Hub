package ui;

import data.SelectionPanelContract;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;

/**
 * The SelectionPanelView class serves as the UI component for displaying model and data lists,
 * allowing users to select a model and associated data and execute a model run.
 * The class implements the {@link SelectionPanelContract.View} interface to facilitate interaction
 * between the UI and the underlying presenter logic.
 */
public class SelectionPanelView extends JPanel implements SelectionPanelContract.View {

    /**
     * The presenter providing the business logic and operations for this view.
     */
    private SelectionPanelContract.Presenter presenter;

    /**
     * A list component for displaying available models for user selection.
     */
    private JList<String> modelList;

    /**
     * A list component for displaying available datasets for user selection.
     */
    private JList<String> dataList;

    /**
     * A button for triggering the execution of the selected model with the selected data.
     */
    private JButton runModelButton;

    /**
     * Default constructor for the SelectionPanelView.
     * Sets up the layout and initializes child components for both models and data selection.
     */
    public SelectionPanelView() {
        init();
        initModelList();
        initDataList();
        initRunModelButton();
    }

    /**
     * Initializes the basic panel layout and sets up the title label at the top of the view.
     */
    public void init() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel title = new JLabel("Select model and data");
        add(title, BorderLayout.NORTH);
    }

    /**
     * Initializes the "Run Model" button and sets an action listener to handle model execution when clicked.
     */
    private void initRunModelButton() {
        runModelButton = new JButton("Run Model");
        runModelButton.addActionListener(e -> presenter.onRunModelClicked());
        add(runModelButton, BorderLayout.SOUTH);
    }

    /**
     * Initializes the model list component, configures selection mode, and sets a listener
     * to notify the presenter when a model is selected.
     */
    private void initModelList() {
        modelList = new JList<>();
        modelList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modelList.addListSelectionListener(e -> {
            if (!modelList.isSelectionEmpty()) {
                presenter.onModelSelected(modelList.getSelectedValue());
            }
        });

        add(new JScrollPane(modelList), BorderLayout.WEST);
    }

    /**
     * Initializes the data list component, configures selection mode, and sets a listener
     * to notify the presenter when a dataset is selected.
     */
    private void initDataList() {
        dataList = new JList<>();
        dataList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dataList.addListSelectionListener(e -> {
            if (!dataList.isSelectionEmpty()) {
                presenter.onDataSelected(dataList.getSelectedValue());
            }
        });
        add(new JScrollPane(dataList), BorderLayout.EAST);
    }

    /**
     * Sets the presenter for this view to enable interaction with business logic.
     *
     * @param presenter The {@link SelectionPanelContract.Presenter} instance to associate with this view.
     */
    @Override
    public void setPresenter(SelectionPanelContract.Presenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Retrieves the currently associated presenter for this view.
     *
     * @return The {@link SelectionPanelContract.Presenter} instance associated with this view.
     */
    @Override
    public SelectionPanelContract.Presenter getPresenter() {
        return presenter;
    }

    /**
     * Updates the model list with the provided array of model names.
     *
     * @param models The array of model names to display in the list.
     */
    @Override
    public void showModelsList(String[] models) {
        modelList.setListData(models);
    }

    /**
     * Updates the data list with the provided array of dataset names.
     *
     * @param data The array of dataset names to display in the list.
     */
    @Override
    public void showDataList(String[] data) {
        dataList.setListData(data);
    }

    /**
     * Displays an error message dialog.
     *
     * @param message The error message to display.
     */
    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a success message dialog indicating that the model execution has completed successfully.
     */
    @Override
    public void showModelRunSuccessfully() {
        JOptionPane.showMessageDialog(null, "Model run successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}