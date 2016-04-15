package com.tirt.controller;

import com.tirt.service.InterfaceStringConverter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import com.tirt.model.DetectorModel;
import javafx.scene.control.ChoiceBox;
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

    private DetectorModel detectorModel;

    public DetectorController(DetectorModel detectorModel){
        this.detectorModel = detectorModel;
    }

    @FXML
    ChoiceBox<PcapNetworkInterface> interfaceChoiceBox;

    @FXML
    public void initialize() {
        List<PcapNetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = detectorModel.obtainInterfaces();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectProperty<ObservableList<PcapNetworkInterface>> networkInterfacesProperty = new SimpleObjectProperty<>();
        networkInterfacesProperty.setValue(FXCollections.observableArrayList(networkInterfaces));
        this.interfaceChoiceBox.itemsProperty().bindBidirectional(networkInterfacesProperty);
        this.interfaceChoiceBox.setConverter(new InterfaceStringConverter());
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
    private void onSave(){
        LOGGER.info("onSave");
    }

    @FXML
    private void onSaveAs(){
        LOGGER.info("onSaveAs");
    }

    @FXML
    private void onClose(){
        LOGGER.info("onClose");
    }

}
