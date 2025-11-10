package ojoaldato.vista;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ojoaldato.db.ConexionDB;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp extends javafx.application.Application {
    public void start(Stage stage) throws IOException {
        try {
            ConexionDB.getConnection();
        } catch (SQLException e) {
            System.err.println("Error: no se encuentra la clase db.ConexionDB.");
            throw new RuntimeException("Fallo al cargar la clase de conexión.");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/Interfaz.fxml"));
        Scene scene = new Scene((Parent)fxmlLoader.load(), (double)320.0F, (double)240.0F);
        stage.setTitle("OnlineStore");
        stage.setScene(scene);
        stage.show();
    }
}
