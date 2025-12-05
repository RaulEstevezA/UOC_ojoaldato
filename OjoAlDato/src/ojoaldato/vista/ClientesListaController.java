package ojoaldato.vista;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ojoaldato.controlador.ClienteControlador;
import ojoaldato.modelo.Cliente;

import java.util.List;

public class ClientesListaController {

    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colDomicilio;
    @FXML private TableColumn<Cliente, String> colNif;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colTipo;

    // Controlador de negocio inyectado
    private ClienteControlador clienteControlador;

    // MÉTODO PARA INYECTARLO
    public void setClienteControlador(ClienteControlador controlador) {
        this.clienteControlador = controlador;
    }

    @FXML
    private void initialize() {

        // Vincular columnas con atributos del modelo
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDomicilio.setCellValueFactory(new PropertyValueFactory<>("domicilio"));
        colNif.setCellValueFactory(new PropertyValueFactory<>("nif"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // "tipo" no es atributo en clase padre, lo obtenemos del nombre de clase
        colTipo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue() instanceof ojoaldato.modelo.ClientePremium ?
                                "Premium" : "Estandar"
                )
        );
    }

    public void cargarTodos() {
        List<Cliente> clientes = clienteControlador.listarClientes();

        if (clientes.isEmpty()) {
            tablaClientes.setPlaceholder(new Label("No hay clientes registrados"));
        }

        tablaClientes.getItems().setAll(clientes);
    }

    public void cargarEstandar() {
        List<Cliente> clientes = clienteControlador.listarClientesEstandar();
        tablaClientes.getItems().setAll(clientes);
    }

    public void cargarPremium() {
        List<Cliente> clientes = clienteControlador.listarClientesPremium();
        tablaClientes.getItems().setAll(clientes);
    }

}
