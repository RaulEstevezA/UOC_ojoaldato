package ojoaldato.vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.function.Consumer;

public class PedidosController {

    @FXML
    private StackPane contenidoPedidos;

    // ======================
    // Nuevo pedido
    // ======================
    @FXML
    private void nuevoPedido() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ojoaldato/vista/PedidoFormulario.fxml")
            );
            Parent view = loader.load();

            PedidoFormularioController controller = loader.getController();
            controller.setContenidoPedidos(contenidoPedidos);

            contenidoPedidos.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar el formulario de pedido");
        }
    }

    // ======================
    // Mostrar TODOS
    // ======================
    @FXML
    private void mostrarTodos() {
        cargarListaPedidos(null);
    }

    // ======================
    // Mostrar PENDIENTES
    // ======================
    @FXML
    private void mostrarPendientes() {
        cargarListaPedidos(PedidosListaController::cargarPedidosPendientes);
    }

    // ======================
    // Mostrar ENVIADOS
    // ======================
    @FXML
    private void mostrarEnviados() {
        cargarListaPedidos(PedidosListaController::cargarPedidosEnviados);
    }

    // ======================
    // Carga centralizada de lista
    // ======================
    private void cargarListaPedidos(
            Consumer<PedidosListaController> configuracion
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ojoaldato/vista/PedidosLista.fxml")
            );
            Parent view = loader.load();

            PedidosListaController controller = loader.getController();
            controller.setContenidoPedidos(contenidoPedidos);

            if (configuracion != null) {
                configuracion.accept(controller);
            }

            contenidoPedidos.getChildren().setAll(view);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al cargar la lista de pedidos");
        }
    }

    // ======================
    // Métodos auxiliares
    // ======================
    private void mostrar(String mensaje) {
        contenidoPedidos.getChildren().clear();
        contenidoPedidos.getChildren().add(new Label(mensaje));
    }

    private void mostrarError(String mensaje) {
        contenidoPedidos.getChildren().clear();
        Label errorLabel = new Label("Error: " + mensaje);
        errorLabel.setStyle("-fx-text-fill: red;");
        contenidoPedidos.getChildren().add(errorLabel);
    }
}