package com.tirt.controller;

import com.tirt.api.Clusterer;
import com.tirt.api.EClusteringMethod;
import com.tirt.service.ClusteringMethodMapper;
import com.tirt.utility.NetworkInterfaceStringConverter;
import com.tirt.service.Sniffer;
import com.tirt.utility.PacketDataExtractor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import com.tirt.model.DetectorModel;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.pcap4j.core.PcapNetworkInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Kuba on 26.03.2016.
 */
public class DetectorController {

    private static Logger LOGGER = LoggerFactory.getLogger(DetectorController.class);

    @FXML Parent root;
    private ToggleGroup toggleGroup;
    @FXML private RadioButton radioButton1;
    @FXML private RadioButton radioButton2;
    @FXML private ChoiceBox<PcapNetworkInterface> interfaceChoiceBox;
    @FXML private TextField packetCountText;
    @FXML private TextField clusterCountText;
    @FXML private Button start;
    @FXML private Button stop;
    @FXML private TabPane tabPane;

    private DetectorModel detectorModel;

    public DetectorController(DetectorModel detectorModel){
        this.detectorModel = detectorModel;
    }

    @FXML
    public void initialize() {
        initNetworkInterfaceChoiceBox();
        initRadioButtons();
        initNumericTextFields();
    }

    private void initNumericTextFields() {
        packetCountText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    packetCountText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        clusterCountText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    clusterCountText.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void onStart() {
        LOGGER.info("onStart");
        int packetsCount = Integer.parseInt(packetCountText.getText());
        int clustersCount = Integer.parseInt(clusterCountText.getText());
        setButtonsOnStart();
        Sniffer sniffer = detectorModel.createSniffer(interfaceChoiceBox.getSelectionModel().getSelectedItem(), packetsCount);
        detectorModel.startSniffer(sniffer);
        EClusteringMethod selectedMethod = ClusteringMethodMapper.map(toggleGroup.getSelectedToggle().toString());
        Clusterer clusterer = detectorModel.createClusterer(selectedMethod, clustersCount);

//        clusterer.setData(PacketDataExtractor.extractSthAndSth(sniffer.getCapturedPackets()));
//        detectorModel.startClusterer(clusterer);


    }

    @FXML
    private void onPause() {
        LOGGER.info("onPause");
        setButtonsOnStop();
    }

    @FXML
    private void onClear(){
        LOGGER.info("onClear");
    }

    @FXML
    private void onClose(){
        LOGGER.info("onClose");
    }


    private void initNetworkInterfaceChoiceBox() {
        List<PcapNetworkInterface> networkInterfaces = detectorModel.receiveNetworkInterfaces();
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

    private void setButtonsOnStart() {
        start.setDisable(true);
        stop.setDisable(false);
    }

    private void setButtonsOnStop() {
        start.setDisable(false);
        stop.setDisable(true);
    }
}
