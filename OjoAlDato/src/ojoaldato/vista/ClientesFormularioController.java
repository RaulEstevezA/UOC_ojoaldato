package ojoaldato.vista;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import ojoaldato.controlador.ClienteControlador;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.ClienteEstandar;
import ojoaldato.modelo.ClientePremium;

public class ClientesFormularioController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDomicilio;
    @FXML private TextField txtNif;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cbTipo;

    // Controlador de negocio (inyectado desde fuera)
    private ClienteControlador clienteControlador;

    // Panel dinámico donde pintar resultados
    private StackPane contenidoClientes;

    public void setClienteControlador(ClienteControlador controlador) {
        this.clienteControlador = controlador;
    }

    public void setContenidoClientes(StackPane panel) {
        this.contenidoClientes = panel;
    }

    @FXML
    private void initialize() {
        cbTipo.getItems().addAll("Estandar", "Premium");
        cbTipo.getSelectionModel().selectFirst();
    }

    @FXML
    private void guardar() {

        // Validación simple
        if (txtNombre.getText().isEmpty() ||
                txtDomicilio.getText().isEmpty() ||
                txtNif.getText().isEmpty() ||
                txtEmail.getText().isEmpty()) {

            mostrarAlerta("Todos los campos son obligatorios");
            return;
        }

        Cliente nuevo;

        boolean esPremium = cbTipo.getValue().equalsIgnoreCase("Premium");

        if (esPremium) {
            nuevo = new ClientePremium(
                    txtNombre.getText(),
                    txtDomicilio.getText(),
                    txtNif.getText(),
                    txtEmail.getText()
            );
        } else {
            nuevo = new ClienteEstandar(
                    txtNombre.getText(),
                    txtDomicilio.getText(),
                    txtNif.getText(),
                    txtEmail.getText()
            );
        }

        String resultado = clienteControlador.addCliente(nuevo);

        mostrarAlerta(resultado);

        limpiarFormulario();
    }

    @FXML
    private void cancelar() {
        limpiarFormulario();
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtDomicilio.clear();
        txtNif.clear();
        txtEmail.clear();
        cbTipo.getSelectionModel().selectFirst();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensaje, ButtonType.OK);
        alert.showAndWait();
    }
}
