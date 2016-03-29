import controller.DetectorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DetectorModel;

/**
 * Created by Kuba on 26.03.2016.
 */
public class AnomalyDetectorApp extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./view/ui.fxml"));
        loader.setControllerFactory(t -> new DetectorController(new DetectorModel()));

        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
