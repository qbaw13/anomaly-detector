package com.tirt.controller;

import com.tirt.api.EClusteringMethod;
import com.tirt.service.NetworkInterfaceReceiver;
import com.tirt.service.NetworkInterfaceStringConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import com.tirt.model.DetectorModel;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.pcap4j.core.PcapNetworkInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kuba on 26.03.2016.
 */
public class DetectorController {

    private static Logger LOGGER = LoggerFactory.getLogger(DetectorController.class);

    @FXML
    Parent root;
    private ToggleGroup toggleGroup;
    @FXML
    private Button closeButton;
    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;
    @FXML
    private ChoiceBox<PcapNetworkInterface> interfaceChoiceBox;

    private DetectorModel detectorModel;

    public DetectorController(DetectorModel detectorModel){
        this.detectorModel = detectorModel;
    }

    @FXML
    public void initialize() {
        initNetworkInterfaceChoiceBox();
        initRadioButtons();
    }

    @FXML
    private void onNew(){
        LOGGER.info("onNew");
    }

    @FXML
    private void onOpen(){
        LOGGER.info("onOpen");
    }

    @FXML
    private void onClose(){
        LOGGER.info("onClose");
    }

    @FXML
    private void onStart() {

    }

    @FXML
    private void onPause() {

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
}
