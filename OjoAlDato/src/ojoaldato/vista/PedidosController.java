package ojoaldato.vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import ojoaldato.controlador.PedidoControlador;

import java.io.IOException;

public class PedidosController {

    @FXML
    private StackPane contenidoPedidos;

    // Controlador de negocio (deberás crearlo)
    private final PedidoControlador pedidoControlador = new PedidoControlador();

    @FXML
    private void initialize() {
        mostrar("Selecciona una opción de pedidos");
    }

    @FXML
    private void nuevoPedido() {
        try {
            // En un futuro podrías crear un formulario para nuevos pedidos
            // Por ahora mostramos un mensaje temporal
            mostrar("Funcionalidad de nuevo pedido en desarrollo");
        } catch (Exception e) {
            mostrarError("Error al intentar crear un nuevo pedido");
        }
    }

    @FXML
    private void mostrarTodos() {
        try {
            // Aquí cargarías la lista completa de pedidos
            mostrar("Mostrando todos los pedidos");
            // En el futuro: cargar PedidosLista.fxml
        } catch (Exception e) {
            mostrarError("Error al cargar los pedidos");
        }
    }

    @FXML
    private void mostrarPendientes() {
        try {
            // Filtrado de pedidos pendientes
            mostrar("Mostrando pedidos pendientes");
        } catch (Exception e) {
            mostrarError("Error al cargar pedidos pendientes");
        }
    }

    @FXML
    private void mostrarEnviados() {
        try {
            // Filtrado de pedidos enviados
            mostrar("Mostrando pedidos enviados");
        } catch (Exception e) {
            mostrarError("Error al cargar pedidos enviados");
        }
    }

    @FXML
    private void mostrarEntregados() {
        try {
            // Filtrado de pedidos entregados
            mostrar("Mostrando pedidos entregados");
        } catch (Exception e) {
            mostrarError("Error al cargar pedidos entregados");
        }
    }

    // Métodos auxiliares
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
