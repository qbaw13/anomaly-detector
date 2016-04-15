package com.tirt.controller;

import com.tirt.AnomalyDetectorApp;
import com.tirt.model.DetectionCreatorModel;
import javafx.fxml.FXML;
import com.tirt.model.DetectorModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
    Parent root;

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

    @FXML
    private void onStart() {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/detection-creator.fxml"));
        loader.setControllerFactory(t -> new DetectionCreatorController(new DetectionCreatorModel()));

        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Detection creator");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(AnomalyDetectorApp.primaryStage);
        stage.setResizable(false);
        stage.show();

    }

    @FXML
    private void onPause() {

    }
}
