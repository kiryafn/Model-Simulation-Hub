package ui;

import data.ModelPanelContract;

import javax.swing.*;
import java.awt.*;

public class ModelPanelView extends JPanel implements ModelPanelContract.View {

    private ModelPanelContract.Presenter presenter;

    private JList<String> modelList;
    private JList<String> dataList;
    private JButton runModelButton;

    public ModelPanelView() {
        init();
        initModelList();
        initDataList();
        initRunModelButton();
    }

    public void init() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel title = new JLabel("Select model and data");
        add(title, BorderLayout.NORTH);
    }

    private void initRunModelButton() {
        runModelButton = new JButton("Run Model");
        runModelButton.addActionListener(e -> presenter.onRunModelClicked());
        add(runModelButton, BorderLayout.SOUTH);
    }

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

    @Override
    public void setPresenter(ModelPanelContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public ModelPanelContract.Presenter getPresenter() {
        return presenter;
    }

    @Override
    public void showModelsList(String[] models) {
         modelList.setListData(models);
    }

    @Override
    public void showDataList(String[] data) {
        dataList.setListData(data);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showModelRunSuccessfully() {
        JOptionPane.showMessageDialog(null, "Model run successfully" , "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}