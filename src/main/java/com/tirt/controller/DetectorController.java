package com.tirt.controller;

import com.tirt.api.Clusterer;
import com.tirt.api.EClusteringMethod;
import com.tirt.entity.Cluster;
import com.tirt.entity.Point;
import com.tirt.service.ClustererImpl;
import com.tirt.service.Sniffer;
import com.tirt.utility.ClusteringMethodMapper;
import com.tirt.utility.NetworkInterfaceStringConverter;
import com.tirt.utility.PacketDataExtractor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import com.tirt.model.DetectorModel;
import javafx.scene.Parent;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
    @FXML private AnchorPane mainPane;

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

        sniffer.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                EClusteringMethod selectedMethod = (EClusteringMethod) toggleGroup.getSelectedToggle().getUserData();
                ClustererImpl clusterer = (ClustererImpl) detectorModel.createClusterer(selectedMethod, clustersCount);

                clusterer.setData(PacketDataExtractor.extractSthAndSth(sniffer.getCapturedPackets(), sniffer.getTimestamps()));
                clusterer.init();
                detectorModel.startClusterer(clusterer);

                clusterer.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        LOGGER.info("Clusterer succeed");
                        if(selectedMethod == EClusteringMethod.K_MEANS) {
                            drawCharts(clusterer.getClusters());
                        }
                        else if (selectedMethod == EClusteringMethod.HIERARCHICAL) {
                            pringNewick(clusterer.getClusters());
                        }
                    }
                });
            }
        });


    }


    private void drawCharts(List<Cluster> clusters) {
        mainPane.getChildren().clear();

        LOGGER.info("Drawing charts");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        final ScatterChart<Number, Number> sc = new ScatterChart<Number,Number>(xAxis,yAxis);

        xAxis.setLabel("ilość danych [bajty]");
        yAxis.setLabel("czas [ms]");
        sc.setTitle("Klasteryzacja K-średnich");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Equities");



        for (int i=0; i<clusters.size(); i++) {
            XYChart.Series tempSeries = new XYChart.Series();
            tempSeries.setName("Cluster " + i);

            for(Point point : clusters.get(i).points) {

                tempSeries.getData().add(new XYChart.Data(point.getX(), point.getY()));
            }

            sc.getData().add(tempSeries);
        }

        mainPane.getChildren().add(sc);
    }


    private void pringNewick(List<Cluster> clusters) {
        mainPane.getChildren().clear();

        TextArea textArea = new TextArea();

        textArea.appendText(clusters.get(0).getNewick()+";");

        mainPane.getChildren().add(textArea);
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
        radioButton1.setUserData(EClusteringMethod.K_MEANS);
        radioButton2.setUserData(EClusteringMethod.HIERARCHICAL);
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
