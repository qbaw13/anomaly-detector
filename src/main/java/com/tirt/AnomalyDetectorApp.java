package com.tirt;

import com.tirt.controller.DetectorController;
import com.tirt.model.DetectorModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kuba on 05.04.2016.
 */
public class AnomalyDetectorApp extends Application {

    public static Stage primaryStage;

    private static Logger LOGGER = LoggerFactory.getLogger(AnomalyDetectorApp.class);

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/main-ui.fxml"));
        LOGGER.info("Main scene loaded");
        loader.setControllerFactory(t -> new DetectorController(new DetectorModel()));
        LOGGER.info("Main scene controller and model created");

        stage.setTitle("Anomaly Detector");
        stage.setScene(new Scene(loader.load()));
        stage.show();

        this.primaryStage = stage;
    }

    public static void main(String[] args) {
        launch(args);

    }
}
