package com.tirt;

import com.tirt.controller.DetectorController;
import com.tirt.model.DetectorModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Kuba on 05.04.2016.
 */
public class AnomalyDetectorApp extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/ui.fxml"));
        loader.setControllerFactory(t -> new DetectorController(new DetectorModel()));

        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);

    }
}
