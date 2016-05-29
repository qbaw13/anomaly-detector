package com.tirt.controller;

import com.tirt.api.EClusteringMethod;
import com.tirt.model.DetectionCreatorModel;
import com.tirt.service.ClusteringMethodMapper;
import com.tirt.service.DetectorFactory;
import com.tirt.service.NetworkInterfaceReceiver;
import com.tirt.service.NetworkInterfaceStringConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.pcap4j.core.PcapNetworkInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kuba on 15.04.2016.
 */
public class DetectionCreatorController {

    private static Logger LOGGER = LoggerFactory.getLogger(DetectionCreatorController.class);

    private ToggleGroup toggleGroup;
    @FXML
    private Button closeButton;
    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;
    @FXML
    private ChoiceBox<PcapNetworkInterface> interfaceChoiceBox;

    private DetectionCreatorModel detectionCreatorModel;

    public DetectionCreatorController(DetectionCreatorModel detectionCreatorModel){
        this.detectionCreatorModel = detectionCreatorModel;
    }

    @FXML
    public void initialize() {
        initNetworkInterfaceChoiceBox();
        initRadioButtons();
    }

    private void initNetworkInterfaceChoiceBox() {
        List<PcapNetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterfaceReceiver.receiveNetworkInterfaces();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectProperty<ObservableList<PcapNetworkInterface>> networkInterfacesProperty = new SimpleObjectProperty<>();
        networkInterfacesProperty.setValue(FXCollections.observableArrayList(networkInterfaces));
        this.interfaceChoiceBox.itemsProperty().bindBidirectional(networkInterfacesProperty);
        this.interfaceChoiceBox.setConverter(new NetworkInterfaceStringConverter());
    }

    private void initRadioButtons() {
        toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton1.setText(EClusteringMethod.K_MEANS.toString());
        radioButton2.setText(EClusteringMethod.HIERARCHICAL.toString());
    }

    @FXML
    private void onStart() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        LOGGER.info("Detection started");
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        LOGGER.info("Detection creation canceled");
    }
}
