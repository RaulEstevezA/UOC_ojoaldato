package ojoaldato.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/Interfaz.fxml"));
        Parent root = fxmlLoader.load();

        // Tamaño inicial de la ventana
        Scene scene = new Scene(root, 1100, 650);

        stage.setTitle("OnlineStore");
        stage.setScene(scene);

        // Tamaño mínimo permitido
        stage.setMinWidth(900);
        stage.setMinHeight(550);

        // Centrar ventana en pantalla
        stage.centerOnScreen();

        // (Opcional) permitir redimensionar - recomendado mantener activo
        stage.setResizable(true);

        stage.show();
    }
}