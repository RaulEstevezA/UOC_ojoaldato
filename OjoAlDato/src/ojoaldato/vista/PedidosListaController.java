package ojoaldato.vista;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import ojoaldato.controlador.PedidoControlador;
import ojoaldato.modelo.Cliente;
import ojoaldato.modelo.Pedido;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PedidosListaController implements Initializable {

    @FXML private TableView<Pedido> tablaPedidos;
    @FXML private TableColumn<Pedido, Integer> colNumPedido;
    @FXML private TableColumn<Pedido, String> colCliente;
    @FXML private TableColumn<Pedido, String> colArticulo;
    @FXML private TableColumn<Pedido, Integer> colCantidad;
    @FXML private TableColumn<Pedido, String> colFecha;
    @FXML private TableColumn<Pedido, String> colEstado;
    @FXML private TableColumn<Pedido, String> colTotal;
    @FXML private TextField txtBuscarEmail;
    @FXML private Button btnCancelarPedido;

    private final PedidoControlador pedidoControlador = new PedidoControlador();
    private StackPane contenidoPedidos;

    // ======================
    // Inicialización
    // ======================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        ocultarCancelar();
        cargarTodos();
    }

    public void setContenidoPedidos(StackPane contenidoPedidos) {
        this.contenidoPedidos = contenidoPedidos;
    }

    // ======================
    // Configuración columnas
    // ======================
    private void configurarColumnas() {

        colNumPedido.setCellValueFactory(new PropertyValueFactory<>("numPedido"));

        colCliente.setCellValueFactory(cell -> {
            Cliente c = cell.getValue().getCliente();
            return new javafx.beans.property.SimpleStringProperty(
                    c.getNombre() + " (" + c.getEmail() + ")"
            );
        });

        colArticulo.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getArticulo().getDescripcion()
                )
        );

        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        colFecha.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getFechaHora().toString()
                )
        );

        colEstado.setVisible(false);

        colTotal.setCellValueFactory(cell -> {
            BigDecimal total = cell.getValue().getPrecioTotal();
            return new javafx.beans.property.SimpleStringProperty(
                    total != null ? String.format("%.2f €", total) : "0.00 €"
            );
        });
    }

    // ======================
    // Cargas principales
    // ======================
    public void cargarTodos() {
        prepararVista(false);
        cargarTabla(pedidoControlador.listarTodosPedidos());
    }

    public void cargarPedidosPendientes() {
        prepararVista(true);
        cargarTabla(pedidoControlador.listarPedidosPendientes(null));
    }

    public void cargarPedidosEnviados() {
        prepararVista(false);
        cargarTabla(pedidoControlador.listarPedidosEnviados(null));
    }

    // ======================
    // Buscar por email
    // ======================
    @FXML
    private void buscarPorEmail() {
        String email = txtBuscarEmail.getText().trim();

        if (email.isEmpty()) {
            mostrarError("Introduce un email para buscar.");
            return;
        }

        List<Pedido> pedidos = pedidoControlador.listarPedidosPorCliente(email);
        cargarTabla(pedidos);

        btnCancelarPedido.setVisible(
                pedidos.stream().anyMatch(p -> !p.getEnviado())
        );

        if (pedidos.isEmpty()) {
            mostrarMensaje("No se encontraron pedidos para el email indicado.");
        }
    }

    // ======================
    // Cancelar pedido
    // ======================
    @FXML
    private void cancelarPedido() {

        Pedido pedido = tablaPedidos.getSelectionModel().getSelectedItem();

        if (pedido == null) {
            mostrarError("Seleccione un pedido.");
            return;
        }

        if (pedido.getEnviado()) {
            mostrarError("No se puede cancelar un pedido enviado.");
            return;
        }

        String resultado = pedidoControlador.deletePedido(pedido.getNumPedido());
        mostrarMensaje(resultado);

        cargarPedidosPendientes();
    }

    // ======================
    // Navegación
    // ======================
    @FXML
    private void volver() {
        if (contenidoPedidos != null) {
            contenidoPedidos.getChildren().clear();
        }
    }

    // ======================
    // Utilidades internas
    // ======================
    private void cargarTabla(List<Pedido> pedidos) {
        tablaPedidos.setItems(FXCollections.observableArrayList(pedidos));
    }

    private void prepararVista(boolean mostrarCancelar) {
        if (txtBuscarEmail != null) {
            txtBuscarEmail.clear();
        }
        btnCancelarPedido.setVisible(mostrarCancelar);
    }

    private void ocultarCancelar() {
        btnCancelarPedido.setVisible(false);
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
