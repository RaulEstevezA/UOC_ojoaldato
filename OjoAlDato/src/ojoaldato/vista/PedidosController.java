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
            // Cargar el formulario de nuevo pedido
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/PedidoFormulario.fxml"));
            Parent view = loader.load();
            
            // Configurar el controlador del formulario
            PedidoFormularioController controller = loader.getController();
            controller.setContenidoPedidos(contenidoPedidos);
            
            // Mostrar el formulario
            contenidoPedidos.getChildren().setAll(view);
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarError("Error al cargar el formulario de pedido: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    @FXML
    private void mostrarTodos() {
        try {
            // Cargar la vista de lista de pedidos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/PedidosLista.fxml"));
            Parent view = loader.load();
            
            // Configurar el controlador de la lista
            PedidosListaController controller = loader.getController();
            controller.setContenidoPedidos(contenidoPedidos);
            
            // Mostrar la lista de pedidos
            contenidoPedidos.getChildren().setAll(view);
            
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error al cargar la lista de pedidos: " + e.getMessage());
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
