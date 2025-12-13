package ojoaldato.vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import java.io.IOException;

public class Controller {

    @FXML
    private StackPane contentArea; // vinculado desde el FXML

    @FXML
    private void abrirArticulos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/ArticulosView.fxml"));
            Parent view = loader.load();

            contentArea.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirClientes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/ClientesView.fxml"));
            Parent view = loader.load();

            ClientesController controller = loader.getController();
            contentArea.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirPedidos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/PedidosView.fxml"));
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarTextoTemporal("Error al cargar la vista de pedidos");
        }
    }
    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }

    // -----------------------------
    // Método auxiliar para mostrar contenido temporal
    // -----------------------------
    private void mostrarTextoTemporal(String texto) {
        contentArea.getChildren().clear();
        Label label = new Label(texto);
        contentArea.getChildren().add(label);
    }
}