package basilseed.ui;

import basilseed.BasilSeed;
import basilseed.exception.BasilSeedException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private BasilSeed basilSeed;

    @Override
    public void start(Stage stage) {
        try {
            basilSeed = new BasilSeed();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setBasilSeed(basilSeed); // inject the BasilSeed instance
            stage.show();
        } catch (IOException | BasilSeedException e) {
            e.printStackTrace();
            Platform.exit();
        }
    }
}
