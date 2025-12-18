package ojoaldato.vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TableView;

import java.io.IOException;

public class Controller {

    @FXML
    private StackPane contentArea; // vinculado desde el FXML

    @FXML
    private BorderPane root;

    @FXML
    private StackPane leftContainer;

    @FXML
    private VBox inicioBox;

    @FXML
    private VBox menuLateral;

    private void mostrarMenuLateral() {
        leftContainer.setVisible(true);
        leftContainer.setManaged(true);
    }


    @FXML
    private void abrirArticulos(ActionEvent event) {
        mostrarMenuLateral();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/ArticulosView.fxml"));
            Parent view = loader.load();

            ajustarTablas(view);

            contentArea.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirClientes() {
        mostrarMenuLateral();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/ClientesView.fxml"));
            Parent view = loader.load();

            ajustarTablas(view);

            contentArea.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirPedidos(ActionEvent event) {
        mostrarMenuLateral();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/PedidosView.fxml"));
            Parent view = loader.load();

            ajustarTablas(view);

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

    private void ajustarTablas(Parent root) {
        root.lookupAll(".table-view").forEach(node -> {
            if (node instanceof TableView<?>) {
                ((TableView<?>) node)
                        .setColumnResizePolicy(
                                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS
                        );
            }
        });
    }
}