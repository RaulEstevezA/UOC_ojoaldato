package ojoaldato.vista;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import ojoaldato.controlador.ClienteControlador;

import java.io.IOException;

public class ClientesController {

    @FXML
    private StackPane contenidoClientes;

    // Controlador de negocio
    private final ClienteControlador clienteControlador = new ClienteControlador();

    @FXML
    private void initialize() {
        mostrar("Selecciona una opción");
    }

    @FXML
    private void anadirCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ojoaldato/vista/ClientesFormulario.fxml"));
            Parent view = loader.load();

            // Obtener el controlador del formulario
            ClientesFormularioController controller = loader.getController();

            // Inyectar dependencias
            controller.setClienteControlador(clienteControlador);
            controller.setContenidoClientes(contenidoClientes);

            // Mostrar la vista
            contenidoClientes.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
            mostrar("Error cargando la vista de formulario");
        }
    }

    @FXML
    private void mostrarClientes() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/ojoaldato/vista/ClientesLista.fxml")
            );
            Parent view = loader.load();

            // OJO: esta línea es CRUCIAL
            ClientesListaController controller = loader.getController();

            // Inyectamos la lógica de negocio para que el controlador pueda consultar DB
            controller.setClienteControlador(clienteControlador);

            // Mostramos la vista en el panel de contenido dinámico
            contenidoClientes.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
            contenidoClientes.getChildren().setAll(
                    new Label("Error al cargar la lista de clientes")
            );
        }
    }

    @FXML
    private void mostrarEstandar() {
        mostrar("Listado de clientes estándar");
    }

    @FXML
    private void mostrarPremium() {
        mostrar("Listado de clientes premium");
    }

    @FXML
    private void volver() {
        mostrar("Volver al menú principal");
        // luego cargaremos otra vista aquí
    }

    // Método auxiliar para mostrar texto temporal
    private void mostrar(String texto) {
        contenidoClientes.getChildren().clear();
        contenidoClientes.getChildren().add(new Label(texto));
    }
}