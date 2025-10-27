package ojoaldato.vista;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends javafx.application.Application {
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ojoaldatovista/Interfaz.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), (double)320.0F, (double)240.0F);
        stage.setTitle("OnlineStore");
        stage.setScene(scene);
        stage.show();
    }
}
